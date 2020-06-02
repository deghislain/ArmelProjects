/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.enums.EBSOperationsEnum;
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
	public void setCardCell(String travAgName, EBSOperationsEnum operation, String card, String confirmation, double amount) {
		this.writeCardCell(travAgName, operation, card, confirmation, amount);
	}

	// setCardCell method ilplementation
	private void writeCardCell(String travAgName, EBSOperationsEnum operation, String card, String confirmation, double amount) {
		synchronized (this.cardAppBuffer) {
			String status = travAgName + "-" + operation + "-" + card + "-" + confirmation + "-" + amount;
			int index = Character.getNumericValue(travAgName.charAt(travAgName.length() - 1));
			if (this.cardAppBuffer[index] == null) {
				this.cardAppBuffer[index] = status;
			}
		}
	}

	@Override
	// This method allows a Travel Agency(TA) to receive an answer for a credit card
	// application and a bank to receive credit card application from TA
	public String getCardCell(int index, String reader) {
		return this.readCardCell(index, reader);
	}

	private String readCardCell(int index, String reader) {
		String status = null;
		synchronized (this.cardAppBuffer) {
			if (cardAppBuffer[index] != null && !cardAppBuffer[index].equals("")) {
				status = cardAppBuffer[index];
				String[] token = status.split("\\-");
				if(null != token && token.length > 0) {
					String operation = token[1];
					
					if((operation.equals(EBSOperationsEnum.FEEDBACK.name()) 
					 || operation.equals(EBSOperationsEnum.DELIVERY.name())) && reader.equals("getFeedBack")){
						cardAppBuffer[index] = null;
					}else if(operation.equals(EBSOperationsEnum.CHARGE.name()) && reader.equals("charge")) {
						cardAppBuffer[index] = null;
					}else if(operation.equals(EBSOperationsEnum.DEPOSIT.name()) && reader.equals("deposit")) {
						cardAppBuffer[index] = null;
					}else if(operation.equals(EBSOperationsEnum.APPLICATION.name()) && reader.equals("process")) {
						cardAppBuffer[index] = null;
					}
				}
				
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
