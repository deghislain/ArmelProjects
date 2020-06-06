/**
 * 
 */
package com.perso.proj.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author deghislain
 *
 */
public class UtilityClass {
	public static String getCurrentTime() {
		 Date currentDate = new Date();
		 return  DateFormat.getTimeInstance().format(currentDate);  
	}
	
	public static String getOrderId() {
		 Random rand = new Random();
		return "O" + new Date().getTime() + rand.nextInt(5000);
	}
}
