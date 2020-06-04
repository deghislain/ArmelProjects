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
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;
import com.perso.proj.services.servinterface.IPricingModelService;

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
	private int ticketInitialStockNumb;

	// indicates how many times there was a price cut
	private static int PRICE_CUT_EVENT_COUNTER = 0;
	
	private static int TOTAL_NUM_ORDERS = 0;

	// indicates the up to date price of a ticket
	private double currentTicketPrice;

	private int totNumberOrdRec;

	// indicates the number of order received before there was a price cut
	private int numOrderRecAtLastPriceCut;

	// indicates the number of order received after the last price cut
	private int numOrdRecSinceLastPriceCut;

	// indicates the current number of tickets holds by the AC
	private int numAvailableTicket;

	// indicates at when the AC start selling ticket
	private Date dateInitSale;

	private List<Order> processedOrders;

	public AirlineCompany(IMultiCellBufferServices mbs, ICreditCardBufferServices ccb, IBankServices bs,
			IPricingModelService pms, int initialStock) {
		this.buffer = mbs;
		this.cardBuffer = ccb;
		this.bank = bs;
		this.pricingModel = pms;
		this.ticketInitialStockNumb = initialStock;
		this.currentTicketPrice = 50;
		this.dateInitSale = new Date();
		this.processedOrders = new ArrayList<Order>();
	}

	public void run() {
		this.updateSalesInfo();
		this.updatePrice();
		this.processNextOrder();
		this.updateOrderStatus();
	}

	private void updateSalesInfo() {
		this.pricingModel.updateSalesData(this.numOrderRecAtLastPriceCut, this.numOrdRecSinceLastPriceCut,
				this.numAvailableTicket, this.dateInitSale);
	}

	private void updatePrice() {
		this.currentTicketPrice = pricingModel.getTicketCurrentPrice();
	}

	private void processNextOrder() {
		Order currOrder = this.buffer.getOneCell();
		if (currOrder != null) {
			OrderProcessing op = new OrderProcessing(currOrder, this.cardBuffer, this.pricingModel, this.bank);
			// Thread opThread = new Thread(op); //TODO uncomment these 2 lines
			// opThread.start();
			op.run(); // TODO //remove after test
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
	private synchronized void priceCutDataUpdate(){
		PRICE_CUT_EVENT_COUNTER++;
		this.numOrderRecAtLastPriceCut = TOTAL_NUM_ORDERS;
	}
	
	private synchronized void updateOrdersData(int numTicket) {
		TOTAL_NUM_ORDERS++;
		this.numOrdRecSinceLastPriceCut = TOTAL_NUM_ORDERS - this.numOrderRecAtLastPriceCut;
		this.numAvailableTicket = this.ticketInitialStockNumb - numTicket;
	}
	
	public double getTicketCurrentPrice() {
		return this.currentTicketPrice;
	}

}
