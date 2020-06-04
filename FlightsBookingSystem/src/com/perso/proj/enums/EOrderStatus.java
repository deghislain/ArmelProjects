/**
 * 
 */
package com.perso.proj.enums;

/**
 * @author deghislain
 *
 */
public enum EOrderStatus {
	NEW("NEW ORDER"),
	PLACED("ORDER SENT TO AC"),
	RECEIVED("ORDER RECEIVED BY AC"),
	PROCESSED("ORDER PRECESSED BY OP"),
	CONFIRMED("ORDER PAID"),
	DISCARDED("ORDER INVALIDATED BY AC"),
	CANCELED("ORDER INVALIDATED BY TA");
	
	
	String status;
	
	String getStatus() {
		return this.status;
	}
		private EOrderStatus(String s) {
			this.status = s;
		}
}
