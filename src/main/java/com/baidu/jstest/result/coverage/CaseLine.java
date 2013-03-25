package com.baidu.jstest.result.coverage;

/**
 * @author JiangShugang
 */

public class CaseLine {
   private String lineNumber;
   private int covNumber;
   
   public void setLineNumber(String lineNumber){
	   this.lineNumber = lineNumber;
   }
   public String getLineNumber(){
	   return this.lineNumber;
   }
   
   public void setCovNumber(int covNumber){
	   this.covNumber = covNumber;
   }
   public int getCovNumber(){
	   return this.covNumber;
   }
  
}
