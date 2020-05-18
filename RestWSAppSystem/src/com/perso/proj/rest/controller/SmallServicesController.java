package com.perso.proj.rest.controller;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;


/**
 * Servlet implementation class SmallServicesController
 */
@WebServlet("/SmallServicesController")
public class SmallServicesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SmallServicesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ClientConfig conf = new ClientConfig();
		//create a client object for consuming the REST response
		Client client = ClientBuilder.newClient(conf);
		String temp = request.getParameter("temp");
		String from = request.getParameter("from");
		
		//the REST URL you want to connect to
		String strUrl = "http://localhost:8080/RestWSAppSystem/rest/smallService/" + temp +"/" +from;
		URI url = UriBuilder.fromUri(strUrl).build(); 
		WebTarget target = client.target(url);
		
		//tell the service you are connecting to that you accept text and invoke the GET
		Response resp = target.request(MediaType.TEXT_HTML).get();
		String result =resp.readEntity(String.class);
		if (result != null) {
			session.setAttribute("newTemp", result);
		}
		response.sendRedirect("index.jsp");
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
