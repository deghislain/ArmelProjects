/**
 * 
 */
package com.roman.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roman.services.RomanCalculatorService;


/**
 * @author deghislain
 *
 */
@Controller
public class ConverterController{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private RomanCalculatorService service;
	
	
	
	@Inject
	public ConverterController(RomanCalculatorService rcs) {
		this.service =rcs;
	}
	
	@RequestMapping("/index")
	public String showCalculusResult(HttpServletRequest req, Model model) {
		logger.info("Enter showCalculusResult");
		String numeral = req.getParameter("numeral");// we get the number provided by the user in html form
		String from    = req.getParameter("from");
		String result = "";
		if(null != from && !from.isEmpty() && from.equals("Number-to-Roman") && null != numeral && !numeral.isEmpty()) {
			try {
				 Integer.parseInt(numeral);
			}catch(Exception e) { 
				e.printStackTrace();
				result = numeral + " Is Invalid Number ";
				model.addAttribute("result", result);
				return "index";// do not call the service if the number inserted is not valid
			}
		}
		if(null != numeral && !numeral.isEmpty()) {
		// we call the service for the convertion
			result = service.convert(numeral, from);
		}else {
			result = "Invalid Number ";
		}
		
		model.addAttribute("result", result);
		logger.info("Exit showCalculusResult");
		return "index";
	}
}
