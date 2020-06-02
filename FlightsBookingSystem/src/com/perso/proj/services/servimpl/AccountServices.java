/**
 * 
 */
package com.perso.proj.services.servimpl;

import com.perso.proj.entities.Account;
import com.perso.proj.services.servinterface.IAccountServices;

/**
 * @author deghislain
 *
 */
public class AccountServices implements IAccountServices {
	
	public AccountServices() {
		
	}

	@Override
	// This method load an account
	public Account deposit(Account acc, double amount) {
		synchronized (acc) {
			double newBalance = acc.getBalance()+ amount;
			acc.setBalance(newBalance);
		}
		return acc;
	}

	@Override
	//This method operate the payement
    public Account charge(Account acc, double amount){
        synchronized (acc) {
            if (acc.getBalance() >= amount){
            	double newBalance = acc.getBalance() - amount;
            	acc.setBalance(newBalance);
            }else {
            	return null;
            }
        }
        return acc;
    }

	@Override
	public Account createAccount(String travAgName, String card, double initialAmount){
		Account acc = new Account();
		acc.setOwner(travAgName);
		acc.setCardNumber(card);
		acc.setBalance(initialAmount);
		return acc;
	}
	
	

}
