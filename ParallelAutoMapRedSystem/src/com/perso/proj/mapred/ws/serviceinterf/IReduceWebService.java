/**
 * 
 */
package com.perso.proj.mapred.ws.serviceinterf;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.perso.proj.mapred.ws.entity.KeyValuePair;

/**
 * @author deghislain
 *This class is a web service that will be called after the mapping in order to achieve the reduce operation
 */
@WebService(targetNamespace = "http://serviceinterf.ws.mapred.proj.perso.com")
public interface IReduceWebService {
@WebMethod(operationName = "reduce", action = "urn:Reduce")
public HashMap<String, Integer> reduce(@WebParam(name = "arg0") List<KeyValuePair> mapHash);
}
