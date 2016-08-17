package com.food.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	
	public static String createNewFile(File file){
		String lastName = "jpg";
		if(file.getName().split("\\.").length>1){
			lastName = file.getName().split("\\.")[1];
		}
		InputStream inStream;
		String newFileName = System.currentTimeMillis()+"."+lastName;
		try {
			inStream = new FileInputStream(file);
	        FileOutputStream fs = new FileOutputStream(file.getParent()+"/"+newFileName); 
	        byte[] buffer = new byte[1444]; 
	        int bytesum = 0; 
	        int byteread = 0; 
	        while ( (byteread = inStream.read(buffer)) != -1) { 
	             bytesum += byteread;
	             fs.write(buffer, 0, byteread); 
	        } 
	        inStream.close(); 
	        fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
         
		file.delete();
		return newFileName;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
