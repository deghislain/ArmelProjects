/**
 * 
 */
package com.perso.proj.services.servinterface;

import com.perso.proj.enums.EBSOperations;

/**
 * @author deghislain
 *
 */
public interface ICreditCardBufferServices {

	public void setCardCell(String travAgName, String orderId, EBSOperations operation, String card, double amount);
	
    public String getCardCell(int index, String reader);
    
}
