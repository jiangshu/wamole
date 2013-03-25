package com.baidu.jstest.result.coverage;

import java.util.List;
import java.util.ArrayList;

/**
 * @author JiangShugang
 */

public class CovResult {
	
	   private String browser;
	   private List<CaseInstance> cases;
	   private int caseCount;
	   
	   public CovResult(){
		   cases=new  ArrayList<CaseInstance>();
		   caseCount=0;
	   }
	   
	   public void addCaseCount(){
		   caseCount++;
	   }
	   public void setCaseCount(int caseCount){
		   this.caseCount=caseCount;
	   }
	   
	   public int getCaseCount(){
		   return this.caseCount;
	   }
	   
	   public void setBrowser(String browser){
		   this.browser=browser;
	   }
	   
	   public String getBrowser(){
		   return this.browser;
	   }
	   
	   public void setCases(List<CaseInstance> cases){
		   this.cases = cases;
	   }
	   
	   public List<CaseInstance> getCases(){
		   return this.cases;
	   }
	   
	   public void addCase(CaseInstance caseInstance){
		   this.cases.add(caseInstance);
	   }
	   
	   public CaseInstance getCase(int index){
		   return this.cases.get(index);
	   }
	   
	   public CaseInstance getCaseByName(String caseName){
		   for(int i=0;i<caseCount;i++){
			   if(cases.get(i).getCaseName().equals(caseName)){
				   return cases.get(i);
			   }
		   }
		   return null;
	   }
} 
