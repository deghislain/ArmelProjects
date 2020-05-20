package com.perso.proj.mapred.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.perso.proj.mapred.services.NameNodeService;

/**
 * Servlet implementation class MapRedController
 */
@Controller
public class MapRedController{
	private static final long serialVersionUID = 1L;
	protected final Log logger = LogFactory.getLog(getClass());
	private static final String UPLOAD_DIRECTORY = "/uploadedFiles";

//	@Inject
	private NameNodeService nService;

	public MapRedController(NameNodeService nns) {
		this.nService = nns;
	}

	@RequestMapping("/index")
	public String showMapRedResult(HttpServletRequest req, Model model) {
		List<FileItem> formItems = processUploadFile(req);
		String numThread = req.getParameter("numThread");
		if(null != formItems && !formItems.isEmpty()) {
			String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
			String result = nService.storeFile(formItems, uploadPath);
			model.addAttribute("result", result);
		}
		
		return "index";
	}

	private List<FileItem> processUploadFile(HttpServletRequest request) {
		List<FileItem> formItems = null;
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// parses the request's content to extract file data
			formItems = upload.parseRequest(request);
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		return formItems;
	}

}
