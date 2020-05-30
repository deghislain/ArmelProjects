/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.Assert.assertNotEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.services.servimpl.PricingModelService;

/**
 * @author deghislain
 *
 */
public class TestPricingModel {
	PricingModelService pm;
	double minPrice;
	int numAvailableTick;
	int numOrderRecsinceLPC;
	int numOrRecAtLPC;
	Date dateInitSale;
	float initRateCut;
	
	@BeforeEach
	public void setUp() throws Exception {
		System.out.println("setUp");
		numAvailableTick = 300;
		numOrderRecsinceLPC = 0;
		numOrRecAtLPC = 0;
		minPrice = 50;
		dateInitSale = new Date();
		initRateCut =0;
	}
	
	@Test
	public void testRunPricingModel() throws InterruptedException {
		pm = new PricingModelService(numAvailableTick);
		float curRateCut = 0;
		double curPrice = 0;
		int count = 15;
		while(count-- >0) {
			pm.runPricingModel(numOrRecAtLPC, numOrderRecsinceLPC,  numAvailableTick, dateInitSale);
			numAvailableTick -= 20;
			numOrderRecsinceLPC += 5;
			numOrRecAtLPC += 3;
			curRateCut =pm.getCurrentCutRate();
			System.out.println(curRateCut);
			curPrice =pm.getTicketCurrentPrice();
			System.out.println(curPrice);
			
		}
		assertNotEquals(minPrice, curPrice);
		assertNotEquals(initRateCut, curRateCut);
	}

	

}
