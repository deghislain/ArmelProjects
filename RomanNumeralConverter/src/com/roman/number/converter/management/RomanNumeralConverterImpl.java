package com.roman.number.converter.management;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.roman.number.converter.action.ConverterAction;


/**
 * 
 * RomanNumeralConverterImpl provide all the services necessary to convert a number 
 *
 */
public class RomanNumeralConverterImpl implements RomanNumeralConverter {
	String romanNumber = "";
	HashMap<Integer, String> romanNumberMap = new HashMap<Integer, String>();
	private static Logger logger = Logger.getLogger(ConverterAction.class);
	
	/**
	 * Convert a roman number to a number
	 * @param romanNumeral is The numeral to be converted.
	 */
	@Override
	public int fromRomanNumeral(String romanNumeral) {
		logger.debug("method fromRomanNumeral Enter:");
		return getTheNumericalNumber(romanNumeral);
	}

	/**
	 * convert a number to a Roman numeral
	 * @param number is The number to be converted.
	 */
	@Override
	public String toRomanNumeral(int number) {
		logger.debug("method toRomanNumeral Enter:");
		romanNumber = null;
		String numberValidity = valideRomanNumber(number);
		if(numberValidity.equals("invalid number")){
			return romanNumber;
		}
		romanNumberMap = getRomanNumberMap();
		romanNumber = romanNumberMap.get(number);
		if (romanNumber == null) {
			romanNumber = getTheRomanNumber(number, romanNumber);
		} 
		
		logger.debug("method toRomanNumeral End:");
		return romanNumber;
	}
	
	/**
	 * check if the number given in Input is valid: >0 and additive
	 * @param number is The number to be validated.
	 */
private String valideRomanNumber(int number){
	logger.debug("method valideRomanNumber Enter:");
		int rest = number % 50;
		
		if(number == 4 || number == 9 || number == 11 || number == 14 || number == 19 
			|| (rest >= 39 && rest<=49) || (rest >=400 && rest<500) 
			|| (number >= 39 && number<=49) || (number >=400 && number<500)
			|| (rest >= 900 && rest<=999) || (number >=900 && number<=999)){
			return "invalid number";
		}
		logger.debug("method valideRomanNumber End:");
		return "valid number";
	}
/**
 * create a map with the first 10 Roman numeral and Number
 *
 */
private HashMap<Integer, String>  getRomanNumberMap() {
	logger.debug("Class-> RomanNumeralConverterImpl ->method valideRomanNumber Enter:");
	if (romanNumberMap.isEmpty()) {
		romanNumberMap.put(new Integer(0), "-1");
		romanNumberMap.put(new Integer(4), "-1");
		romanNumberMap.put(new Integer(9), "-1");
		romanNumberMap.put(new Integer(1), "I");
		romanNumberMap.put(new Integer(2), "II");
		romanNumberMap.put(new Integer(3), "III");
		romanNumberMap.put(new Integer(5), "V");
		romanNumberMap.put(new Integer(6), "VI");
		romanNumberMap.put(new Integer(7), "VII");
		romanNumberMap.put(new Integer(8), "VIII");
	}
	return romanNumberMap;
}

/**
 * Convert a number to a roman numeral
 * @param number is The number to be converted.
 * @param romanNumber is The result.
 */
	private String getTheRomanNumber(int number, String romanNumber) {
		logger.debug("method getTheRomanNumber Enter:");
		switch (getTheRanchNumber(number)) {
		case UNDERFIFTY:
			romanNumber = manageNumberUnder50(number, romanNumber);
			break;
		case FIFTYTOHUNDRED:
			romanNumber = manageNumberFrom50ToHundred(number,romanNumber);
			break;
		case HUNDRETOFIVEHUNDRED:
			romanNumber = manageNumberFrom100ToFiveHundred(number,romanNumber);
			break;
		case FIVEHUNDREDTOTHOUSAND:
			romanNumber = manageNumberFrom500ToThousand(number,romanNumber);
			break;
		case UPTOTHOUSAND:
			romanNumber = manageNumberUpToThousand(number,romanNumber);
			break;
		default:
			break;
		}
		logger.debug("method getTheRomanNumber End:");
		return romanNumber;
	}

	/**
	 * determine the ranch of the number to be converted
	 * @param number is The number to be converted.
	 */
	private RanchNumber getTheRanchNumber(int number) {
		logger.debug("method getTheRanchNumber Enter:");
		if (number > 0 && number < 10) {
			return RanchNumber.UNDERTEN;
		}else if (number >= 10 && number < 50) {
			return RanchNumber.UNDERFIFTY;
		} else if (number >= 50 && number < 100) {
			return RanchNumber.FIFTYTOHUNDRED;
		} else if (number >= 100 && number < 500) {
			return RanchNumber.HUNDRETOFIVEHUNDRED;
		} else if (number >= 500 && number < 1000) {
			return RanchNumber.FIVEHUNDREDTOTHOUSAND;
		}
		logger.debug("method getTheRanchNumber End:");
		return RanchNumber.UPTOTHOUSAND;
	}
	
