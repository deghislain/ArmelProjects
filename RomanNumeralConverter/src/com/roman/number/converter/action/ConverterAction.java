package com.roman.number.converter.action;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.roman.number.converter.entity.Converter;
import com.roman.number.converter.management.RomanNumeralConverterImpl;

public class ConverterAction extends ActionSupport implements
		ModelDriven<Converter> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Converter converter = new Converter();
	private static Logger logger = Logger.getLogger(ConverterAction.class);
	private RomanNumeralConverterImpl romnImpl = new RomanNumeralConverterImpl();
	HttpSession session = ServletActionContext.getRequest().getSession();
	private String result = "";

	public String doConvert() {
		logger.debug("method doConvert Enter:");
		
		if (this.converter.getConverterType() != null && !this.converter.getConverterType().isEmpty()
				&& this.converter.getNumeral() != null && !this.converter.getNumeral().isEmpty()) {
			
			if (this.converter.getConverterType().equals("Roman")) {
				this.result = new Integer(romnImpl.fromRomanNumeral(this.converter.getNumeral().toUpperCase())).toString();
				if (result == null || result.equals("-1")) {
					converter.setErrorMessage("Invalid Number");
				}
				converter.setResult(this.result);

			} else if (this.converter.getConverterType().equals("Number")) {
				try{
					this.result = romnImpl.toRomanNumeral(new Integer(this.converter.getNumeral()).intValue());
				}catch(Exception e){
					converter.setErrorMessage("Invalid Number");
				}
				converter.setResult(this.result);
				
				if (result == null || result.equals("-1")) {
					converter.setErrorMessage("Invalid Number");
				}
			}

		}else {
				converter.setErrorMessage("Invalid Input Converter Type or Number Missing");
			}
		
			session.setAttribute("conv", converter);
		
		logger.debug("method doConvert End:");
		return "success";
	}

	public Converter getConverter() {
		return converter;
	}

	public Converter getModel() {
		return converter;
	}

}
