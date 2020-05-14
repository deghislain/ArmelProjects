package com.perso.proj.client.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.perso.proj.client.model.SmallServicesModel;

/**
 * Servlet implementation class SmallServicesControler
 */
@WebServlet(name = "SmallServicesControler", urlPatterns = {"/SmallServicesControler"})
public class SmallServicesControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SmallServicesControler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SmallServicesModel  sm = new SmallServicesModel();
		String temp = request.getParameter("temp");
		String from = request.getParameter("from");
		if(null != temp && null != from) {
			String newTemp = sm.convertTemperature(Integer.parseInt(temp), from.charAt(0));
			HttpSession  session = request.getSession(true);
			session.setAttribute("newTemp", newTemp);
		}
		response.sendRedirect("index.jsp");
		//request.getRequestDispatcher("index.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