	/**
	 * Convert numbers under 50
	 * @param number is The number to be converted.
	 * @param romanNumber is The result.
	 */
	private String manageNumberUnder50(int number, String romanNumber){
		logger.debug("method manageNumberUnder50 Enter:");
		int numberOfTime = number / 10;
		int rest = number % 10;
		
		for (int i = 0; i < numberOfTime; i++) {
			if(romanNumber == null){
				romanNumber = "X";
			}else{
				romanNumber += "X";
			}
		}
		if(rest != 0 && rest != 4 && rest != 9){
			romanNumber += getRomanNumberMap().get(rest);
		}else if(rest == 0){
			return romanNumber;
		}else{
			romanNumber = null;
		}
		logger.debug("method manageNumberUnder50 End:");
		return romanNumber;
	}
	
	/**
	 * Convert numbers from 50 to 100
	 * @param number is The number to be converted.
	 * @param romanNumber is The result.
	 */
	private String manageNumberFrom50ToHundred(int number,String romanNumber){
		logger.debug("method manageNumberFrom50ToHundred Enter:");
		int numberOfTime = number / 50;
		int rest = number % 50;
			 if(numberOfTime ==1 && rest ==0){
				 if(romanNumber == null){
					 return romanNumber = "L";
				 }else return romanNumber += "L";
				 
			}else if(numberOfTime == 1 && rest >0){
				if(romanNumber == null){
					romanNumber = "L";
				 }else romanNumber += "L";
				return romanNumber = manageNumberUnder50(rest,romanNumber);
			}
		if(numberOfTime == 0 && rest >0){
			return romanNumber = manageNumberUnder50(rest,romanNumber);
		}
		logger.debug("method manageNumberFrom50ToHundred End:");
		return romanNumber;
	}

	/**
	 * Convert numbers from 100 to 500
	 * @param number is The number to be converted.
	 * @param romanNumber is The result.
	 */
	private String manageNumberFrom100ToFiveHundred(int number,String romanNumber){
		logger.debug("method manageNumberFrom100ToFiveHundred Enter:");
		int numberOfTime = number / 100;
		int rest = number % 100;
		
		 if(numberOfTime >=1){
			 romanNumber = addHundred(numberOfTime, romanNumber);
		 }
		 RanchNumber ranchNumber= getTheRanchNumber(rest);
		 if(ranchNumber.equals(RanchNumber.FIFTYTOHUNDRED)){
			 romanNumber = manageNumberFrom50ToHundred(rest,romanNumber);
		 }else if(ranchNumber.equals(RanchNumber.UNDERFIFTY)){
			 romanNumber =  manageNumberUnder50(rest,romanNumber);
		 }else if(ranchNumber.equals(RanchNumber.UNDERTEN)){
			 romanNumber += getRomanNumberMap().get(rest);
			 if((numberOfTime ==0 && rest == 0) || rest == 4 || rest == 9){
				 romanNumber = null; 
			 }
		 }else if(rest == 0){
			 return romanNumber;
		 }
		 logger.debug("method manageNumberFrom100ToFiveHundred Enter:");
		return romanNumber;
	}
	
	/**
	 * add a hundred numeral in roman to the result
	 * @param numberOfTime is The number of roman numeral to be added.
	 * @param romanNumber is The result.
	 */
	private String addHundred(int numberOfTime,String romanNumber){
		logger.debug("addHundred Enter:");
		if(romanNumber == null && numberOfTime == 1){
			return romanNumber = "C"; 
		}
		for (int i = 0; i <numberOfTime; i++) {
			if(romanNumber == null){
			 romanNumber = "C"; 
			}else{
				romanNumber += "C"; 
			}
		}
		logger.debug("addHundred End:");
		return romanNumber;
	}
	
	
	/**
	 * Convert numbers from 500 to 1000
	 * @param number is The number to be converted.
	 * @param romanNumber is The result.
	 */
	private String manageNumberFrom500ToThousand(int number,String romanNumber){
		logger.debug("method manageNumberFrom500ToThousand Enter:");
		int numberOfTime = number / 500;
		int rest = number % 500;
			 if(numberOfTime ==1 && rest ==0){
				 if(romanNumber == null){
					 return romanNumber = "D";
				 }else return romanNumber += "D";
				 
			}else if(numberOfTime == 1 && rest >0){
				//romanNumber += "D";
				 if(romanNumber == null){
					 romanNumber = "D";
				 }else romanNumber += "D";
				return romanNumber = manageNumberFrom100ToFiveHundred(rest,romanNumber);
			}
		if(numberOfTime == 0 && rest >0){
			return romanNumber = manageNumberFrom100ToFiveHundred(rest,romanNumber);
		}
		logger.debug("method manageNumberFrom500ToThousand Enter:");
		return romanNumber;
	}
	
