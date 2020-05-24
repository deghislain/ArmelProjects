/**
 * 
 */
package com.perso.proj.mapred.ws.serviceimpl;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import com.perso.proj.mapred.ws.entity.KeyValuePair;
import com.perso.proj.mapred.ws.serviceinterf.IReduceWebService;

/**
 * @author deghislain
 * This is a web service that does the reduce when the mapping is completed 
 *
 */

@WebService(endpointInterface = "com.perso.proj.mapred.ws.serviceinterf.IReduceWebService", serviceName = "ReduceWebServiceImpl", portName = "ReduceWebServiceImplPort")
public class ReduceWebServiceImpl implements IReduceWebService{

	@Override
	//This method does the reduce operation
	public HashMap<String, Integer> reduce(List<KeyValuePair> mappedWords) {
		return this.getReducedMap(mappedWords);
	}
	
	//This method implements the reduce operation 
	private HashMap<String, Integer> getReducedMap(List<KeyValuePair> mappedWords){
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		for(KeyValuePair kvp : mappedWords){
			if(null == results.get(kvp.getKey()) || results.get(kvp.getKey()).equals("")) {
				int freq = countNumOccurrences(kvp, mappedWords);
				results.put(kvp.getKey(), freq);
			}
		}
	
		return results;
	}
	
	//This method add the number of occurrences of a single word
	private int countNumOccurrences(KeyValuePair key, List<KeyValuePair> mappedList) {
		int numOcc = 0;
		for(KeyValuePair currKvp : mappedList){
			if(currKvp.getKey().equals(key.getKey())) {
				numOcc += currKvp.getValue();
			}
		}
		return numOcc;
		
	}

}
