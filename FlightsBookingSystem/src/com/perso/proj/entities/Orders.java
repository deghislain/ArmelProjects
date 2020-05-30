/**
 * 
 */
package com.perso.proj.entities;

/**
 * @author deghislain
 *
 */
public class Orders { 
//Indicates the id of the travel agency placing the order
private String senderId;

//Indicates the credit card number of the travel agency placing the order
private String creditCardNumber;

//Indicates the id of the Airline company receiving the order
private String receiverId;

//Indicates the number of tickets to order
private int amount;

//Indicates the price of a single ticket received from Airlines companies
private double unitPrice;

/**
 * @return the senderId
 */
public String getSenderId() {
	return senderId;
}

/**
 * @param senderId the senderId to set
 */
public void setSenderId(String senderId) {
	this.senderId = senderId;
}

/**
 * @return the creditCardNumber
 */
public String getCreditCardNumber() {
	return creditCardNumber;
}

/**
 * @param creditCardNumber the creditCardNumber to set
 */
public void setCreditCardNumber(String creditCardNumber) {
	this.creditCardNumber = creditCardNumber;
}

/**
 * @return the receiverId
 */
public String getReceiverId() {
	return receiverId;
}

/**
 * @param receiverId the receiverId to set
 */
public void setReceiverId(String receiverId) {
	this.receiverId = receiverId;
}

/**
 * @return the amount
 */
public int getAmount() {
	return amount;
}

/**
 * @param amount the amount to set
 */
public void setAmount(int amount) {
	this.amount = amount;
}

/**
 * @return the unitPrice
 */
public double getUnitPrice() {
	return unitPrice;
}

/**
 * @param unitPrice the unitPrice to set
 */
public void setUnitPrice(double unitPrice) {
	this.unitPrice = unitPrice;
}








}
