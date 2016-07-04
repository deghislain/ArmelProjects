package com.roman.number.converter.entity;

public class Converter {
	private String numeral;
	private String result;
	private String errorMessage;
	private String converterType;
	
	public String getConverterType() {
		return converterType;
	}
	public void setConverterType(String converterType) {
		this.converterType = converterType;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getNumeral() {
		return numeral;
	}
	public void setNumeral(String numeral) {
		this.numeral = numeral;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
