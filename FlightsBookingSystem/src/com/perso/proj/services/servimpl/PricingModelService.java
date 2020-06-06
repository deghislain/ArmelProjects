/**
 * 
 */
package com.perso.proj.services.servimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import com.perso.proj.services.servinterface.IPricingModelService;

/**
 * @author deghislain
 *
 */
public class PricingModelService implements IPricingModelService{
	 // Indicates the percentage to apply to the current price in order to ajust the price based on the current weekday 
    private HashMap<String, Integer> dailyPriceVariationRate;
    
    // Indicate the minimum price for a ticket
    private  final static int MIN_PRICE = 50;
    
    // Indicates the maximum price for a ticket
    private  final static int MAX_PRICE = 200;
    
    // Contains the days of a week
    private String[] weekDays;
    
  //Indicate the current price
    private double CURRENT_PRICE = 50;

    //Indicates a specific week day
    private String weekDay;
    
    //Indicates the percentage of the current price to be reduce
    private float cutRate;

    //Indicates the initial number of ticket hold by the airline Company
    private int initStockTicket;
    
  //Indicates the current number of ticket hold by the airline Company
    private int numAvailableTick;

    //Indicates the number of orders receive by the Airline company since the last price cut 
    private int numOrderRecSinceLPC;
    
    //Indicates the number of orders receive by the Airline company befor the last price cut
    private int numOrdRecAtLPC;

    //Indicates when the Airline start selling ticket
    private Date dateInitSale;

    
    public PricingModelService(int initStockTicket)
    {
        this.dailyPriceVariationRate = new HashMap<String, Integer>();
        this.dailyPriceVariationRate.put("Monday", -8);
        this.dailyPriceVariationRate.put("Tuesday", -4);
        this.dailyPriceVariationRate.put("Wednesday", 0);
        this.dailyPriceVariationRate.put("Thursday", 4);
        this.dailyPriceVariationRate.put("Friday", 6);
        this.dailyPriceVariationRate.put("Saturday", 8);
        this.dailyPriceVariationRate.put("Sunday", 10);
        this.weekDays = new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        this.weekDay = "Sunday";
        this.cutRate = 0;
        this.initStockTicket = initStockTicket;
        this.numAvailableTick = initStockTicket;
        this.numOrderRecSinceLPC = 0;
        this.numOrdRecAtLPC = 0;
        this.dateInitSale = new Date();
    }
    
   @Override
	public void runPricingModel() {
		//this.updateSalesData(numOrdRecAtLPC, numOrdRecSinceLPC, numAvailableTick, dateInitSale);
	    this.calculateCutRate();
		this.updateCurrentPrice();
	}
   
    
    //This method calculate the percentage that we want to apply to the current price in order to reduce or increase it
    private void calculateCutRate()
    {
    	  float percTicSold = 0;
    	  int numTicketSold = 0;
    	  
    	//float newRate =0;
        //The price cut has not happen yet, and the number of ticket sold is too low
        if (numOrderRecSinceLPC == 0 && numOrdRecAtLPC == 0)
        {
            //Here we estimate for how long the company have been selling ticket 
            float saleTime = 1f*(new Date().getTime() - dateInitSale.getTime())/1000;
          
           // Object boxSaleTime = saleTime;

            //Here we calculate the percentage of ticket sold during the saleTime
            if( this.initStockTicket != this.numAvailableTick) {
            	numTicketSold = this.initStockTicket - this.numAvailableTick;
            	 percTicSold = (numTicketSold*1f / this.initStockTicket);
            }

            if (saleTime > 60 && percTicSold <= 0.2)
            {
            	this.cutRate -= 10; //with this we will have a 10% reduction on the current price
            }
            else if (saleTime > 60 && percTicSold > 0.3)
            {
            	this.cutRate += 5; //with this we will have a 5% increase on the current price
            }
        }//The price cut has already happen at least once
        else if (this.numOrderRecSinceLPC != 0 && this.numOrdRecAtLPC != 0)
        {
        	//Here we calculate the percentage of ticket sold during the saleTime
            if( this.initStockTicket != this.numAvailableTick) {
            	numTicketSold = this.initStockTicket - this.numAvailableTick;
            	 percTicSold = (numTicketSold*1f / this.initStockTicket);
            }
            // Here we estimate the growth of orders since the last price cut 
            float perOrderGrowthSinceLPC = 1f *this.numOrderRecSinceLPC / (this.numOrdRecAtLPC + this.numOrderRecSinceLPC);
            
            if (perOrderGrowthSinceLPC <= 0.2)
            {
            	this.cutRate -= 5; //with this we will have a 5% reduction on the current price
            }
            else
            {
            	this.cutRate += 5; //with this we will have a 1% increase on the current price
            }
            
            if(CURRENT_PRICE >= 200 && percTicSold < 0.8 && this.cutRate > 0) { //the price has reached its max value and we still have a lot of ticket
            	this.cutRate -= 10;
            }
        }
    	
    }
    
    // This method initialize the ticket price based on the week day
    private void updateCurrentPrice(){
		// get a random day as current day for simulation purpose
		Random r = new Random();
		int indexCurrentDay = getIndexCurrentDay();
		if (indexCurrentDay != -1) {
			int indexDay = r.nextInt(this.weekDays.length);
			if(indexDay <= indexCurrentDay) {
				indexDay +=1; // To have days moving forward, for example monday, tuesday, wednesday... and not monday then sunday, saturday....
			}
			if (indexDay == this.weekDays.length) {
				indexDay = 0;
			}
			this.weekDay = weekDays[indexDay];
			int dailyRate = this.dailyPriceVariationRate.get(this.weekDay);

			double currPrice = CURRENT_PRICE;
			
			if(currPrice >=  MIN_PRICE) {
				currPrice = currPrice + (currPrice * dailyRate) / 100;
				double percDrop = currPrice/CURRENT_PRICE;
				if(percDrop <= 0.1 && currPrice >=  MIN_PRICE) {
					currPrice = currPrice + currPrice * this.cutRate;
				}
			}
			// This is to keep the price within the range as specified
			if (currPrice < MIN_PRICE) {
				currPrice = MIN_PRICE;
			}
			if (currPrice > MAX_PRICE) {
				currPrice = MAX_PRICE;
			}
			CURRENT_PRICE = currPrice;
		}
	}
    private int getIndexCurrentDay() {
    	int indexDay = -1;
    	for(int index = 0; index < this.weekDays.length; index++) {
    		if(this.weekDay.equals(this.weekDays[index])) {
    			indexDay = index;
    			break;
    		}
    	}
    	return indexDay;
    }
	
	//This method implement updateTicketPrice
    public void updateSalesData(int numOrdRecAtLPC, int numOrdRecSinceLPC, int numAvailableTick, Date dateInitSale)
    { 
        this.numAvailableTick = numAvailableTick;
        this.numOrderRecSinceLPC = numOrdRecSinceLPC;
        this.numOrdRecAtLPC = numOrdRecAtLPC;
        this.dateInitSale = dateInitSale;
        //calculateTicketPrice();
    }

	@Override
	public double getTicketCurrentPrice() {
		return CURRENT_PRICE;
	}

	@Override
	public float getCurrentCutRate() {
		return this.cutRate;
	}

}
