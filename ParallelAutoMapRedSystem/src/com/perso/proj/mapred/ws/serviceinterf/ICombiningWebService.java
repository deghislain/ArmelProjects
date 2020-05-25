/**
 * 
 */
package com.perso.proj.mapred.ws.serviceinterf;

import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author deghislain
 *
 */
@WebService(targetNamespace = "http://serviceinterf.ws.mapred.proj.perso.com")
public interface ICombiningWebService {
 @WebMethod(operationName = "combine", action = "urn:Combine")
public HashMap<String, Integer> combine(@WebParam(name = "arg0") HashMap<String, Integer> combMap, @WebParam(name = "arg1") HashMap<String, Integer> redMap);
}
