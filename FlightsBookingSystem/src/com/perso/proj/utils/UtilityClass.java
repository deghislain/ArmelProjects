/**
 * 
 */
package com.perso.proj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author deghislain
 *
 */
public class UtilityClass {
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss.SS");
		 String currentDate = sdf.format(new Date());
		 return currentDate;
	}
	
	public static String getOrderId(boolean isSpecialOrder) {
		 Random rand = new Random();
		 String prefix = "";
		 if(isSpecialOrder) {
			 prefix = "OS";
		 }else {
			 prefix = "O";
		 }
		return prefix + new Date().getTime() + rand.nextInt(5000);
	}
}