	/**
	 * Convert numbers up to 1000
	 * @param number is The number to be converted.
	 * @param romanNumber is The result.
	 */
	private String manageNumberUpToThousand(int number,String romanNumber){
		logger.debug("method manageNumberUpToThousand Enter:");
		int numberOfTime = number / 1000;
		int rest = number % 1000;
		String numberValidity = valideRomanNumber(rest);
		if(numberValidity.equals("invalid number")){
			return romanNumber;
		}
		 if(numberOfTime >=1){
			 romanNumber = addThousand(numberOfTime, romanNumber);
		 }
		 if(rest>0){
			 romanNumber = manageNumberFrom500ToThousand(rest,romanNumber);
		 }
		 logger.debug("method manageNumberUpToThousand Enter:");
		return romanNumber;
	}
	/**
	 * add a thousand numeral in roman to the result
	 * @param numberOfTime is The number of roman numeral to be added.
	 * @param romanNumber is The result.
	 */
	private String addThousand(int numberOfTime,String romanNumber){
		logger.debug("method addThousand Enter:");
		if(romanNumber == null && numberOfTime == 1){
			romanNumber = "M"; 
				return romanNumber;
		}
		for (int i = 0; i <numberOfTime; i++) {
			if(romanNumber == null){
			 romanNumber = "M";
			}else {
				romanNumber += "M";
			}
		}
		logger.debug("method addThousand Enter:");
		return romanNumber;
	}
	
	/**
	 * Convert a Roman numeral to a number
	 * @param romanNumeral is The number to be converted.
	 */
	 private int getTheNumericalNumber(String romanNumeral){
		 logger.debug("method getTheNumericalNumber Enter:");
		 HashMap<String, Integer> romanMap = getTheRomanNumberMap();
		 int sum = 0;
		 int romanNumberLenght = 0;
		 if(romanNumeral.length() == 1){
			return  manageSingleNumber(romanNumeral.trim());
		 }else{
			 romanNumberLenght = romanNumeral.trim().length()-1;
		 }
		
		 char nextNumber = ' ';
		 int  nextNumberVal = 0;
		 char preNumber = ' ';
		 int  preNumberVal = 0;
		 for(int index = 0; index < romanNumberLenght; index++){
			 preNumber = romanNumeral.charAt(index);
			 if(romanMap.get(String.valueOf(preNumber)) == null){
				  return sum = -1;
			 }
			 preNumberVal = romanMap.get(String.valueOf(preNumber)).intValue();
			 if(index+1 <= romanNumberLenght){
				 nextNumber = romanNumeral.charAt(index+1);
				  if(nextNumber == ' '){
					  return sum = -1;
				  }
				  nextNumberVal = romanMap.get(String.valueOf(nextNumber) );
				 if(nextNumberVal >preNumberVal){
					 return sum = -1;
				 }else{
					 sum += preNumberVal;
				 }
			 }
		 }
		 sum += nextNumberVal;
		 logger.debug("method getTheNumericalNumber End:");
		 return sum;
	 }
	 
	 /**
		 * create a map with all the Roman Numeral
		 */
	 private HashMap<String, Integer> getTheRomanNumberMap(){
		 logger.debug("method getTheRomanNumberMap Enter:");
		 HashMap<String, Integer> romanMap = new HashMap<String, Integer>();
		 romanMap.put("I", 1);
		 romanMap.put("V", 5);
		 romanMap.put("X", 10);
		 romanMap.put("L", 50);
		 romanMap.put("C", 100);
		 romanMap.put("D", 500);
		 romanMap.put("M", 1000);
		 logger.debug("method valideRomanNumber End:");
		return romanMap;
	 }
	 
	 /**
		 * Convert single Roman numeral to a number
		 * @param romanNumeral is The number to be converted.
		 */
	 private int manageSingleNumber(String romanNumeral){
		 logger.debug("method getTheRomanNumberMap Enter:");
		 int result = -1;
		 HashMap<String, Integer> romanMap = getTheRomanNumberMap();
		 String numeral = String.valueOf(romanNumeral.charAt(0));
		 if(romanMap.get(String.valueOf(numeral)) != null){
			 result = romanMap.get(String.valueOf(numeral)).intValue();
		 }
		 logger.debug("method getTheRomanNumberMap Enter:");
		 return result;
	 }
}
