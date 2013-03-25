package com.baidu.jstest.result.coverage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class SourceCodeTransiton {  //解决jscoverage编码问题
   
	public static void  codeTransition(String path,String sor,String des){
	   try{
		    FileInputStream infile = new  FileInputStream(new File(path));
			BufferedReader br = new BufferedReader(new InputStreamReader(infile));
			String line="";
			String ta="";
		
			while((line=br.readLine())!=null){
				if((line.trim()).equals(sor.trim())){
					ta=ta+des.trim()+"\n";
				}    
				else{
					ta=ta+line+"\n";
				}	
			}				
			
			infile.close();
			br.close();
			FileOutputStream outFile=new FileOutputStream(new File(path));
			PrintWriter source = new PrintWriter(new OutputStreamWriter(outFile),true); 
			source.println(ta);
			outFile.close();
			source.close();
	   }catch(IOException e){
		   e.printStackTrace();
	   }     
   }
}
