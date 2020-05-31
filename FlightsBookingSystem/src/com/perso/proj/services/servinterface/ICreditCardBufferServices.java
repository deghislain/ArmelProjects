/**
 * 
 */
package com.perso.proj.services.servinterface;

/**
 * @author deghislain
 *
 */
public interface ICreditCardBufferServices {

	public void setCardCell(String travAgName, String operation, String card);
	
    public String getCardCell(int index);
    
    public void setOrderStatus(String orderId, String senderId, boolean isConfirm);
    
    public String getOrderStatus(int index);
}
