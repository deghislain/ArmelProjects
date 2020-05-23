/**
 * 
 */
package com.perso.proj.mapred.services;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.perso.proj.mapred.ws.serviceinterf.IMappingWebService;


/**
 * @author deghislain
 *
 */
public class TaskTrackerService implements ITaskTrackerService{

	@Override
	public HashMap<String, String> map(List<String> words) {
		IMappingWebService mProxy = getMappingProxy();
		if(null != mProxy) {
			return  mProxy.map(words);
		}
		return null;
	}

	@Override
	public HashMap<String, String> reduce(HashMap<String, String> mappedMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private IMappingWebService getMappingProxy() {
		Service mappingServices = null;
		IMappingWebService proxy = null;
		try {
			URL url = new URL("http://localhost:8080/ParallelAutoMapRedSystem/services/MappingWebServiceImplPort?wsdl");
			//we use targetNamespace and serviceName from WS (IMappingWebService interface) to create the QName
			QName qn = new QName("http://serviceinterf.ws.mapred.proj.perso.com", "MappingWebServiceImpl");
			mappingServices = Service.create(url, qn);
			proxy = mappingServices.getPort(IMappingWebService.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return proxy;
	}
	 
}
