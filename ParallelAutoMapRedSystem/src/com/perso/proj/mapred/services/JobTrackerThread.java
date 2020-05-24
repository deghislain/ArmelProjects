/**
 * 
 */
package com.perso.proj.mapred.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.perso.proj.mapred.ws.entity.KeyValuePair;

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
	
	
	
	public JobTrackerThread(IMapReduceBufferService mrbs, ITaskTrackerService tts, String rsPath ) {
		this.mrbService = mrbs;
		this.ttService = tts;
		this.resultStoragePath = rsPath;
		this.isJobDone = false;
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
			}else {//TODO remove after test
				this.isJobDone = true; 
			}
			
			if(this.mrbService.getTotMapped() <=0 && this.mrbService.getTotReduced()<=0) {
				this.doTheCombining(this.resultStoragePath);
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
	
	private void doTheCombining(String path) {
		
	}

}
