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
public interface ITaskTrackerService {
	public List<KeyValuePair> map(List<String> words);
	public HashMap<String, Integer> reduce(List<KeyValuePair> mappedMap);
}
