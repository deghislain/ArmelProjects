/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.perso.proj.enums.EBSOperations;
import com.perso.proj.services.servimpl.BankServices;
import com.perso.proj.services.servimpl.CreditCardBufferServices;
import com.perso.proj.services.servimpl.EncryptionService;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IEncryptionService;

/**
 * @author deghislain
 *
 */
@TestMethodOrder(OrderAnnotation.class)
public class TestBankServices {
	// TA:Travel Agency, BS:Bank Service, AC:Airline Company
	static ICreditCardBufferServices ccBuffer;
	static IEncryptionService encrService;
	static String travAgName;
	//String operation;
	String card;
	static IBankServices bs;
    private final static int KEY1 = 20; //keys for encryption
    private final static int KEY2 = 25;
	
	
	@BeforeAll
	public static void setUp() {
		ccBuffer = new CreditCardBufferServices();
		travAgName = "Thread1";
		//operation = "Card Application";
		bs = new BankServices(ccBuffer);
		encrService = new EncryptionService();
	}
	
	@Test
	@Order(1)
	public void testProcessCreditCardApplication() {
		//this part test processCreditCardApplication
		ccBuffer.setCardCell(travAgName, null, EBSOperations.APPLICATION, card, 0);//TA send the application
		bs.runBankService();//BS process the application
		String resultApp = ccBuffer.getCardCell(1, "TA");//TA get the feedback of its application
		
		String[] token = new String[resultApp.length()];
		token = resultApp.split("\\-");
		card = token[3];
		card = encrService.decrypt(card, KEY1, KEY2);
		
		String feedback = token[2];
		
		assertNotNull(resultApp);
		
		assertNotNull(card);
		
		assertEquals(EBSOperations.DELIVERY.name(), feedback);
	}
	
	@Test
	@Order(2)
	public void testChargeCreditCard() {
		card = "2477.9582.0900.3698";//TA already have the a credit card
		card = encrService.encrypt(card, KEY1, KEY2);
		ccBuffer.setCardCell(travAgName, "orderId", EBSOperations.CHARGE, card, 100); //AC request a payment of 100$
		bs.runBankService(); //BS process the request
		String resultCharge = ccBuffer.getCardCell(1, "AC");// AC get feedback from BS
		String[] rcToken = resultCharge.split("\\-");
		String feedback = rcToken[2];
		
		assertNotNull(feedback);
		
		assertEquals(EBSOperations.CONFIRM.name(), feedback);
		//******************************************TEST2********************************
		
		card = "2477.9582.0900.3698";
		card = encrService.encrypt(card, KEY1, KEY2);
		ccBuffer.setCardCell(travAgName, "orderId", EBSOperations.CHARGE, card, 1000); //a subsequent payment of 1000$ is not valid, for insufficient fund
		bs.runBankService(); 
		resultCharge = ccBuffer.getCardCell(1, "AC");;
		rcToken = resultCharge.split("\\-");
		feedback = rcToken[2];
		
		assertNotNull(feedback);
		
		assertEquals(EBSOperations.DECLINE.name(), feedback);
	}
	
	@Test
	@Order(3)
	public void testDeposit() {
		card = "2477.9582.0900.3698";//TA already have the a credit card
		card = encrService.encrypt(card, KEY1, KEY2);
		ccBuffer.setCardCell(travAgName, null, EBSOperations.DEPOSIT, card, 500); // TA request a deposit of 500$
		bs.runBankService(); //BS process the request
		String resultCharge = ccBuffer.getCardCell(1, "TA");// AC get feedback from BS
		String[] rcToken = resultCharge.split("\\-");
		String confirmation = rcToken[2];
		
		assertNotNull(confirmation);
		
		assertEquals(EBSOperations.FEEDBACK.name(), confirmation);
	}
}
