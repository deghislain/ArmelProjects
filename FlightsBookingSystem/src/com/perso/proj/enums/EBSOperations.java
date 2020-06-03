/**
 * 
 */
package com.perso.proj.enums;

/**
 * @author deghislain
 *
 */
public enum EBSOperations {
	APPLICATION("CREDIT CARD APPLICATION"), 
	DEPOSIT("REQUESTED DEPOSIT OPERATION"), 
	CHARGE("REQUESTED CHARGE OPERATION"), 
	DELIVERY("REQUESTED DELIVERY OPERATION"), 
	FEEDBACK("COMPLETED ORDER PROCESSING"),
	CONFIRM("VALID"),
	DECLINE("NO VALID");
	
	private String info;
	
	public String getInfo() {
		return this.info;
	}
	private EBSOperations(String info) {
		this.info = info;
	}
}
