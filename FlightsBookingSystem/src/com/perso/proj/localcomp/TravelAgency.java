/**
 * 
 */
package com.perso.proj.localcomp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EBSOperations;
import com.perso.proj.enums.EOrderStatus;
import com.perso.proj.services.servimpl.EncryptionService;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IEncryptionService;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;
import com.perso.proj.utils.UtilityClass;

/**
 * @author deghislain
 *
 */
public class TravelAgency extends Thread implements PriceCutEventListener{
	//indicates the cost of a single ticket from Airline Companies(AC)
	private static double CURRENT_PRICE = 50;
	
	//buffer that allows Travel Agency(TA) to communicate with the Bank
	private ICreditCardBufferServices ccBuffer;
	
	// service that handle the financial transaction during the ticket sales
	private IBankServices bank;
	
	//list orders made by this TA
	private List<Order>orders;
	
	//credit card necessary for orders
	private String creditCard;
	
	// buffer that allows communication between the Airline Company(AC), and The Travel Agency(TA)
	private IMultiCellBufferServices buffer;
	
	//indicates the max number of tickets that a TA can handle at a time, this number can be increased to 150 in case of price cut 
    private  int plafond = 100;
	
    //indicates the total number of tickets currently holds by a TA
	private int currentTotalNumTicket;
	
	private  float percentageTicketLeft = 1;
	
	private boolean isOrderConfirmed;
	
	private boolean isWaitingConf;
	
	private boolean isFirstOrder = true;
	
	private boolean IS_COMMAND_STOP_ISSUED = false;
	
	private final static int KEY1 = 20; // keys for encryption

	private final static int KEY2 = 25;
	
	private final static double DEPOSIT_AMOUNT = 100000;//max deposit amount for each TA
	
	private static double NEW_PRICE = 50;
	
	//private int amountLastOrder;
	


	public TravelAgency(IMultiCellBufferServices mcbs, ICreditCardBufferServices ccb, IBankServices bs) {
		this.ccBuffer = ccb;
		this.bank = bs;
		this.orders = new ArrayList<Order>();
		this.buffer = mcbs;
		this.isOrderConfirmed = false;
		this.isWaitingConf = false;
		//this.amountLastOrder = 0;
	}

