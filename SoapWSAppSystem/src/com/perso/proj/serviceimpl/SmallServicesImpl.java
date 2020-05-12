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
		return c;
	}
	
	public int convertF2C(int f) {
		return this.F2C(f);
	}
	private int F2C(int f) {
		return f;
	}
	
	public String sort(String s) {
		return this.sortStrNum(s);
	}
	private String sortStrNum(String s) {
		return s;
	}
	
	public String StorageServices(String fileNameOrUrl) {
		return this.storeFile(fileNameOrUrl);
	} 
	private String storeFile(String fNameOrURL) {
		return fNameOrURL;
	}
	
}
