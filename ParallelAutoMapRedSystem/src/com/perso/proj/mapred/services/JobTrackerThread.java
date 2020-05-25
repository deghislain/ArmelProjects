/**
 * 
 */
package com.perso.proj.mapred.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.perso.proj.mapred.ws.entity.KeyValuePair;
import com.perso.proj.mapred.ws.serviceinterf.ICombiningWebService;

/**
 * @author deghislain
 *
 */
public class JobTrackerThread{
	private IMapReduceBufferService mrbService;
	
	private ITaskTrackerService ttService;
	
	//This holds the path where we store the resuts
	private String resultStoragePath;
	
	//Indicates when to stop the process
	private boolean isJobDone;
	
	private INameNodeService nameNodeService;
	
	
	
	public JobTrackerThread(IMapReduceBufferService mrbs, ITaskTrackerService tts, INameNodeService nns, String rsPath ) {
		this.mrbService = mrbs;
		this.ttService = tts;
		this.resultStoragePath = rsPath;
		this.isJobDone = false;
		this.nameNodeService = nns;
	}

	//@Override //TODO remove comment
	public void run() {
		while(!this.isJobDone) {
			List<String> part = this.mrbService.getNextPart();
			if( null!= part) {
				this.doTheMapping(part);
			}
			
			if(this.mrbService.getTotMapped()>0) {
				this.doTheReducing();
			}
			
			if(this.mrbService.getTotReduced()>=0) {
				HashMap<String, Integer> combBuf = new HashMap<String, Integer>();
				synchronized (combBuf) {
					this.doTheCombining(this.resultStoragePath, combBuf);
				}
			}
		}
	}
	
	private void doTheMapping(List<String> part) {
		List<KeyValuePair> mapped = new ArrayList<KeyValuePair>();
		if(null != part) {
			mapped = this.ttService.map(part);
			if(null != mapped && !mapped.isEmpty()) {
				this.mrbService.addMappedDataToBuffer(mapped);
			}
		}
	}
	
	private void doTheReducing() {
		List<KeyValuePair> mapped = this.mrbService.getNextMappedData();
		if(null != mapped) {
			HashMap<String, Integer> reduced = this.ttService.reduce(mapped);
			if(null != reduced && !reduced.isEmpty()) {
				this.mrbService.addReducedDataToBuffer(reduced);
			}
		}
	}
	
	private void doTheCombining(String path, HashMap<String, Integer> combBuf) {
		combBuf = this.mrbService.getCombBuffer();
		HashMap<String, Integer> rb1 = this.mrbService.getNextReducedData();
		HashMap<String, Integer> rb2 = new HashMap<String, Integer>();
		
		
		if((combBuf != null && !combBuf.isEmpty()) && (rb1 == null || rb1.isEmpty())) {//No more reduced data to combine
			this.nameNodeService.storeResults(combBuf, path); //here we store the map reduce result on a xml file
			this.isJobDone = true;
			return;
		}
		
		//This is the first combining, so we need a second set of data for the combining
		if((combBuf == null || combBuf.isEmpty()) && (rb1 != null && !rb1.isEmpty())) {
			rb2 = this.mrbService.getNextReducedData();
			
			if(rb2 != null && !rb2.isEmpty()) {//we only have one set of reduced data for now
				ICombiningWebService proxy = getCombiningProxy();
				
				if(proxy != null) {
					combBuf = proxy.combine(rb1, rb2);
					if(combBuf != null && !combBuf.isEmpty()) {
						this.mrbService.updateCombBuffer(combBuf);
					}
				}
				
			}else {//we put our first set of reduced data into combined buffer
				this.mrbService.updateCombBuffer(rb1);
			}
			
		}
		
		if((combBuf != null && !combBuf.isEmpty()) && (rb1 != null && !rb1.isEmpty())) {
			ICombiningWebService proxy = getCombiningProxy();
			
			if(proxy != null) {
				combBuf = proxy.combine(combBuf, rb1);
				if(combBuf != null && !combBuf.isEmpty()) {
					this.mrbService.updateCombBuffer(combBuf);
				}
			}

		}
		
	}
	
	private ICombiningWebService getCombiningProxy() {
		Service combServices = null;
		ICombiningWebService proxy = null;
		try {
			URL url = new URL("http://localhost:8080/ParallelAutoMapRedSystem/services/CombiningWebServiceImplPort?wsdl");
			//we use targetNamespace and serviceName from WS (IReduceWebService interface) to create the QName
			QName qn = new QName("http://serviceinterf.ws.mapred.proj.perso.com", "CombiningWebServiceImpl");
			combServices = Service.create(url, qn);
			proxy = combServices.getPort(ICombiningWebService.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return proxy;
	}

}
