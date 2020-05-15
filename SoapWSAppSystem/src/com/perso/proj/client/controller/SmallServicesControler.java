package com.perso.proj.client.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.perso.proj.client.model.SmallServicesModel;

/**
 * Servlet implementation class SmallServicesControler
 */
@WebServlet(name = "SmallServicesControler", urlPatterns = { "/SmallServicesControler" })
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SmallServicesModel sm = new SmallServicesModel();
		String temp = request.getParameter("temp");
		String from = request.getParameter("from");
		HttpSession session = request.getSession(true);
		String result = null;
		String newTemp = "";
		String action = request.getParameter("action");
		// here we process the temperature converter
		if (null != action && action.equals("tempConv")) {
			if (null != temp && !temp.isEmpty() && null != from && !from.isEmpty()) {
				try {
					newTemp = sm.convertTemperature(Integer.parseInt(temp), from.charAt(0));
				} catch (Exception e) {
					result = "Invalid input, insert a valid number and the temperature type";
				}
				if (result == null) {
					session.setAttribute("newTemp", newTemp);
				}

			} else {
				result = "Invalid input, insert a valid number and the temperature type";
				session.setAttribute("newTemp", result);
			}
		}
		// here we process the numbers sorter
		if (null != action && action.equals("sorter")) {
			String strNum = request.getParameter("strNum");

			if (null != strNum && !strNum.isEmpty()) {
				String sortedStr = sm.sortString(strNum);
				session.setAttribute("sortedStr", sortedStr);
			} else {
				result = "Invalid String Of Numbers";
				session.setAttribute("sortedStr", result);
			}
		}
		// here we process the file upload
		 if (ServletFileUpload.isMultipartContent(request)) {
			 String filePath = processUploadFile(request);
			 result = sm.storeFile(filePath);
			 if(result.equals("KO")) {
				 result = "Unable to store the file";
			 }else {
				 result = "File Successfuly Stored";
			 }
			 session.setAttribute("result", result);
		 }
		response.sendRedirect("index.jsp");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
