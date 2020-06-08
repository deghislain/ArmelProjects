/**
 * 
 */
package com.perso.proj.services.servinterface;

import com.perso.proj.entities.Order;

/**
 * @author deghislain
 *
 */
public interface IMultiCellBufferServices {
	//This method is used to store an order within the buffer by TA
	public void setOneCell(Order order);
	
	//This method is used to get one order from buffer by AC
    public Order getOneCell();

}
