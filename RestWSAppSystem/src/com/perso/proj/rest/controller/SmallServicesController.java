package com.perso.proj.rest.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String temp = request.getParameter("temp");
		String from = request.getParameter("from");
		String action = request.getParameter("action");
		String strUrl = "http://localhost:8080/RestWSAppSystem/rest/smallService/";

		if (null != action && action.equals("tempConv") && null != temp && !temp.isEmpty() && null != from
				&& !from.isEmpty()) {
			strUrl = strUrl + temp + "/" + from;
			String result = serviceCall(strUrl);
			session.setAttribute("newTemp", result);
		} else {
			session.setAttribute("newTemp", "Invalid Input");
		}

		String strNum = request.getParameter("strNum");

		if (null != action && action.equals("sorter") && null != strNum && !strNum.isEmpty()) {
			strUrl = strUrl + strNum;
			String result = serviceCall(strUrl);
			session.setAttribute("sortedStr", result);
		} else {
			session.setAttribute("sortedStr", "Invalid Input");
		}
		
		// here we process the file upload
				 if (ServletFileUpload.isMultipartContent(request)) {
					 String filePath = processUploadFile(request);
					 strUrl = strUrl + filePath ;
					 String result = serviceCall(strUrl);
					 if(result.equals("KO")) {
						 result = "Unable to store the file";
					 }else {
						 result = "File Successfuly Stored";
					 }
					 session.setAttribute("result", result);
				 }

		response.sendRedirect("index.jsp");
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String serviceCall(String strUrl) {
		ClientConfig conf = new ClientConfig();
		// create a client object for consuming the REST response
		Client client = ClientBuilder.newClient(conf);
		// the REST URL you want to connect to
		URI url = UriBuilder.fromUri(strUrl).build();
		WebTarget target = client.target(url);

		// tell the service you are connecting to that you accept text and invoke the
		// GET
		Response resp = target.request(MediaType.TEXT_HTML).get();
		String result = resp.readEntity(String.class);

		return result;
	}
	
	private String processUploadFile(HttpServletRequest request) {
		//String filePath = null;
		String absolPath = null;
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// parses the request's content to extract file data
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						//String filePath = uploadPath + File.separator + fileName;
						File file = new File(fileName);
						absolPath = file.getAbsolutePath();
					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		return absolPath;
	}

}
