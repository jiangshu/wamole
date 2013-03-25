package com.baidu.jstest.result.coverage;
import java.util.List;
import java.util.ArrayList;

/**
 * @author JiangShugang
 */

public class CaseInstance {
   private String browser;
   private String caseName;
   private List<CaseLine> caseLines;
   private int lineCount;
   
   public CaseInstance(){
	   caseLines=new ArrayList<CaseLine>();
	   lineCount=0;
   }
   
   public void addLineCount(){
	   lineCount++;
   }
   
   public int getLineCount(){
	   return this.lineCount;
   }
   
   public void setBrowser(String browser){
	   this.browser = browser;
   }
   
   public String getBrowser(){
	   return this.browser;
   }
   
   public void setCaseName(String caseName){
	   this.caseName = caseName;
   }
   
   public String getCaseName(){
	   return this.caseName;
   }
   
   public void setCaseLines(List<CaseLine> caseLine){
	   this.caseLines = caseLine;
   }
   
   public void addCaseLine(CaseLine caseLine){
	   this.caseLines.add(caseLine);
   }
   
   public List<CaseLine> getCaseLines(){
	   return this.caseLines;
   }
   
   public CaseLine getCaseLine(int index){
	   return this.caseLines.get(index);
   }
   
   public CaseLine getCaseLineByLineNumber(String lineNumber){
	   for(int i=0;i<lineCount;i++){
		   if(caseLines.get(i).getLineNumber().equals(lineNumber)){
			   return caseLines.get(i);
		   }
	   }
	   return null;
   } 
   
}
