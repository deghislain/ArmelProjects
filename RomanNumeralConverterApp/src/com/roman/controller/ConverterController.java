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
		String numeral = req.getParameter("numeral");// we get the number provided by the user in html form
		String from    = req.getParameter("from");
		
		String result = service.convert(numeral, from);// we call the service for the convertion
		
		model.addAttribute("result", result);
		return "index";
	}
}
