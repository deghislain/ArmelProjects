/**
 * 
 */
package com.perso.proj.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.entities.Order;
import com.perso.proj.enums.EOrderStatus;
import com.perso.proj.services.servimpl.MultiCellBufferServices;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;

/**
 * @author deghislain
 *
 */
public class TestMultiCellBufferServices {
	IMultiCellBufferServices buffer;
	Order order;
	Order recOrder;
	
	@BeforeEach
	public void setUp() {
		Random rand = new Random();
		this.buffer = new MultiCellBufferServices();
		this.order = new Order();
		String orderId = "O" + new Date().getTime() + rand.nextInt(5000);
		this.order.setAmount(10);
		this.order.setCreditCardNumber("2453.9512.0000.3698");
		this.order.setReceiverId("ThreadAC1");
		this.order.setSenderId("ThreadTA1");
		this.order.setStatus(EOrderStatus.NEW);
		this.order.setUnitPrice(55);
		this.order.setOrderId(orderId);
		
		System.out.println("orderId "+orderId);
		
		
		this.recOrder = new Order();
	}
	
	@Test
	public void testSetAndReadOneCell() {
		this.buffer.setOneCell(this.order);
		
		this.recOrder = this.buffer.getOneCell();
		
		assertEquals(this.order.getAmount(), this.recOrder.getAmount());
		assertEquals(this.order.getOrderId(), this.recOrder.getOrderId());
		assertEquals(this.order.getCreditCardNumber(), this.recOrder.getCreditCardNumber());
		
	}
	
}
