/**
 * 
 */
package com.perso.proj.msg.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author deghislain
 *
 */
@WebServlet("/MessagesController")
public class MessagesController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5090946576177964600L;
	
	public MessagesController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
