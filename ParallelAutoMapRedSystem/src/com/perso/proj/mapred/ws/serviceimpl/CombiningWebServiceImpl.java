/**
 * 
 */
package com.perso.proj.mapred.ws.serviceimpl;

import java.util.HashMap;

import javax.jws.WebService;

import com.perso.proj.mapred.ws.serviceinterf.ICombiningWebService;

/**
 * @author deghislain
 *
 */
@WebService(endpointInterface = "com.perso.proj.mapred.ws.serviceinterf.ICombiningWebService", serviceName = "CombiningWebServiceImpl", portName = "CombiningWebServiceImplPort")
public class CombiningWebServiceImpl implements ICombiningWebService {

	@Override
	public HashMap<String, Integer> combine(HashMap<String, Integer> combMap, HashMap<String, Integer> redMap) {

		return this.getTheCombineList(combMap, redMap);
	}

	private HashMap<String, Integer> getTheCombineList(HashMap<String, Integer> combMap, HashMap<String, Integer> redMap) {
		
		for (String key : redMap.keySet()) {
				Integer combMapValue = combMap.get(key);
				Integer redMapValue = redMap.get(key);
				
				if(redMapValue != null && combMapValue == null) {
					combMap.put(key, redMapValue);
				}else if(redMapValue != null && combMapValue != null) {
					redMapValue += combMapValue;
					combMap.put(key, redMapValue);
				}
		}
		return combMap;
	}
}
