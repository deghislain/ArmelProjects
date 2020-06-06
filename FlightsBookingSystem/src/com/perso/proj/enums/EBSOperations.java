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
	FEEDBACK("VALID"), //successful payment
	FEEDBACK_NO("NO VALID"),// payment did not went through
	FEEDBACK_P("PROCESSED"),//order successfully processed waiting for bank payment aproval
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
