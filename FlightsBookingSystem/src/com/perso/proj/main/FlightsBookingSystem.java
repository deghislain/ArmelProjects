/**
 * 
 */
package com.perso.proj.main;

import com.perso.proj.localcomp.AirlineCompany;
import com.perso.proj.localcomp.PriceCutEventEmiter;
import com.perso.proj.localcomp.TravelAgency;
import com.perso.proj.services.servimpl.BankServices;
import com.perso.proj.services.servimpl.CreditCardBufferServices;
import com.perso.proj.services.servimpl.MultiCellBufferServices;
import com.perso.proj.services.servinterface.IBankServices;
import com.perso.proj.services.servinterface.ICreditCardBufferServices;
import com.perso.proj.services.servinterface.IMultiCellBufferServices;

/**
 * @author deghislain
 *
 */
public class FlightsBookingSystem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IMultiCellBufferServices buffer = new MultiCellBufferServices();
		ICreditCardBufferServices creditCardBuffer = new CreditCardBufferServices();
		IBankServices bank = new BankServices(creditCardBuffer);
		PriceCutEventEmiter pce = new PriceCutEventEmiter();
		final int initialStock = 1000;
		
	
         for (int i = 0; i < 3; i++){
        	 AirlineCompany aci =  new AirlineCompany(buffer, creditCardBuffer, bank, pce, initialStock);
        	 aci.setName("AC" + i);
        	 aci.start();
         }
         
         
         for (int i = 0; i < 5; i++)
         {
        	 TravelAgency  tai = new TravelAgency(buffer, creditCardBuffer, bank);
        	 			   tai.setName("TA" + i);
                           tai.start();
                           pce.addListener(tai);
         }
		
	}

}
