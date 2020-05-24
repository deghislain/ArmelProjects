/**
 * 
 */
package com.perso.proj.mapred.ws.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



/**
 * @author deghislain
 *
 */
@XmlType(name = "keyValuePair", propOrder = {"key","value"})
public class KeyValuePair implements Serializable{
	public KeyValuePair() {
		
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3868590122683991790L;
	@XmlElement(required = true)
	private String key;
	
	@XmlElement(required = true)
	private int value;
	/**
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * @return the Key
	 */
	public String getKey() {
		return this.key;
	}
	/**
	 * @param value the value to set
	 */
	public void put(String key, int value) {
		this.value = value;
		this.key = key;
	}

}
