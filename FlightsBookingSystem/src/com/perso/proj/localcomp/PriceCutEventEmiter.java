/**
 * 
 */
package com.perso.proj.localcomp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deghislain
 *
 */
public class PriceCutEventEmiter {
	
	List<PriceCutEventListener>listeners;
	
	public PriceCutEventEmiter() {
		listeners = new ArrayList<PriceCutEventListener>();
	}

	public void addListener(PriceCutEventListener lis) {
		listeners.add(lis);
	}
	
	public void emitPriceCutEvent(double newPrice) {
		for(PriceCutEventListener pceListener : listeners) {
			pceListener.onPriceCut(newPrice);
		}
	}
	
	public void sendNewPriceToTA(double newPrice) {
		for(PriceCutEventListener pceListener : listeners) {
			pceListener.onPriceChange(newPrice);
		}
	}
}
