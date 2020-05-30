/**
 * 
 */
package com.perso.proj.services.servinterface;

import java.util.Date;

/**
 * @author deghislain
 *
 */
public interface IPricingModelService {
	void runPricingModel(int numOrRecBefLPC, int numOrdRecSinceLPC, int numAvailableTick, Date dateInitSale);
	
	//return the current price of a ticket
	public double getTicketCurrentPrice();

    public float getCurrentCutRate();
}
