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
public interface ITaskTrackerService {
	public HashMap<String, String> map(List<String> words);
	public HashMap<String, String> reduce(HashMap<String, String> mappedMap);
}
