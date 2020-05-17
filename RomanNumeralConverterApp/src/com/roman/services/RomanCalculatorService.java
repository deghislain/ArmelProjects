/**
 * 
 */
package com.roman.services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

/**
 * @author deghislain
 *
 */
@Service
public class RomanCalculatorService {
	private HashMap<String, Integer> mapNum = new HashMap<String, Integer>();
	
	
	public RomanCalculatorService() {
		//here we map the roman base numeral with equivalent number
		mapNum.put("I", 1);
		mapNum.put("V", 5);
		mapNum.put("X", 10);
		mapNum.put("L", 50);
		mapNum.put("C", 100);
		mapNum.put("D", 500);
		mapNum.put("M", 1000);
	}
	public String convert(String number, String from) {
		return this.doTheMaths(number, from);
	}
	
	private String doTheMaths(String number, String from) {
		if(from.equals("Roman-to-Number")) {
			return this.toNumber(number);
		}else {
			return toRoman(number);
		}
	}
	
	private String toNumber(String number) {
		number = number.toUpperCase();
		String firstNum = String.valueOf(number.charAt(0));
		String result = "";
	    int tot = mapNum.get(firstNum);//we initialize total we the first roman numeral in our input string
			
			for(int index =0; index < number.length(); index ++) {
				if((index +1) < number.length()) {
					String curr = String.valueOf(number.charAt(index));
					String next = String.valueOf(number.charAt(index+1));
					
					//if the current number is > to the next we add next to total
					if(mapNum.get(curr)>= mapNum.get(next)) {
						tot += mapNum.get(next);
						//if the current number is < to the next we remove it from total
					}else {
						//we have to remove the current number twice because it was added once in the previous iteration
						tot = tot + mapNum.get(next) - 2*mapNum.get(curr);
					}
				}
			}
			result = String.valueOf(tot);
		return result;
	}
	
	private String toRoman(String number) {
		String result = "";
		
		return result;
	}
	
}
