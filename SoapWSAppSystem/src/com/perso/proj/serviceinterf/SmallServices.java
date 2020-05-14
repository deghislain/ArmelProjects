/**
 * this package contains interfaces for small services
 */
package com.perso.proj.serviceinterf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Armel 
 * interface for a variety of small services
 *
 */
@WebService(targetNamespace = "http://serviceinterf.proj.perso.com")
public interface SmallServices {
	/**
	 * This service convert a temperature in degree Celsius to Fahrenheit 
	 * @param take in input the temperature c in Celsius and
	 * @return the equivalent in Fahrenheit
	 */
	@WebMethod(operationName = "convertC2F", action = "urn:ConvertC2F")
	public int convertC2F(@WebParam(name = "arg0") int c);
	/**
	 * This service convert a temperature in degree Fahrenheit to Celsius
	 * @param take in input the temperature f in Fahrenheit and
	 * @return the equivalent in Celssius
	 */
	@WebMethod(operationName = "convertF2C", action = "urn:ConvertF2C")
	public int convertF2C(@WebParam(name = "arg0") int f);
	/**
	 * This service sort a string of numbers
	 * @param take in input the unsorted string and 
	 * @return return a sorted one using a sorted algorithm 
	 */
	@WebMethod(operationName = "sort", action = "urn:Sort")
	public String sort(@WebParam(name = "arg0") String s);
	/**
	 * This service load a local file to a server
	 * @param Take in input a fileNameOrUrl and 
	 * @return return the URL of the file in the server
	 */
	@WebMethod(operationName = "StorageServices", action = "urn:StorageServices")
	public String StorageServices(@WebParam(name = "arg0") String fileNameOrUrl);
	
	
}
