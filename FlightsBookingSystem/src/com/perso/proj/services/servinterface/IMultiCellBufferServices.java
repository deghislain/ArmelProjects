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
	public void setOneCell(Order order);
    public Order getOneCell();
}
