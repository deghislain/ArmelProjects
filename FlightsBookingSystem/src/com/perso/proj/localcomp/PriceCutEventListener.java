/**
 * 
 */
package com.perso.proj.localcomp;

/**
 * @author deghislain
 *
 */
public interface PriceCutEventListener {
	//public void onPriceCut(double priceCut);
	
	public void onPriceChange(double newPrice);
	
	public void onStopEvent(boolean isStop);
}
