/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.services.servimpl.CreditCardBufferServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;

/**
 * @author deghislain
 *
 */
public class TestCreditCardBufferServices {
	ICreditCardBufferServices ccService;
	String travAgName;
	String operation;
	String card;
	String orderId;
	boolean isConfirm;

	@BeforeEach
	public void setUp() {
		ccService = new CreditCardBufferServices();
		travAgName = "Thread1";
		card = "2453-9512-0000-3698";

		orderId = "order1";
		isConfirm = true;
	}

	@Test
	public void testSetCardCell() {
		ccService.setCardCell(travAgName, operation, card);
		String expResult = travAgName + "-" + operation + "-" + card;

		String result = ccService.getCardCell(1);

		String result1 = ccService.getCardCell(0);

		assertEquals(expResult, result);

		assertNull(result1);
	}
	
	@Test
	public void testSetOrderStatus() {
		ccService.setOrderStatus(orderId, travAgName, isConfirm);
		String expResult11 = orderId + "-" + travAgName +"-valid";
		String result11 = ccService.getOrderStatus(1);
		String result21 = ccService.getOrderStatus(2);

		assertEquals(expResult11, result11);

		assertNull(result21);
		
		isConfirm = false;
		ccService.setOrderStatus(orderId, travAgName, isConfirm);
		String expResult21 = orderId + "-" + travAgName + "-no valid";
		String result211 = ccService.getOrderStatus(1);
		String result22 = ccService.getOrderStatus(2);

		assertEquals(expResult21, result211);

		assertNull(result22);
	}

}
