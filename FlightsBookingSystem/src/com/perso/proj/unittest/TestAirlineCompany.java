/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EBSOperations;
import com.perso.proj.enums.EOrderStatus;
import com.perso.proj.localcomp.AirlineCompany;
import com.perso.proj.localcomp.PriceCutEventEmiter;
import com.perso.proj.services.servimpl.BankServices;
import com.perso.proj.services.servimpl.CreditCardBufferServices;
import com.perso.proj.services.servimpl.EncryptionService;
import com.perso.proj.services.servimpl.MultiCellBufferServices;
import com.perso.proj.services.servimpl.PricingModelService;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IEncryptionService;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;
import com.perso.proj.services.servinterface.IPricingModelService;
import com.perso.proj.utils.UtilityClass;

/**
 * @author deghislain
 *
 */
@TestMethodOrder(OrderAnnotation.class)
public class TestAirlineCompany {
	static IMultiCellBufferServices buffer;
	
	static ICreditCardBufferServices cardBuffer;
	
	private static IBankServices bank;
	
	//private static IPricingModelService pricingModel;
	
	static int ticketInitialStockNumb;
	
	static IEncryptionService encrService;
	
	static AirlineCompany ac;
	
	static Order order;
	
	static String card;
	
	static PriceCutEventEmiter pce;
	
	private final static int KEY1 = 20; //keys for encryption
	
	private final static int KEY2 = 25;
	
	@BeforeAll
	public static void setUp() {
		buffer = new MultiCellBufferServices();
		cardBuffer = new CreditCardBufferServices();
		bank = new BankServices(cardBuffer);
		ticketInitialStockNumb = 1000;
		//pricingModel = new PricingModelService(ticketInitialStockNumb);
		encrService = new EncryptionService();
		
		pce = new PriceCutEventEmiter();
		
		 ac = new AirlineCompany(buffer, cardBuffer, bank, pce, ticketInitialStockNumb);
		 
		 
		 Random rand = new Random();
		 //this.buffer = new MultiCellBufferServices();
		 order = new Order();
		 String orderId = "O" + new Date().getTime() + rand.nextInt(5000);
		 order.setAmount(10);
		 order.setReceiverId("ThreadAC1");
		 order.setSenderId("ThreadTA1");
		 order.setStatus(EOrderStatus.NEW);
		 order.setUnitPrice(55);
		 order.setOrderId(orderId);
		 order.setOrderDate(UtilityClass.getCurrentTime());
			
	}
	
	@Test
	@org.junit.jupiter.api.Order(1)
	public void testUpdatePrice() {// we need to apply for a credit card before placing an order`

		cardBuffer.setCardCell(order.getSenderId(), null, EBSOperations.APPLICATION, order.getCreditCardNumber(), 0);// TA send the application
		bank.runBankService();// BS process the application
		String resultApp = cardBuffer.getCardCell(1, "TA");// TA get the feedback of its application
		String[] token = new String[resultApp.length()];
		token = resultApp.split("\\-");
		card = token[3];
		
		//card = encrService.decrypt(card, KEY1, KEY2);
		
		String feedback = token[2];
		
		assertNotNull(resultApp);
		
		assertNotNull(card);
		
		assertEquals(EBSOperations.DELIVERY.name(), feedback);
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	public void testProcessNextOrder() {
		order.setCreditCardNumber(card);
		String[] rcToken = null;
		String confirmation = null;
		buffer.setOneCell(order);
		ac.run();
		String feedback = cardBuffer.getCardCell(1, "TA"); // TA get feedback for this order
		if(feedback != null && !feedback.isEmpty()) {
		rcToken = feedback.split("\\-");
		 confirmation = rcToken[2];
		}
		assertNotNull(confirmation);
		
		assertEquals(EBSOperations.FEEDBACK_NO.name(), confirmation); // this order amount for 8800 and the credit card only got 1000
		
	}
	
	// here we make a deposit
	@Test
	@org.junit.jupiter.api.Order(3)
	public void testDeposit() {
		cardBuffer.setCardCell(order.getSenderId(), null, EBSOperations.DEPOSIT, card, 10000); // TA request a deposit of 1000$
		bank.runBankService(); //BS process the request
		String resultCharge = cardBuffer.getCardCell(1, "TA");// AC get feedback from BS
		String[] rcToken = resultCharge.split("\\-");
		String confirmation = rcToken[2];
		
		assertNotNull(confirmation);
		
		assertEquals(EBSOperations.FEEDBACK.name(), confirmation);
	}
	
	// here we try test2(testProcessNextOrder) again
	@Test
	@org.junit.jupiter.api.Order(4)
	public void test2() {
		order.setCreditCardNumber(card);
		String[] rcToken = null;
		String confirmation = null;
		buffer.setOneCell(order);
		ac.run();
		String feedback = cardBuffer.getCardCell(1, "TA"); // TA get feedback for this order
		//Order processedOrder = buffer.getOneCell();
		if(feedback != null && !feedback.isEmpty()) {
		rcToken = feedback.split("\\-");
		 confirmation = rcToken[2];
		}
		assertNotNull(confirmation);
		
		assertEquals(EBSOperations.FEEDBACK.name(), confirmation); // this order amount for 8800 and the credit card only got 1000
		
	}
}
