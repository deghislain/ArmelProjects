package com.roman.number.converter.junit.test;


import junit.framework.TestCase;

import org.junit.Test;

import com.roman.number.converter.management.RomanNumeralConverterImpl;

public class RomanNumeralConverterUnitTest extends TestCase {
	protected int number;
	protected String romanNumber;
	protected String romanNumeral;
	
	protected void setUp(){
		number=5;
		romanNumeral = "VII";
		romanNumber = "";
	   }
	
	@Test
	public void testFromRomanNumeral(){
		RomanNumeralConverterImpl romNumConverter = new RomanNumeralConverterImpl();
		int number = romNumConverter.fromRomanNumeral(romanNumeral);
		assertEquals(7, number);
	}
	
	@Test
	public void testToRomanNumeral(){
	RomanNumeralConverterImpl romNumConverter = new RomanNumeralConverterImpl();
		romanNumber = romNumConverter.toRomanNumeral(number);
		assertEquals("V", romanNumber);
	}
	
	
}
