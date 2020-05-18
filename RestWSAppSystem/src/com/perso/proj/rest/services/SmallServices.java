/**
 * 
 */
package com.perso.proj.rest.services;

/**
 * @author deghislain
 *
 */
public class SmallServices {
	public String convertTemperature(int temp, char from) {
		int nTemp = 0;
		String result = "";
		if (from == 'C') {
			result = temp + " Degrees Celsius in Fahrenheit is: ";
			nTemp = this.C2F(temp);
		} else {
			nTemp = this.F2C(temp);
			result = temp + " Fahrenheit in Degrees Celssius is: ";
		}

		result += Integer.toString(nTemp);
		return result;
	}

	private int C2F(int c) {
		float f = c * 9f / 5 + 32;
		return Math.round(f);
	}

	private int F2C(int f) {
		float c = (f - 32f) * 5 / 9;
		return Math.round(c);
	}
}