	@Override
	public void run() {
		System.out.println("Started Thread: " + this.getName() + " At "+ UtilityClass.getCurrentTime());
		while (!IS_COMMAND_STOP_ISSUED) {
			try {
				this.percentageTicketLeft = 1f * this.currentTotalNumTicket/this.plafond;
				applyForCreditCard();
				updateCurrentPrice();
				//no additional order if waiting for confirmation on a previous order/ PERCENTAGE_TICKET_SOLD == at first call
				if(!this.isWaitingConf) {
					Thread.sleep(1000);
					makeOrder(false);
				}
				
				checkFeedBack();
				if(this.currentTotalNumTicket > 0) {
					saleTicket();
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

		System.out.println("Ended Thread: " + this.getName() + " At "+ UtilityClass.getCurrentTime());
	}

	//allow TA to apply for credit card
	private void applyForCreditCard() {
		String travAgName = this.getName();
		this.ccBuffer.setCardCell(travAgName, null, EBSOperations.APPLICATION, null, 0);
		this.bank.runBankService();
	}
	
	//allows TA get feedbacks from bank and AC
	private void checkFeedBack(){
		String travAgName = this.getName();
		int num = travAgName.length() - 1;
		String operation = null;
		String orderId = null;
		String cardNum = null;
		char indice = travAgName.charAt(num);
		
		String result = this.ccBuffer.getCardCell(Character.getNumericValue(indice), "TA");
		if (result != null && !result.isEmpty()) {
			String[] token = result.split("\\-");
			if (token != null) {
				if (token.length >= 2) {
					orderId = token[1];
				}
				if (token.length >= 3) {
					operation = token[2];
				}

				if (token.length >= 4) {
					cardNum = token[3];
				}
			}
		}
		if(operation != null && operation.equals(EBSOperations.DELIVERY.name())) {
			if(cardNum != null && !cardNum.isEmpty()) {
				this.creditCard = cardNum;
			}
		}
		
		if(operation != null && operation.equals(EBSOperations.FEEDBACK_P.name())) {
			Order o = getOrder(orderId);
			if(o != null) {
				updateOrderStatus(orderId, EOrderStatus.PROCESSED);
			}
			
		}
		
		if(operation != null && operation.equals(EBSOperations.FEEDBACK.name())) {
			Order o = getOrder(orderId);
			if(o != null) {
				this.currentTotalNumTicket +=  o.getAmount();
				
				updateOrderStatus(orderId, EOrderStatus.CONFIRMED);
				this.isOrderConfirmed = true;
				this.isWaitingConf = false;
			}
		}
		
		if(operation != null && operation.equals(EBSOperations.FEEDBACK_NO.name())) {
			this.isWaitingConf = false;
			Order o = getOrder(orderId);
			//here we have an order that was rejected for insufficient fund
			if(o != null) {
				updateOrderStatus(orderId, EOrderStatus.CANCELED); //we keep track the oder in canceled state for inventory
				this.makeAdeposit();//we load the credit card
				this.makeOrder(false);// then we make a new order
			}
			
		}
	}

	//makes a special whenever possible in case of price cut
	private void makeSpecialOrder() {
		if(!this.isWaitingConf || this.plafond == 150) {// a TA that is waiting for confirmation from a previous can only makes special orders
			makeOrder(true);
		}
	}
	
	//makes an ordinary order
	private  void makeOrder(boolean isSpecialOrder) {
		int amount = 0;
		if(isSpecialOrder) {
			this.plafond = 150; // the company can handle more than 100 ticket in case of price cut event
		}
		
		if(this.isFirstOrder || (this.currentTotalNumTicket < this.plafond && this.percentageTicketLeft <= 0.5)) {
			amount = this.plafond - this.currentTotalNumTicket;
			if(this.creditCard != null && !this.creditCard.isEmpty() && this.currentTotalNumTicket < this.plafond) {
				Order order = createOrder(amount, isSpecialOrder);
				this.plafond = 100; //here we reset plafond to its ordinary value
				this.buffer.setOneCell(order);
				//synchronized (this.orders) {
					this.orders.add(order);
				//}
				this.isWaitingConf = true;
				this.isFirstOrder = false;
			}
		}
	}
	
	//create an order
	private  Order createOrder(int amount, boolean isSpecialOrder) {
		Order o = new Order();
		o.setAmount(amount);
		o.setCreditCardNumber(this.creditCard);
		o.setOrderDate(UtilityClass.getCurrentTime());
		o.setOrderId(UtilityClass.getOrderId(isSpecialOrder));
		o.setSenderId(this.getName());
		o.setStatus(EOrderStatus.NEW);
		o.setUnitPrice(CURRENT_PRICE);
		return o;
	}
	
	//this method update the orders status for every TA
		private  void updateOrderStatus(String orderId, EOrderStatus newStatus) {
			for (Order currOrder : this.orders) {
				if(orderId.equals(currOrder.getStatus().name())) {
					currOrder.setStatus(newStatus);
					break;
				}
			}
		}
		//return a specific order
		private Order getOrder(String orderId) {
			Order order = null;
			for(Order o : this.orders) {
				if(orderId.equals(o.getOrderId())) {
					order = o;
					break;
				}
			}
			return order;
		}

	/*private synchronized void updateCurrentPrice() {
		CURRENT_PRICE = pcEventHand.getNewPrice();
	}*/
	
	//this method basically sales tickets
	private void saleTicket() {
		if (this.isOrderConfirmed) { // the sales can only start if there is a confirmed(valid response => completed order) for at least one order
			Random r = new Random();
			int rn = r.nextInt(5); // we do not sale more than 5 tickets at a time
			this.currentTotalNumTicket -= rn;

			if(this.percentageTicketLeft < 0.5) {//if the TA has already sold 50% of its plafond, a new order is placed automaticaly
				makeOrder(false);
			}
		}
	}


	@Override
	public void onPriceChange(double newPrice) {
		NEW_PRICE = newPrice;
	}
	
	private void makeAdeposit() {
		IEncryptionService encrService = new EncryptionService();
		String encrCard = encrService.encrypt(this.creditCard, KEY1, KEY2);
		ccBuffer.setCardCell(this.getName(), null, EBSOperations.DEPOSIT, encrCard, DEPOSIT_AMOUNT); // TA request a deposit of 100000$
	}

	@Override
	public void onStopEvent(boolean isStop) {
		IS_COMMAND_STOP_ISSUED = true;
	}
	
	private  void updateCurrentPrice() {
		double oldPrice  = CURRENT_PRICE;
		CURRENT_PRICE = NEW_PRICE;
		if(NEW_PRICE < oldPrice) {//this mean price cut the TA can make a special order if necessary
			this.plafond = 150; // we increase the max number of ticket that a TA can handle to 150
			makeSpecialOrder();
		}
	}

}
