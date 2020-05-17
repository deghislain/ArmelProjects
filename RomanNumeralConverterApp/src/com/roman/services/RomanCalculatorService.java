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
		// here we map the roman base numeral with equivalent number
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
		if (from.equals("Roman-to-Number")) {
			return this.toNumber(number);
		} else {
			return toRoman(number);
		}
	}

	private String toNumber(String number) {
		number = number.toUpperCase();
		String firstNum = "";
		firstNum = String.valueOf(number.charAt(0));
		if(mapNum.get(firstNum) == null) {
			return number + " is an Invalid Roman Number"; 
		}
		String result = "";
		int tot = mapNum.get(firstNum);// we initialize total we the first roman numeral in our input string

		for (int index = 0; index < number.length(); index++) {
			if ((index + 1) < number.length()) {
				String curr = String.valueOf(number.charAt(index));
				String next = String.valueOf(number.charAt(index + 1));

				// if the current number is > to the next we add next to total
				if (mapNum.get(curr) >= mapNum.get(next)) {
					tot += mapNum.get(next);
					// if the current number is < to the next we remove it from total
				} else {
					// we have to remove the current number twice because it was added once in the
					// previous iteration
					tot = tot + mapNum.get(next) - 2 * mapNum.get(curr);
				}
			}
		}
		result = String.valueOf(tot);
		return result;
	}

	private String toRoman(String number) {
		String result = number + " In Roman is: ";
		int intNum = Integer.parseInt(number);

		if (intNum == 1) {
			return number + " In Roman is I";
		}

		int range = getRange(intNum);

		if (range <= 5) {
			result += this.addMaxFive(intNum);
		} else if (range <= 10) {
			result += this.addMaxTen(intNum);
		} else if (range <= 50) {
			result += this.addMaxFifty(intNum);
		} else if (range <= 100) {
			result += this.addMaxHund(intNum);
		} else if (range <= 500) {
			result += this.addMaxFiveHund(intNum);
		} else {
			result += this.addMaxThous(intNum);
		}
		return result;
	}

	// this method return the closest base roman numeral(I,V,X...) to the current
	// input
	private int getRange(int number) {
		if (number <= 5) {
			return 5;
		} else if (number <= 10) {
			return 10;
		} else if (number <= 50) {
			return 50;
		} else if (number <= 100) {
			return 100;
		} else if (number <= 500) {
			return 500;
		} else if (number <= 1000) {
			return 1000;
		}

		return 1;
	}

	// this method convert number <= 5 to Roman Numeral
	private String addMaxFive(int number) {
		String result = "";
		if (number == 5) {
			return "V";
		} else if (number == 4) {
			return "IV";
		} else {
			int i = 0;
			while (i < number) {
				result += "I";
				i++;
			}
		}

		return result;
	}

	// this method convert number <= 10 to Roman Numeral
	private String addMaxTen(int number) {
		String result = "";

		if (number == 10) {
			return "X";
		} else if (number == 9) {
			return "IX";
		} else {
			if (number > 5) {
				number = number - 5;
				result += "V";
			}
		}
		result += this.addMaxFive(number);
		
		return result;
	}

	// this method convert number <= 50 to Roman Numeral
	private String addMaxFifty(int number) {
		String result = "";
		if (number == 50) {
			return "L";
		} else if (number >= 40) {
			result += "XL";
			number -= 40;
		} else {
			while (number > 10) {
				result += "X";
				number -= 10;
			}
		}

		result += this.addMaxTen(number);

		return result;
	}

	// this method convert number <= 100 to Roman Numeral
	private String addMaxHund(int number) {
		String result = "";
		if (number == 100) {
			return "C";
		} else if (number >= 90) {
			result += "XC";
			number -= 90;
			result += this.addMaxTen(number);
		} else if (number > 50) {
			result += "L";
			number -= 50;
		}

		result += this.addMaxFifty(number);

		return result;
	}

	// this method convert number <= 500 to Roman Numeral
	private String addMaxFiveHund(int number) {
		String result = "";
		if (number == 500) {
			result += "D";
		} else if (number >= 400) {
			result += "CD";
			number -= 400;
		} else {
			while (number > 100) {
				result += "C";
				number -= 100;
			}
		}

		result += this.addMaxHund(number);

		return result;
	}

	// this method convert number <= 1000 to Roman Numeral
	private String addMaxThous(int number) {
		String result = "";

		if (number == 1000) {
			return "M";
		} else if (number == 900) {
			result += "CM";
			number -= 900;
		} else {
			if (number > 500) {
				result += "D";
				number -= 500;
				result += this.addMaxFiveHund(number);
			}
		}

		return result;
	}

}
