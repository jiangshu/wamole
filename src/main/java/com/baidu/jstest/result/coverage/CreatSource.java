package com.baidu.jstest.result.coverage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @ auth jiangshuguang
 * @ 用于生成_$jscoverage对象的source，查看覆盖率的时候能查看具体的代码的覆盖情况
 * @ 实现方法：通过扫描项目下所有经过jscoverage编译的js源代码，找出其中包含_$jscoverage[jsfilename].source的行
 */

public class CreatSource {
   
	List<File> list = new ArrayList<File>();
	Logger logger = LoggerFactory.getLogger(CreatSource.class);
	private String srcdir;
	private String testdir;
	
	public CreatSource(String srcdir,String testdir){
		
		this.srcdir=srcdir;
		this.testdir=testdir;   	
    }
	
	public static boolean isfind(String ora,String regex)
	{
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(ora);
	
		if(m.find()){
			return true;
		}	
		else{
			return false;
		}
	}
    

	public static String StringReg(String ora,String regex)
	{
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(ora);
		String number;
		while(m.find())
		{			
			number=m.group();
			p=Pattern.compile(".+");
			m=p.matcher(number);	
					
			while(m.find())
			{	/*			
				number=m.group();
				p=Pattern.compile("[a-zA-Z0-9]*");
				m=p.matcher(number);
				//System.out.println(m.group().toString());
				while(m.find())
				{
			       return m.group().toString();
			    }*/
				return m.group().toString();
			}			
		}	
		 return null;
	}
	
    public void listFiles(){

    	 String path=srcdir;
         File file = new File(path);
         File files[] = null;
         files = file.listFiles();
         addFile(files);
         isDirectory(files);
    }
	
   
    public void isDirectory(File[] files) {
        for (File s : files) {
            if (s.isDirectory()) {
	                File file[] = s.listFiles();
	                addFile(file);
	                isDirectory(file);
	                continue;
	            }
	        }
	    }
	  
    
	  public void addFile(File file[]) {
	       for (int index = 0; index < file.length; index++) {
	            list.add(file[index]);
	        }
	   }
     
	//生成项目source的总的js文件
    public boolean create(){
	   PrintWriter Psource;
	   String path=testdir+"/tools/br/coveragereport/totalCoverage/source.js";
       listFiles();	
       System.out.println("source.js path :"+path );
    	try{	    		
    		FileOutputStream outFile=new FileOutputStream(path);
    		Psource = new PrintWriter(new OutputStreamWriter(outFile),true);  
    		Psource.println("function loadSource(){");
		
            for(File jsFile : list){
               if(jsFile.isDirectory() || !jsFile.getName().endsWith(".js")){
            	   continue;
               }
               
               if(jsFile.getName().equals("import.js")||jsFile.getName().equals("jscoverage.js")||jsFile.getName().equals("source.js")||
            		   jsFile.getName().equals("jsloader.js")){
            	   continue;
               }
            	   
    		      try{		
    				    FileInputStream file = new FileInputStream(jsFile);
    	    			BufferedReader br = new BufferedReader(new InputStreamReader(file));		
    	    			String data;	
    	    			
    	    			while((data = br.readLine())!=null){
    	    				  if(isfind(data,"_\\$jscoverage\\['.+'\\]\\.source"))
    	    				   {
    	    					  String prefix =StringReg(data,"\\[.+\\]\\.source");
    	    					  prefix =StringReg(prefix,"\\[.+\\]");
                                  prefix="_$jscoverage"+prefix+" && (";
                                  data=data.substring(0, data.length()-1);
                                  data=prefix + data +");";
                                  Psource.println(data);
//    	    					  System.out.println(data);
                                  break;
    	    				   }  
    	    				}
    	    			   br.close();
    	    	       }catch(IOException e){   	    	    	   
    	    	    	   System.out.println(e.toString());
    	    	       }	        	 
            }
             Psource.println("}");
    	     Psource.close();  
	       }catch (IOException e){
	    	   System.out.println(e.toString());
	       }	 
    	return true;
    }
}




