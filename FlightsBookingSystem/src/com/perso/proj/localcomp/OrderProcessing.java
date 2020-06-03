/**
 * 
 */
package com.perso.proj.localcomp;

import org.junit.jupiter.api.Order;

/**
 * @author deghislain
 *
 */
public class OrderProcessing implements Runnable {
	private Order myOrder;

	private final static float TAX_RATE = 13;

	private final static float LOCATION_CHARGE_RATE = 2;

	public OrderProcessing(Order o) {
		this.myOrder = o;
	}

	@Override
	public void run() {
		calculateTotalAmount();
		makePaymentRequest();
		printOrder();
		sendFeedbackToTA();
	}

	// This method calculate the total amount that the AC can apply to the TA
	// current order
	private double calculateTotalAmount() {
		double total = -1;

		return total;
	}
	
	//This method send a request to the Bank for a payment
	private void makePaymentRequest() {
		
	}

	//This method print the result of the order processing
	private void printOrder() {

	}

	//This method notify The TA about the outcome of the current order processing 
	private void sendFeedbackToTA() {

	}

}
