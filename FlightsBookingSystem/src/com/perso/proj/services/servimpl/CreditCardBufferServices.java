/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.enums.EBSOperations;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;

/**
 * @author deghislain
 *
 */
public class CreditCardBufferServices implements ICreditCardBufferServices {
	private String[] cardAppBuffer = new String[5];

	public CreditCardBufferServices() {
		for (int i = 0; i < cardAppBuffer.length; i++) {
			this.cardAppBuffer[i] = null;
		}
	}

	@Override
	// This method allows communication with the Bank
	public void setCardCell(String travAgName, String orderId, EBSOperations operation, String card, double amount) {
		this.writeCardCell(travAgName, orderId, operation, card, amount);
	}

	// setCardCell method ilplementation
	private void writeCardCell(String travAgName, String orderId, EBSOperations operation, String card, double amount) {
		synchronized (this.cardAppBuffer) {
			String status = travAgName + "-" + orderId + "-" + operation.name() + "-" + card + "-" + amount;
			int index = Character.getNumericValue(travAgName.charAt(travAgName.length() - 1));
			if (this.cardAppBuffer[index] == null) {
				this.cardAppBuffer[index] = status;
			}
		}
	}

	@Override
	// This method allows communication with the Bank
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
					String operation = token[2];
					
					if((operation.equals(EBSOperations.CONFIRM.name()) 
					 || operation.equals(EBSOperations.DELIVERY.name())
					 || operation.equals(EBSOperations.DECLINE.name())) && reader.equals("getFeedBack")){
						//the reading of a cell is opened but only a valid combination operation-reader(method performing reading)can consume 
						cardAppBuffer[index] = null;
					}else if(operation.equals(EBSOperations.CHARGE.name()) && reader.equals("charge")) {
						cardAppBuffer[index] = null;
					}else if(operation.equals(EBSOperations.DEPOSIT.name()) && reader.equals("deposit")) {
						cardAppBuffer[index] = null;
					}else if(operation.equals(EBSOperations.APPLICATION.name()) && reader.equals("process")) {
						cardAppBuffer[index] = null;
					}
				}
				
			}
		}

		return status;
	}
}
