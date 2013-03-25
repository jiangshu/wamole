package com.baidu.jstest.result.coverage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CopyFile {
   
    public static void excutor(String oldPath, String newPath) { 
        try { 
            int bytesum = 0; 
            int byteread = 0; 
            new File(newPath).delete();
            File oldfile = new File(oldPath); 
            if (oldfile.exists()) { 
                InputStream inStream = new FileInputStream(oldPath); 
                FileOutputStream fs = new FileOutputStream(newPath); 
                byte[] buffer = new byte[1444]; 
                while ( (byteread = inStream.read(buffer)) != -1) { 
                    bytesum += byteread;                     
                    fs.write(buffer, 0, byteread); 
                } 
                inStream.close(); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}
