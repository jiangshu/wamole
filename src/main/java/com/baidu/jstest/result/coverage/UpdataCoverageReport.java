package com.baidu.jstest.result.coverage;

import java.io.File;   

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;   
import java.io.FileOutputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;   
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;   
import org.w3c.dom.Element;

import java.util.List;
/*
 * @ jiangshuguang
 * @ 更新covereage_report.xml
 * @  
 */

public class UpdataCoverageReport {   

    private Document doc;
    private NodeList lineList;
    Logger logger = LoggerFactory.getLogger(UpdataCoverageReport.class);
     
    public  void loadOldCoverage(String filename){
    	
       try{
	    	File f = new File(filename);   
	 	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
	 	    DocumentBuilder builder = factory.newDocumentBuilder();   
	 	    doc = builder.parse(f);
    	   
       }catch (Exception e) {   
	    e.printStackTrace();   
	   } 
       
       logger.info("updata coverage_report.xml" );
    }  
         
    public void saveNewCoverage(String filename,String addbrowser){
    	
    	NodeList testsuites = doc.getElementsByTagName("testsuite"); 
    	for(int j=0;j<testsuites.getLength();j++){
		      Element lineElement =(Element)testsuites.item(j);	   
		      lineElement.setAttribute("browser", addbrowser);
		 }  
  
    	try{
    		File f = new File(filename);
	        TransformerFactory transformerFactory=TransformerFactory.newInstance();
	        Transformer transformer=transformerFactory.newTransformer();
	        DOMSource domSource=new DOMSource(doc);
	        //transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        StreamResult result=new StreamResult(new FileOutputStream(f));
	        transformer.transform(domSource, result);
  	    }catch (Exception e) {   
  	    	e.printStackTrace();   
  	   }   
    }
    
    public NodeList getCase(String casename){
    	
    	NodeList testsuites = doc.getElementsByTagName("testsuite");  
  	    
	    for (int i = 0; i < testsuites.getLength(); i++) {   
		   if(((Element)testsuites.item(i)).getAttribute("casename").equals(casename)){	
			   lineList = testsuites.item(i).getChildNodes();
			   return lineList;
		   }		   
	   }
	   lineList = null; 
	   return null;   	
    }
  
    
    public void updataCoverage(int linenumber){
	   if(lineList != null){
	    	for(int j=0;j<lineList.getLength();j++){
			      Node lineNode = lineList.item(j);	   
				  if(lineNode.getNodeName().equals("#text")){
					   continue;	
				   }				   
				 Element line=(Element) lineList.item(j);    
				 if( Integer.parseInt(line.getAttribute("lineNumber"))<linenumber){
					   continue;
				 }
					   
				 if(Integer.parseInt(line.getAttribute("lineNumber"))==linenumber){
					 if(Integer.parseInt(line.getAttribute("covNumber"))==0){
							line.setAttribute("covNumber", "1");
					  }
					  break;   
				  }
			 }  
	   }
    }  
   
    
	public void createLine(String casename,List<String> lineNumber,List<String> covs){
		
		Element root=(Element)doc.getElementsByTagName("testsuites").item(0);
		Element testsuite = doc.createElement("testsuite");
		testsuite.setAttribute("name", casename);
		for(int i=0;i<lineNumber.size();i++)
		{
			Element line = doc.createElement("line");
			line.setAttribute("lineNumber", lineNumber.get(i));
            line.setAttribute("covNumber",covs.get(i));
            testsuite.appendChild(line);
		}
		
		root.appendChild(testsuite);   
	}
}	  
 

  
  

