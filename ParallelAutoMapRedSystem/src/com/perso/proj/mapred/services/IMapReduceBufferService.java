/**
 * 
 */
package com.perso.proj.mapred.services;

import java.util.HashMap;
import java.util.List;

import com.perso.proj.mapred.ws.entity.KeyValuePair;

/**
 * @author deghislain
 *
 */
public interface IMapReduceBufferService {
	//This method get one part of partition data for mapping
	public List<String> getNextPart();
	
	//This method store the result of mapping to the buffer
	public void addMappedDataToBuffer(List<KeyValuePair>map);
	
	//This method get a mapped data from buffer
	public List<KeyValuePair> getNextMappedData();
	
	//This method store the result of reducing to the buffer
	public void addReducedDataToBuffer(HashMap<String, Integer>map);
		
	//This method get a reduced data from buffer
	public HashMap<String, Integer> getNextReducedData();
	
	public HashMap<String, Integer> getCombBuffer();
	
	public void updateCombBuffer(HashMap<String, Integer> cb);
	
	public int getTotMapped();
	
	public int getTotReduced();
	
	public boolean getIsJobDone();
	
	public boolean getIsResultsStored();
	
	public void setIsJobDone(boolean isd);
	
	public void setIsResultsStored(boolean irs);

}
