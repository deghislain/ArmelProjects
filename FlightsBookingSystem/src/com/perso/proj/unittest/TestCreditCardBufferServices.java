/**
 * 
 */
package com.perso.proj.unittest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.enums.EBSOperations;
import com.perso.proj.services.servimpl.CreditCardBufferServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;

/**
 * @author deghislain
 *
 */
public class TestCreditCardBufferServices {
	ICreditCardBufferServices ccService;
	String travAgName;
	//String operation;
	String card;
	String orderId;
	EBSOperations feedback;

	@BeforeEach
	public void setUp() {
		ccService = new CreditCardBufferServices();
		travAgName = "Thread1";
		card = "2453-9512-0000-3698";

		orderId = "order1";
		
	}

	@Test
	public void testSetCardCell() {
		ccService.setCardCell(travAgName, null, EBSOperations.APPLICATION, card, 0);
		String result = ccService.getCardCell(1, "TA");

		String result1 = ccService.getCardCell(0, "TA");
		
		assertNotNull(result);

		assertNull(result1);
	}
	
	@Test
	public void testSetOrderStatus() {
		ccService.setCardCell(travAgName, orderId, EBSOperations.CONFIRM, card, 0);
		String expResult11 = travAgName + "-" + orderId + "-" + EBSOperations.CONFIRM.name() + "-" + card + "-" + 0.0;
		String result11 = ccService.getCardCell(1, "getFeedBack");
		String result21 = ccService.getCardCell(1, "getFeedBack");

		assertEquals(expResult11, result11);

		assertNull(result21);
		
		feedback = EBSOperations.DECLINE;
		ccService.setCardCell(travAgName, orderId, EBSOperations.DECLINE, card, 0);
		String expResult21 = travAgName + "-" + orderId + "-" + EBSOperations.DECLINE + "-" + card + "-" + 0.0;
		String result211 = ccService.getCardCell(1, "getFeedBack");
		String result22 = ccService.getCardCell(1, "getFeedBack");
		assertEquals(expResult21, result211);

		assertNull(result22);
	}

}
