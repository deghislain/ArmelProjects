/**
 * 
 */
package com.perso.proj.mapred.ws.serviceinterf;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author deghislain
 *
 */
@WebService(targetNamespace = "http://serviceinterf.ws.mapred.proj.perso.com")
public interface IMappingWebService {
	@WebMethod(operationName = "map", action = "urn:Map")
	public HashMap<String, String> map(@WebParam(name = "arg0") List<String> words);
}
