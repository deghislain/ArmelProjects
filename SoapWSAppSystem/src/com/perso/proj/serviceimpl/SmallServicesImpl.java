/**
 * 
 */
package com.perso.proj.serviceimpl;

import javax.jws.WebService;

import com.perso.proj.serviceinterf.SmallServices;

/**
 * @author Armel
 * This class implement the SmallServices interface
 *
 */
@WebService(endpointInterface = "com.perso.proj.serviceinterf.SmallServices", 
serviceName = "SmallServicesImpl", portName = "SmallServicesImplPort")
public class SmallServicesImpl implements SmallServices{
	
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
	
	int[] intArr = null;
	public String sort(String s) {
		return this.sortStrNum(s);
	}
	private String sortStrNum(String s) {
		intArr = new int[s.length()];
		for(int i =0; i<s.length(); i++) {
			intArr[i] = s.charAt(i+1);
		}
		this.quickSort(intArr, 0, intArr.length-1);
		
		return intArr.toString();
	}
	
	private void quickSort(int intArr[], int left, int right) {
		
		if(left < right) {
			int pi = partition(intArr, left, right);
			this.quickSort(intArr, left, pi-1);
			this.quickSort(intArr, left+1, right);
		}
		
	}
	
	private int partition(int[] intArr, int left, int right) {
		int pivot = intArr[right];
		int i = left-1;
		
		
		for(int index =left; index < right; index++) {
			if(intArr[index]>pivot) {
				i++;
				this.swap(intArr, i, index);
			}
		}
		intArr = this.swap(intArr, i+1, right);
		
		return i+1;
	}
	
	private int[] swap(int[] intArr, int index, int level) {
		int temp = 0;
		if(intArr[index]>intArr[level]) {
			temp = intArr[index];
			intArr[index] = intArr[level];
			intArr[level] = temp;
		}
		return intArr;
	}
	
	public String StorageServices(String fileNameOrUrl) {
		return this.storeFile(fileNameOrUrl);
	} 
	private String storeFile(String fNameOrURL) {
		return fNameOrURL;
	}
	
}
