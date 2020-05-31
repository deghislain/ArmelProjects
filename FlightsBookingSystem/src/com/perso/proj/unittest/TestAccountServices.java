/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.services.servimpl.AccountServices;
import com.perso.proj.services.servinterface.IAccountServices;

/**
 * @author deghislain
 *
 */
public class TestAccountServices {
	IAccountServices account;
	String card;
	@BeforeEach
	public void setUp() {
		card = "2453-9512-0000-3698";
		double initAmount = 1500;
		account = new AccountServices(card, initAmount);
	}

	@Test
	public void testDeposit() {
		double depAmount = 100;
		account.deposit(depAmount);
		double balance = account.getCurrentBalance();

		assertEquals(1600, balance);
	}

	@Test
	public void testCharge() {
		double charAmount = 300;
		account.charge(charAmount);
		double balance = account.getCurrentBalance();

		assertEquals(1200, balance);
	}
	
	@Test
	public void testGetCardNumber() {
		String cn = account.getCardNumber(); 
		
		assertEquals(card, cn);
	}

}
