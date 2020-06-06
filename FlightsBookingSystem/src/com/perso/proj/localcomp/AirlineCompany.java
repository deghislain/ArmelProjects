/**
 * 
 */
package com.perso.proj.localcomp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EBSOperations;
import com.perso.proj.enums.EOrderStatus;
import com.perso.proj.services.servimpl.PricingModelService;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;
import com.perso.proj.services.servinterface.IPricingModelService;
import com.perso.proj.utils.UtilityClass;

/**
 * @author deghislain
 *
 */
public class AirlineCompany extends Thread {

	// buffer that allows communication between the Airline Company(AC), and The
	// Travel Agency(TA)
	private IMultiCellBufferServices buffer;

	// buffer that allows communication between the Airline Company(AC), The Travel
	// Agency(TA), and the Bank
	private ICreditCardBufferServices cardBuffer;

	// service that handle the financial transaction during the ticket sales
	private IBankServices bank;

	// service that define the ticket price and the company business logic
	private IPricingModelService pricingModel;

	// the total number of tickets holds by an airline company at the opening of
	// sales
	private static int ticketInitialStockNumb;

	// indicates how many times there was a price cut
	private static int PRICE_CUT_EVENT_COUNTER = 0;
	
	//indicate the total number of sales received since the sales started
	private static int TOTAL_NUM_ORDERS = 0;

	// indicates the up to date price of a ticket
	private static double CURRENT_TICKET_PRICE = 50;

	// indicates the number of order received before there was a price cut
	private static int  NUM_ORDER_REC_AT_LAST_PRICE_CUT;

	// indicates the number of order received after the last price cut
	private static int NUM_ORDER_REC_SINCE_LAST_PRICE_CUT;

	// indicates the current number of tickets holds by the AC
	private static int TOTAL_NUM_AVAILABLE_TICKET;

	// indicates at when the AC start selling ticket
	private Date dateInitSale;

	//Indicates the orders processed by a specific AC
	private List<Order> processedOrders;
	
	private PriceCutEventEmiter pcEvent;
	

	public AirlineCompany(IMultiCellBufferServices mbs, ICreditCardBufferServices ccb, IBankServices bs, PriceCutEventEmiter pce, int initialStock) {
		this.buffer = mbs;
		this.cardBuffer = ccb;
		this.bank = bs;
		this.pricingModel =  new PricingModelService(initialStock) ;
		this.ticketInitialStockNumb = initialStock;
		TOTAL_NUM_AVAILABLE_TICKET = initialStock;
		this.dateInitSale = new Date();
		this.processedOrders = new ArrayList<Order>();
		this.pcEvent = pce;
	}

	public void run() {
		System.out.println("Started Thread: " + this.getName() + " At "+ UtilityClass.getCurrentTime());
		while (PRICE_CUT_EVENT_COUNTER < 20) {
			try {
				this.updateSalesInfo();
				this.updatePrice();
				this.processNextOrder();
				this.updateOrderStatus();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		pcEvent.onStopEvent(true);//when the AC stop we notify the TA to stop too
		System.out.println("Ended Thread: " + this.getName() + " At "+ UtilityClass.getCurrentTime());
	}

	private void updateSalesInfo() {
		this.pricingModel.updateSalesData(NUM_ORDER_REC_AT_LAST_PRICE_CUT, NUM_ORDER_REC_SINCE_LAST_PRICE_CUT,
				TOTAL_NUM_AVAILABLE_TICKET, this.dateInitSale);
	}

	private synchronized void updatePrice() {
		double newPrice = pricingModel.getTicketCurrentPrice();
		if(newPrice < CURRENT_TICKET_PRICE) {
			priceCutDataUpdate();
		}
		pcEvent.sendNewPriceToTA(newPrice);
		CURRENT_TICKET_PRICE = newPrice;
	}

	private void processNextOrder() {
		Order currOrder = this.buffer.getOneCell();
		if (currOrder != null) {
			currOrder.setReceiverId("OP" +this.getName());
			OrderProcessing op = new OrderProcessing(currOrder, this.cardBuffer, this.pricingModel, this.bank);
			Thread opThread = new Thread(op); 
			opThread.setName("OP " +this.getName());
			opThread.start();
			updateOrdersData(currOrder.getAmount());
			synchronized (this.processedOrders) {
				this.processedOrders.add(currOrder);
			}
			
		}
	}

	//this method update the order status and send a feedback to TA
	private synchronized void updateOrderStatus() {
		for (Order currOrder : this.processedOrders) {
			EOrderStatus currOrdStatus = currOrder.getStatus();
			if (currOrdStatus.name().equals(EOrderStatus.PROCESSED.name())) {
				String travAgName = currOrder.getSenderId();
				int index = travAgName.length() - 1;
				String status = this.cardBuffer.getCardCell(Character.getNumericValue(travAgName.charAt(index)), "AC");
				if(status != null && !status.isEmpty()) {
				String[] token = status.split("\\-");
				if (null != token && token.length > 2) {
					String operation = token[2];
					if (operation.equals(EBSOperations.CONFIRM.name())) {
						currOrder.setStatus(EOrderStatus.CONFIRMED);
						// now we notify the TA that the order was successfully processed
						this.cardBuffer.setCardCell(currOrder.getSenderId(), currOrder.getOrderId(),
								EBSOperations.FEEDBACK, currOrder.getCreditCardNumber(), currOrder.getAmount());
					} else if (operation.equals(EBSOperations.DECLINE.name())) {
						currOrder.setStatus(EOrderStatus.DISCARDED);
						this.cardBuffer.setCardCell(currOrder.getSenderId(), currOrder.getOrderId(),
								EBSOperations.FEEDBACK_NO, currOrder.getCreditCardNumber(), currOrder.getAmount());
					}
				}
			}
			}
		}
	}
	
	//this method is called only when there is price cut
	private void priceCutDataUpdate(){
		PRICE_CUT_EVENT_COUNTER++;
		NUM_ORDER_REC_AT_LAST_PRICE_CUT = TOTAL_NUM_ORDERS;
	}
	
	private synchronized void updateOrdersData(int numTicket) {
		TOTAL_NUM_ORDERS++;
		NUM_ORDER_REC_SINCE_LAST_PRICE_CUT = TOTAL_NUM_ORDERS - NUM_ORDER_REC_AT_LAST_PRICE_CUT;
		TOTAL_NUM_AVAILABLE_TICKET = TOTAL_NUM_AVAILABLE_TICKET - numTicket;
	}
	
	
	
	public double getTicketCurrentPrice() {
		return CURRENT_TICKET_PRICE;
	}
	

}
