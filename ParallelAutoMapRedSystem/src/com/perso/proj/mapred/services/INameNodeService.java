/**
 * 
 */
package com.perso.proj.mapred.services;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

/**
 * @author deghislain
 *
 */
public interface INameNodeService {
	public String storeFile(List<FileItem> formItems, String filePath);
	
	public List<List<String>> partitionData(String filePath, int numThread);
	
	public String storeResults(HashMap<String, String> results, String resultStoragePath);
	
	 HashMap<String, String> displayResults(String filePath);
}
