/**
 * 
 */
package com.perso.proj.mapred.ws.serviceimpl;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import com.perso.proj.mapred.ws.serviceinterf.IMappingWebService;

/**
 * @author deghislain
 *
 */
@WebService(endpointInterface = "com.perso.proj.mapred.ws.serviceinterf.IMappingWebService", serviceName = "MappingWebServiceImpl", portName = "MappingWebServiceImplPort")
public class MappingWebServiceImpl implements IMappingWebService {

	public HashMap<String, String> map(List<String> words) {
		return this.mapWords(words);
	}

	private HashMap<String, String> mapWords(List<String> words) {
		HashMap<String, String> mappedData = new HashMap<String, String>();

		for (String key : words) {
			mappedData.put(key, "1");
		}
		return mappedData;
	}

}
