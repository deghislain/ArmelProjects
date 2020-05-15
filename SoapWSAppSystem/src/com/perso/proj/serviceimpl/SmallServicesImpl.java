/**
 * 
 */
package com.perso.proj.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.jws.WebService;

import org.apache.commons.io.FileUtils;

import com.perso.proj.serviceinterf.SmallServices;

/**
 * @author Armel
 * This class implement the SmallServices interface
 *
 */
@WebService(endpointInterface = "com.perso.proj.serviceinterf.SmallServices", 
serviceName = "SmallServicesImpl", portName = "SmallServicesImplPort")
public class SmallServicesImpl implements SmallServices{
	 private static final String UPLOAD_DIRECTORY = "uploadedFiles";
	
	public int convertC2F(int c) {
		return this.C2F(c);
	}
	private int C2F(int c) {
		float f = c*9f/5+32;
		return Math.round(f);
	}
	
	public int convertF2C(int f) {
		return this.F2C(f);
	}
	private int F2C(int f) {
		float c = (f-32f)*5/9;
		return Math.round(c);
	}
	
	String[] strArr = null;
	public String sort(String s) {
		return this.sortStrNum(s);
	}
	private String sortStrNum(String s) {
		strArr = new String[s.length()];
		strArr = s.split("\\,");
		String strNum = "";
		try { // we might have some number format exception
			this.quickSort(strArr, 0, strArr.length-1);
		}catch(Exception e) {
			strNum = null;
		}
		
		if(strNum != null) {
			for(int index = 0; index<strArr.length; index++) {
				if(index != strArr.length) {//to avoid a comma after the last number
					strNum += strArr[index] + ",";
				}else {
					strNum += strArr[index];
				}
				
			}
		}else {
			// this is return if the input has some invalid numbers that can generate number format exception
			strNum = "Invalid Input"; 
		}
		
		return strNum;
	}
	
	private void quickSort(String strArr[], int left, int right) {
		
		if(left < right) {
			int pi = partition(strArr, left, right);
			this.quickSort(strArr, left, pi-1);
			this.quickSort(strArr, left+1, right);
		}
		
	}
	
	private int partition(String[] strArr, int left, int right) {
		int pivot = Integer.parseInt(strArr[right]);
		int i = left-1;
		
		
		for(int index =left; index < right; index++) {
			if(Integer.parseInt(strArr[index]) <pivot) {
				i++;
				this.swap(strArr, i, index);
			}
		}
		strArr = this.swap(strArr, i+1, right);
		
		return i+1;
	}
	
	private String[] swap(String[] strArr, int index, int level) {
		String temp = "0";
		if(Integer.parseInt(strArr[index])>Integer.parseInt(strArr[level])) {
			temp = strArr[index];
			strArr[index] = strArr[level];
			strArr[level] = temp;
		}
		return strArr;
	}
	
	public String StorageServices(String fileNameOrUrl) {
		return this.storeFile(fileNameOrUrl);
	} 
	private String storeFile(String filePath) {
		String result = "OK";
		File srcFile = new File(filePath);
		File destDir = new File(UPLOAD_DIRECTORY);
		try {
			 Path path = Paths.get(UPLOAD_DIRECTORY);

		        if (!Files.exists(path)) {
		            
		            Files.createDirectory(path);
		        }
			FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "KO";
		}
		
		return result;
	}
	
}
