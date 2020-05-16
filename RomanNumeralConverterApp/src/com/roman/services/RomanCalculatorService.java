/**
 * 
 */
package com.roman.services;

import org.springframework.stereotype.Service;

/**
 * @author deghislain
 *
 */
@Service
public class RomanCalculatorService {

	public String convert(String numeral, String from) {
		return this.doTheMaths(numeral, from);
	}
	
	private String doTheMaths(String numeral, String from) {
		String result = "";
		
		return result;
	}
	
}
