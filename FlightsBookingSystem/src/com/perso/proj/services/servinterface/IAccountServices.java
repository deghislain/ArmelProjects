/**
 * 
 */
package com.perso.proj.services.servinterface;

import com.perso.proj.entities.Account;

/**
 * @author deghislain
 *
 */
public interface IAccountServices {

	 //This method load an account
    public Account deposit(Account acc, double amount);
    
    //This method operate the payement
    public Account charge(Account acc, double amount);
    
    public Account createAccount(String travAgName, String card, double initialAmount);
    
    
}
