/**
 * 
 */
package com.perso.proj.localcomp;

import java.text.DateFormat;
import java.util.Date;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EBSOperations;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;

/**
 * @author deghislain
 *
 */
public class OrderProcessing implements Runnable {
	private Order myOrder;

	private ICreditCardBufferServices ccBufferServices;
	private final static float TAX_RATE = 13;

	private final static float LOCATION_CHARGE_RATE = 2;

	public OrderProcessing(Order o, ICreditCardBufferServices ccBuffer) {
		this.myOrder = o;
		this.ccBufferServices = ccBuffer;
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
		total = this.myOrder.getUnitPrice() * this.myOrder.getAmount();
		double tax = total * OrderProcessing.TAX_RATE;
		double locationCharge = total * OrderProcessing.LOCATION_CHARGE_RATE;
		total = total + tax + locationCharge;
		return total;
	}

	// This method send a request to the Bank for a payment
	private void makePaymentRequest() {
		this.ccBufferServices.setCardCell(this.myOrder.getSenderId(), this.myOrder.getOrderId(), EBSOperations.CHARGE,
				this.myOrder.getCreditCardNumber(), this.calculateTotalAmount());
	}

	// This method print the result of the order processing
	private void printOrder() {
		System.out.printf("%-10s %-10s %-10s %-10s %-10s %n", "OrderId: " + this.myOrder.getOrderId(), "| Order Date: " + this.myOrder.getOrderDate(),
				"| Ordered By: " + this.myOrder.getSenderId(), "| Total Charges: " + this.calculateTotalAmount(), "| Order Processed at: " + getProcessingTime());
	}

	// This method notify The TA about the outcome of the current order processing
	private void sendFeedbackToTA() {
		
	}

	private String getProcessingTime() {
		 Date currentDate = new Date();
		 return  DateFormat.getTimeInstance().format(currentDate);  
	}
}
