/**
 * 
 */
package com.perso.proj.mapred.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.perso.proj.mapred.ws.entity.KeyValuePair;
import com.perso.proj.mapred.ws.serviceinterf.ICombiningWebService;

/**
 * @author deghislain
 *
 */
public class JobTrackerThread extends Thread{
	protected final Logger logger = LogManager.getLogger(JobTrackerThread.class);
	private IMapReduceBufferService mrbService;
	
	private ITaskTrackerService ttService;
	
	//This holds the path where we store the resuts
	private String resultStoragePath;
	
	private INameNodeService nameNodeService;
	
	
	
	public JobTrackerThread(IMapReduceBufferService mrbs, ITaskTrackerService tts, INameNodeService nns, String rsPath ) {
		this.mrbService = mrbs;
		this.ttService = tts;
		this.resultStoragePath = rsPath;
		this.nameNodeService = nns;
	}

	@Override 
	public void run() {
		logger.info("Started " +this.getName());
		while(!this.mrbService.getIsJobDone()) {
			try {
				Random rand = new Random();
				int waitfor = rand.nextInt(2000);
				Thread.sleep(waitfor);
				List<String> part = this.mrbService.getNextPart();
				if(null!= part) {
					this.doTheMapping(part);
				}
				
				if(this.mrbService.getTotMapped()>=0) {
					this.doTheReducing();
				}
				
				if(this.mrbService.getTotReduced()>=0) {
					HashMap<String, Integer> combBuf = new HashMap<String, Integer>();
					synchronized (combBuf) {
						this.doTheCombining(this.resultStoragePath, combBuf);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		if(this.mrbService.getIsJobDone() && !this.mrbService.getIsResultsStored()) {
			try {
				Thread.sleep(5000);
				this.storeResults();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
		}
		logger.info("End " + this.getName());
	}
	
	private void doTheMapping(List<String> part) {
		logger.info("doTheMapping started by " + this.getName());
		List<KeyValuePair> mapped = new ArrayList<KeyValuePair>();
		if(null != part) {
			mapped = this.ttService.map(part);
			if(null != mapped && !mapped.isEmpty()) {
				this.mrbService.addMappedDataToBuffer(mapped);
			}
		}
		logger.info("doTheMapping Ended by " + this.getName());
	}
	
	private void doTheReducing() {
		logger.info("doTheReducing started by " + this.getName());
		List<KeyValuePair> mapped = this.mrbService.getNextMappedData();
		if(null != mapped) {
			HashMap<String, Integer> reduced = this.ttService.reduce(mapped);
			if(null != reduced && !reduced.isEmpty()) {
				this.mrbService.addReducedDataToBuffer(reduced);
			}
		}
		logger.info("doTheReducing Ended by " + this.getName());
	}
	
	private void doTheCombining(String path, HashMap<String, Integer> combBuf) {
		logger.info("doTheCombining started by " + this.getName());
		combBuf = this.mrbService.getCombBuffer();
		HashMap<String, Integer> rb1 = this.mrbService.getNextReducedData();
		HashMap<String, Integer> rb2 = new HashMap<String, Integer>();
		
		
		if(!this.mrbService.getIsJobDone() && (combBuf != null && !combBuf.isEmpty()) && this.mrbService.getTotMapped() <=0 && this.mrbService.getTotReduced() <= 0) {//No more reduced data to combine
			//this.nameNodeService.storeResults(combBuf, path); //here we store the map reduce result on a xml file
			this.mrbService.setIsJobDone(true);
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
		logger.info("doTheCombining Ended by " + this.getName());
	}
	
	private ICombiningWebService getCombiningProxy() {
		logger.info("Entered getCombiningProxy Method");
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
		logger.info("Exiting getCombiningProxy Method");
		return proxy;
	}
	
	private synchronized void storeResults() {
		logger.info("Entered storeResults Method");
			if(!this.mrbService.getIsResultsStored()) {
				this.nameNodeService.storeResults(this.mrbService.getCombBuffer(), this.resultStoragePath); 
				System.out.println("storeResults done by " + this.getName());
				this.mrbService.setIsResultsStored(true);
			}
		logger.info("Exiting storeResults Method");
	}

}
