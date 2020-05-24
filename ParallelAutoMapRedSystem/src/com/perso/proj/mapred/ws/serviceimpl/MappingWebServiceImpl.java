/**
 * 
 */
package com.perso.proj.mapred.ws.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.perso.proj.mapred.ws.entity.KeyValuePair;
import com.perso.proj.mapred.ws.serviceinterf.IMappingWebService;

/**
 * @author deghislain
 *
 */
@WebService(endpointInterface = "com.perso.proj.mapred.ws.serviceinterf.IMappingWebService", serviceName = "MappingWebServiceImpl", portName = "MappingWebServiceImplPort")
public class MappingWebServiceImpl implements IMappingWebService {

	public List<KeyValuePair> map(List<String> words) {
		return this.mapWords(words);
	}

	private List<KeyValuePair> mapWords(List<String> words) {
		List<KeyValuePair> mappedData = new ArrayList<KeyValuePair>();

		for (String key : words) {
			KeyValuePair kvp = new KeyValuePair();
			kvp.put(key, 1);
			mappedData.add(kvp);
		}
		return mappedData;
	}

}
