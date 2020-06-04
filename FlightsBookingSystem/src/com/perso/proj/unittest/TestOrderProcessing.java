/**
 * 
 */
package com.perso.proj.unittest;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EOrderStatus;
import com.perso.proj.localcomp.OrderProcessing;
import com.perso.proj.services.servimpl.BankServices;
import com.perso.proj.services.servimpl.CreditCardBufferServices;
import com.perso.proj.services.servimpl.PricingModelService;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IPricingModelService;

/**
 * @author deghislain
 *
 */
public class TestOrderProcessing {
	OrderProcessing op;
	Order order;
	Random rand = new Random();
	ICreditCardBufferServices cBufServ;
	IBankServices bank;
	IPricingModelService pricingModel;
	int ticketInitialStockNumb;
	
	@BeforeEach
	public void setUp() {
		this.cBufServ = new CreditCardBufferServices();
		this.order = new Order();
		String orderId = "O" + new Date().getTime() + rand.nextInt(5000);
		this.order.setAmount(10);
		this.order.setCreditCardNumber("2453.9512.0000.3698");
		this.order.setReceiverId("ThreadAC1");
		this.order.setSenderId("ThreadTA1");
		this.order.setStatus(EOrderStatus.NEW);
		this.order.setUnitPrice(55);
		this.order.setOrderId(orderId);
		this.bank = new BankServices(this.cBufServ);
		this.ticketInitialStockNumb = 100;
		this.pricingModel = new PricingModelService(this.ticketInitialStockNumb);
		
		 Date currentDate = new Date();  
		this.order.setOrderDate(DateFormat.getTimeInstance().format(currentDate));
		
		
		this.op = new OrderProcessing(this.order, this.cBufServ, this.pricingModel, this.bank);
		
	}
	
	@Test
	public void testOrderProcessing() {
		this.op.run();
	}
}
