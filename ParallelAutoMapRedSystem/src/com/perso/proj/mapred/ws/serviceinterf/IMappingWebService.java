/**
 * 
 */
package com.perso.proj.mapred.ws.serviceinterf;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.perso.proj.mapred.ws.entity.KeyValuePair;

/**
 * @author deghislain
 *
 */
@WebService(targetNamespace = "http://serviceinterf.ws.mapred.proj.perso.com")
public interface IMappingWebService {
	@WebMethod(operationName = "map", action = "urn:Map")
	public List<KeyValuePair> map(@WebParam(name = "arg0") List<String> words);
}
