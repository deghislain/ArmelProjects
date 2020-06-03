/**
 * 
 */
package com.perso.proj.enums;

/**
 * @author deghislain
 *
 */
public enum EOrderStatus {
	NEW,//just created by Travel Agency(TA)
	PLACED,//send to Airline company(AC)
	CONFIRMED,//successfully processed
	RECEIVED, //received by AC
	DISCARDED,//invalid order, for insufficient fund, or invalid credit card.
	CANCELED// a TA might change its mind after issuing an order
}
