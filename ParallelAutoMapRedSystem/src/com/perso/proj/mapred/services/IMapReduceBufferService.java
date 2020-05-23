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
public interface IMapReduceBufferService {
	//This method get one part of partition data for mapping
	public List<String> getNextPart();
	
	//This method store the result of mapping to the buffer
	public void addMappedDataToBuffer(HashMap<String, String>map);
	
	//This method get a mapped data from buffer
	public HashMap<String, String> getNextMappedData();
	
	//This method store the result of reducing to the buffer
	public void addReducedDataToBuffer(HashMap<String, String>map);
		
	//This method get a reduced data from buffer
	public HashMap<String, String> getNextReducedData();
	
	public HashMap<String, String> getCombBuffer();
	
	public void updateCombBuffer(HashMap<String, String> cb);
	
	public int getTotMapped();
	
	public int getTotReduced();

}
