package com.baidu.jstest.result.coverage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.page.ConfigurationFactory;
import com.baidu.jstest.result.Result;
import com.baidu.jstest.result.ResultTableImpl;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.util.Map;
import java.util.HashMap;
/**
 * @author JiangShugang
 */
public class CoverageResultTableImpl extends ResultTableImpl {
	private Map<String,String> jsons;
	private Map<String,CovResult> covMap;
	private CovResult totalCov;
	private String srcPath;
	private String testPath;
	private String covPath;
	String browsers;
	boolean flag;

	Logger logger = LoggerFactory.getLogger(CoverageResultTableImpl.class);

	public CoverageResultTableImpl(String id, List<String> browserList,
			List<Kiss> kissList, int interval, String savePath, String srcPath,
			String testPath,String covPath) {

		super(id, browserList, kissList, interval, savePath);
		this.srcPath = srcPath;
		this.testPath = testPath;
		this.covPath = covPath;
		browsers="";
		flag=true;
		covMap = new HashMap<String,CovResult>();
	}

	public synchronized String store(Result result) {
		String next = null;
		next = super.store(result);
		if(!(result.getBrowser().equals(""))&& result.getBrowser()!=null && result.getCov()!=null && 
				!"".equals(result.getCov()) && result.getCov().startsWith("{")&& result.getCov().endsWith("}") ){		
			if(covMap.get(result.getBrowser())==null){
			   covMap.put(result.getBrowser(),parseCovObject(result.getCov(), result.getBrowser()));
			}else{
				CovResult oldCovResult = covMap.get(result.getBrowser());
				CovResult newCovResult = parseCovObject(result.getCov(), result.getBrowser());
				covMap.put(result.getBrowser(),covMerge(oldCovResult, newCovResult));
			}		
		}
		return next;
	}
	
	public boolean isDead(String browser) {
		if (deadLine.isDead()) {
			
			report();	
			if(covMap.get(browser)!=null){
				browsers=browsers+" "+browser;
				if(totalCov==null){
					totalCov=covMap.get(browser);
				}else{
					totalCov = covMerge(totalCov,covMap.get(browser));
				}
				generate(totalCov);
			}
			
			if(flag){
				flag=false;
		    	String sourcePath=testPath+"/tools/br/coveragereport/totalCoverage/source.js";
		    	File file= new File(sourcePath);
		    	if(file.exists())
		    		file.delete();
			    CreatSource source = new CreatSource(covPath,testPath);  
			    source.create();	
			}
        }		
		return deadLine.isDead();
	}  
    	
   public CovResult parseCovObject(String covJson,String browser){		
		return Coverage.parseCovObject(covJson,browser);
	}
     
   public synchronized CovResult covMerge(CovResult oldCovResult,CovResult newcovResult){
	    return Coverage.covMerge(oldCovResult, newcovResult); 
	}
   
   public synchronized void generate(CovResult covResult) {
	   		Coverage.generate(covResult, browsers, testPath);
	}
}
