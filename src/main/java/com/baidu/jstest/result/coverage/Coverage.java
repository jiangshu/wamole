package com.baidu.jstest.result.coverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.invoker.BatchInvoker;
import com.baidu.jstest.page.ConfigurationFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * @ auth jiangshuguang
 */

public class Coverage {
    static String regex="^\\d\\d*$";
	static Pattern p=Pattern.compile(regex);
	static Matcher m;
	static Logger logger = LoggerFactory.getLogger(Coverage.class);
	public static CovResult parseCovObject(String covJson,String browser){		
		JSONObject statements= null;
		JSONObject lineObject= null;
		String lines,line,lineCov,key;	
		CovResult covResult = new CovResult();
	    try {
	    	lineObject=new JSONObject(covJson);
		    for (Iterator iter = lineObject.keys(); iter.hasNext();) {						  
				 key = (String)iter.next();
				 lines=lineObject.getString(key);	
				 CaseInstance caseInstance = new CaseInstance();
				 caseInstance.setBrowser(browser);
				 caseInstance.setCaseName(key);
				 if(lines!=null && !("".equals(lines)) && lines.startsWith("{") && lines.endsWith("}")){
					 lines=lines.replaceAll("\"", "'");
					 statements= new JSONObject(lines);
					 for (Iterator iter1 = statements.keys(); iter1.hasNext();){
						 line = (String)iter1.next();
						 lineCov=statements.getString(line);
						 m = p.matcher(lineCov);
						 if(lineCov!=null && !"".equals(lineCov) && m.find()){							
							 CaseLine caseLine = new CaseLine();
							 caseLine.setLineNumber(line);
							 caseLine.setCovNumber(Integer.parseInt(lineCov));
							 caseInstance.addLineCount();
							 caseInstance.addCaseLine(caseLine);
						 }else{
							 logger.info("  ***"+lineCov+"****");
						 }	 
					 } 
				 }
				 covResult.addCaseCount();
				 covResult.addCase(caseInstance);
				 covResult.setBrowser(browser);
				}
		 }catch(JSONException e){
			 e.printStackTrace();
		 } 
		 return covResult;
	}
     
   public static CovResult covMerge(CovResult oldCovResult,CovResult newcovResult){
	   
	    CaseInstance oldCaseInstance = new CaseInstance();
	    CaseLine oldCaseLine = new CaseLine();
	    CaseInstance newCaseInstance = new CaseInstance();
	    CaseLine newCaseLine = new CaseLine();	    
	    CovResult covResult = new CovResult();
	    covResult.setBrowser(oldCovResult.getBrowser());
	    String caseName;
		
		for(int i=0;i<newcovResult.getCaseCount();i++){
			newCaseInstance = newcovResult.getCase(i);
			caseName = newCaseInstance.getCaseName();
			oldCaseInstance = oldCovResult.getCaseByName(caseName);
			if(oldCaseInstance==null){
				covResult.addCase(newCaseInstance);
				covResult.addCaseCount();
			}else{
				CaseInstance caseInstance = new CaseInstance();
				caseInstance.setCaseName(caseName);
				caseInstance.setBrowser(oldCovResult.getBrowser());
				for(int j=0;j<newCaseInstance.getLineCount();j++){
					CaseLine caseLine = new CaseLine();
					newCaseLine = newCaseInstance.getCaseLine(j);
					String lineNumber = newCaseLine.getLineNumber();
					oldCaseLine = oldCaseInstance.getCaseLineByLineNumber(lineNumber);
					caseLine.setLineNumber(lineNumber);
					caseLine.setCovNumber(newCaseLine.getCovNumber()+oldCaseLine.getCovNumber());
					caseInstance.addCaseLine(caseLine);
					caseInstance.addLineCount();
				}
				covResult.addCase(caseInstance);
				covResult.addCaseCount();
			}
		}
		
		for(int i=0;i<oldCovResult.getCaseCount();i++){
			oldCaseInstance = oldCovResult.getCase(i);
			caseName = oldCaseInstance.getCaseName();
			if(newcovResult.getCaseByName(caseName)==null){
				covResult.addCase(oldCaseInstance);
				covResult.addCaseCount();
			}
		}
		return covResult;
	}
   

   public  static void generate(CovResult covResult,String browser,String path) {
	   
		Template template = null;
		try {
			template = ConfigurationFactory.getInstance()
			.getTemplate("coverage_test.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String filePath =path+"/tools/br/coveragereport/totalCoverage/coverage_report.xml";
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		Writer writer = null;
		
		try {
			 writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			        
		List<String> casenames=new ArrayList<String>(); 
		List<Object> lineNumbers=new ArrayList<Object>();
		List<Object> covNumbers=new ArrayList<Object>();		
		CaseInstance caseInstance = new CaseInstance();
		CaseLine caseLine = new CaseLine();
		String caseName;
		
		for(int i=0;i<covResult.getCaseCount();i++){
			caseName = covResult.getCase(i).getCaseName();
			casenames.add(caseName);
	   		List<String> lineNumber=new ArrayList<String>(); 
	   		List<String> covNumber=new ArrayList<String>(); 
	   		caseInstance = covResult.getCase(i);
	   		for(int j=0;j<caseInstance.getLineCount();j++){
	   			caseLine = caseInstance.getCaseLine(j);
	   			lineNumber.add(caseLine.getLineNumber());
	   			covNumber.add(caseLine.getCovNumber()+"");
	   		}
	  		 lineNumbers.add(lineNumber);
			 covNumbers.add(covNumber);
		}			
					
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("casenames", casenames);;
		map.put("lineNumbers", lineNumbers);
		map.put("covNumbers", covNumbers);
		map.put("browser", browser);
		
		try {
			template.process(map, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
