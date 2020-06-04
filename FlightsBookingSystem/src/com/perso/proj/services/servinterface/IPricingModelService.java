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
	void runPricingModel();
	
	//return the current price of a ticket
	public double getTicketCurrentPrice();

    public float getCurrentCutRate();
    
    public void updateSalesData(int numOrdRecAtLPC, int numOrdRecSinceLPC, int numAvailableTick, Date dateInitSale);
}
