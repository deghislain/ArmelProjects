/**
 * 
 */
package com.perso.proj.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author deghislain
 *
 */
public class DateUtils {
	public static String getCurrentTime() {
		 Date currentDate = new Date();
		 return  DateFormat.getTimeInstance().format(currentDate);  
	}
}
