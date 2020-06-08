/**
 * 
 */
package com.perso.proj.localcomp;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EBSOperations;
import com.perso.proj.enums.EOrderStatus;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IPricingModelService;
import com.perso.proj.utils.UtilityClass;

/**
 * @author deghislain
 *
 */
public class OrderProcessing implements Runnable {
	private Order myOrder;
	
	private IPricingModelService pricingModel;
	
	private IBankServices bankService;

	private ICreditCardBufferServices ccBufferServices;
	private final static float TAX_RATE = 13;

	private final static float LOCATION_CHARGE_RATE = 2;

	public OrderProcessing(Order o, ICreditCardBufferServices ccBuffer, IPricingModelService pms, IBankServices bs) {
		this.myOrder = o;
		this.ccBufferServices = ccBuffer;
		this.pricingModel = pms;
		this.bankService = bs;
	}

	@Override
	public void run() {
		this.pricingModel.runPricingModel();
		calculateTotalAmount();
		makePaymentRequest();
		printOrder();
		sendFeedbackToTA();
		this.bankService.runBankService();
		
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
		this.myOrder.setStatus(EOrderStatus.PROCESSED);
	}

	// This method print the result of the order processing
	private void printOrder() {
		System.out.printf("%-12s %-12s %-12s %-12s %-12s %-12s %-12s %-12s %n", "OrderId: " + this.myOrder.getOrderId(), "| Order Date: " + this.myOrder.getOrderDate(),
				"| Ordered By: " + this.myOrder.getSenderId(),"| Processed By " + this.myOrder.getReceiverId(), "| Amount: " + this.myOrder.getAmount(),"| Unit Price: " + this.myOrder.getUnitPrice(), "| Total Charges: " + UtilityClass .getRoundedValue(this.calculateTotalAmount()), "| Order Processed at: " + UtilityClass.getCurrentTime());
	}

	// This method notify The TA about the outcome of the current order processing
	private void sendFeedbackToTA() {
		 this.ccBufferServices.setCardCell(this.myOrder.getSenderId(), this.myOrder.getOrderId() , EBSOperations.FEEDBACK_P, this.myOrder.getCreditCardNumber(), this.myOrder.getAmount());
	}

	
}
