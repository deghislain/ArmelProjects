/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.services.servinterface.IAccountServices;

/**
 * @author deghislain
 *
 */
public class AccountServices implements IAccountServices {
	// private String travAgName;
	private String cardNumber;
	private double balance;

	public AccountServices(String card, double initAmount) {
		// this.travAgName = ta;
		this.cardNumber = card;
		this.balance = initAmount;
	}

	@Override
	// This method load an account
	public void deposit(double amount) {
		Object b = this.balance;
		synchronized (b) {
			this.balance += amount;
		}
	}

	@Override
	//This method operate the payement
    public boolean charge(double amount){
        boolean isValid = false;
        Object b = this.balance;
        synchronized (b) {
            if (this.balance >= amount)
            {
                this.balance -= amount;
                isValid = true;
            }
        }
        return isValid;
    }

	@Override
	public String getCardNumber() {
		return this.cardNumber;
	}

	@Override
	public double getCurrentBalance() {
		return this.balance;
	}

}
