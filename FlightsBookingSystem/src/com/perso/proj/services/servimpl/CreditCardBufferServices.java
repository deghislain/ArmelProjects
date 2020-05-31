/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.services.servinterface.ICreditCardBufferServices;

/**
 * @author deghislain
 *
 */
public class CreditCardBufferServices implements ICreditCardBufferServices {
	private String[] cardAppBuffer = new String[5];

	private String[] orderConfirmBuffer = new String[5];

	public CreditCardBufferServices() {
		for (int i = 0; i < cardAppBuffer.length; i++) {
			this.cardAppBuffer[i] = null;
		}
	}

	@Override
	// This method allows a Travel Agency to apply for a credit card and obtain a
	// response from the Bank
	public void setCardCell(String travAgName, String operation, String card) {
		this.writeCardCell(travAgName, operation, card);
	}

	// setCardCell method ilplementation
	private void writeCardCell(String travAgName, String operation, String card) {
		synchronized (this.cardAppBuffer) {
			String status = travAgName + "-" + operation + "-" + card;
			int index = Character.getNumericValue(travAgName.charAt(travAgName.length() - 1));
			if (this.cardAppBuffer[index] == null) {
				this.cardAppBuffer[index] = status;
			}
		}
	}

	@Override
	// This method allows a Travel Agency(TA) to receive an answer for a credit card
	// application and a bank to receive credit card application from TA
	public String getCardCell(int index) {
		return this.readCardCell(index);
	}

	private String readCardCell(int index) {
		String status = null;
		synchronized (this.cardAppBuffer) {
			if (cardAppBuffer[index] != null && !cardAppBuffer[index].equals("")) {
				status = cardAppBuffer[index];
				cardAppBuffer[index] = null;
			}
		}

		return status;
	}

	@Override
	// This method allows a Bank to confirm a order
	public void setOrderStatus(String orderId, String senderId, boolean isConfirm) {
		this.writeCell(orderId, senderId, isConfirm);
	}

	private void writeCell(String orderId, String senderId, boolean isConfirm) {
		synchronized (this.orderConfirmBuffer) {
			String status = orderId + "-" + senderId;
			if (isConfirm) {
				status = status + "-valid";
			} else {
				status = status + "-no valid";
			}
			int index = Character.getNumericValue(senderId.charAt(senderId.length() - 1));
			if (this.orderConfirmBuffer[index] == null) {
				this.orderConfirmBuffer[index] = status;
			}
		}
	}

	@Override
	// This method provide the bank confirmation for an order
	public String getOrderStatus(int index) {
		return this.readCell(index);
	}

	private String readCell(int index) {
		String status = null;
		synchronized (this.orderConfirmBuffer) {

			if (orderConfirmBuffer[index] != null) {
				status = orderConfirmBuffer[index];
				orderConfirmBuffer[index] = null;
			}
		}

		return status;
	}

}
