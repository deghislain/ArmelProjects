/**
 * 
 */
package com.perso.proj.services.servinterface;

/**
 * @author deghislain
 *
 */
public interface IAccountServices {

	 //This method load an account
    public void deposit(double amount);
    
    //This method operate the payement
    public boolean charge(double amount);

    public String getCardNumber();
    
    public double getCurrentBalance();
    
    
}
