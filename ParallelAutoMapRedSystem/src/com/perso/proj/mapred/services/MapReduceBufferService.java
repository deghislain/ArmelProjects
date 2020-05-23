/**
 * 
 */
package com.perso.proj.mapred.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author deghislain
 * This class play the normal role of a buffer necessary in multithreading environment
 *
 */
public class MapReduceBufferService implements IMapReduceBufferService{
	
	//This buffer contains the data after partition
	private List<List<String>> partsBuffer;
	
	//This buffer contains the data after mapping
	private List<HashMap<String, String>>mappedBuffer = new ArrayList<HashMap<String,String>>();
	
	//This buffer contains the data after reducing process
	private List<HashMap<String, String>>reducedBuffer = new ArrayList<HashMap<String,String>>();
	
	//This buffer contains the data after combining process
	private HashMap<String, String> combinedBuffer;

	//This property helps determine how many data we still have to map
	private int totMapped;
	
	//This property helps determine how many data we still have to reduce
	private int totReduced;
	
	public MapReduceBufferService(List<List<String>> p) {
		this.partsBuffer = p;
		this.totMapped = p.size();
		this.totReduced = p.size();
	}

	@Override
	public List<String> getNextPart() {
		//we synchronize to avoid the situation were 2 threads get and process the same part
		synchronized (this.partsBuffer) {
			if(null != this.partsBuffer && !this.partsBuffer.isEmpty()) {
				List<String> currPart = this.partsBuffer.get(0);
				//we remove current part from the list to avoid double processing
				this.partsBuffer.remove(0);
			return currPart;
			}else {
				return null;// indicate the mapping is completed
			}
		}
		
	}

	@Override
	public void addMappedDataToBuffer(HashMap<String, String> map) {
		synchronized (this.mappedBuffer) {
			this.mappedBuffer.add(map);
			int num = this.mappedBuffer.size();
		}
		
	}

	@Override
	public HashMap<String, String> getNextMappedData() {
		
		//we synchronize to avoid the situation were 2 threads get and process the data
				synchronized (this.mappedBuffer) {
					if(null != this.mappedBuffer && !this.mappedBuffer.isEmpty()) {
						HashMap<String, String> currMap = this.mappedBuffer.get(0);
						//we remove current mapped data from the list to avoid double processing
						this.mappedBuffer.remove(0);
						this.totMapped -=1;//we keep track of the number of part being mapped
						return currMap;
					}else {
						return null;
					}
				}
	}

	@Override
	public void addReducedDataToBuffer(HashMap<String, String> rmap) {
		synchronized (this.reducedBuffer) {
			this.reducedBuffer.add(rmap);
		}
		
	}

	@Override
	public HashMap<String, String> getNextReducedData() {
		//we synchronize to avoid the situation were 2 threads get and process the data
		synchronized (this.reducedBuffer) {
			if(null != this.reducedBuffer && !this.reducedBuffer.isEmpty()) {
			HashMap<String, String> currRed = this.reducedBuffer.get(0);
			//we remove current reduced data from the list to avoid double processing
			this.reducedBuffer.remove(0);
			this.totReduced -=1;//we keep track of the number of part being reduced
			return currRed;
			}else {
				return null;
			}
		}
	}

	@Override
	public HashMap<String, String> getCombBuffer() {
		return this.combinedBuffer;
	}

	@Override
	public void updateCombBuffer(HashMap<String, String> cb) {
		//we synchronized to avoid overriding
		synchronized(this.combinedBuffer) {
			this.combinedBuffer = cb;
		}
	}

	@Override
	public int getTotMapped() {
		return this.totMapped;
	}

	@Override
	public int getTotReduced() {
		return this.totReduced;
	}

}
