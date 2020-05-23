/**
 * 
 */
package com.perso.proj.mapred.services;

import java.util.HashMap;
import java.util.List;

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
			}else {//TODO remove after test
				this.isJobDone = true; 
			}
			
			if(this.mrbService.getTotMapped()>0) {
				this.doTheReducing();
			}
			
			if(this.mrbService.getTotMapped() <=0 && this.mrbService.getTotReduced()<=0) {
				this.doTheCombining(this.resultStoragePath);
			}
		}
	}
	
	private void doTheMapping(List<String> part) {
		HashMap<String, String> mapped = new HashMap<String, String>();
		if(null != part) {
			mapped = this.ttService.map(part);
			if(null != mapped && !mapped.isEmpty()) {
				this.mrbService.addMappedDataToBuffer(mapped);
			}
		}
	}
	
	private void doTheReducing() {
		
	}
	
	private void doTheCombining(String path) {
		
	}

}
