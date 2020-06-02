/**
 * 
 */
package com.perso.proj.services.servinterface;

import com.perso.proj.enums.EBSOperationsEnum;

/**
 * @author deghislain
 *
 */
public interface ICreditCardBufferServices {

	public void setCardCell(String travAgName, EBSOperationsEnum operation, String card, String confirmation, double amount);
	
    public String getCardCell(int index, String reader);
    
    public void setOrderStatus(String orderId, String senderId, boolean isConfirm);
    
    public String getOrderStatus(int index);
}
