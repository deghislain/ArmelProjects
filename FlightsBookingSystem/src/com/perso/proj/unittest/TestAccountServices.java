/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.entities.Account;
import com.perso.proj.services.servimpl.AccountServices;
import com.perso.proj.services.servinterface.IAccountServices;

/**
 * @author deghislain
 *
 */
public class TestAccountServices {
	IAccountServices account;
	String card;
	Account acc;
	@BeforeEach
	public void setUp() {
		card = "2453-9512-0000-3698";
		double initAmount = 1500;
		acc = new Account();
		acc.setBalance(initAmount);
		acc.setCardNumber(card);
		acc.setOwner("travelAgency1");
		account = new AccountServices();
	}

	@Test
	public void testDeposit() {
		double depAmount = 100;
		acc = account.deposit(acc,depAmount);
		double balance = acc.getBalance();

		assertEquals(1600, balance);
	}

	@Test
	public void testCharge() {
		double charAmount = 300;
		acc = account.charge(acc,charAmount);
		double balance = acc.getBalance();

		assertEquals(1200, balance);
		
		charAmount = 3000;
		acc = account.charge(acc,charAmount);
		
		assertNull(acc);
	}
	
	

}
