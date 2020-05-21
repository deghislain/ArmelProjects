package com.perso.proj.mapred.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.perso.proj.mapred.services.NameNodeService;

/**
 * Servlet implementation class MapRedController
 */
@Controller
@RequestMapping("/paraMapRedCont")
public class MapRedController {
	private static final long serialVersionUID = 1L;
	protected final Log logger = LogFactory.getLog(getClass());
	private static final String UPLOAD_DIRECTORY = "/uploadedFiles";
	private String msg;
	private String status;

    @Inject
	private NameNodeService nService;

	public MapRedController(NameNodeService nns) {
		this.nService = nns;
		this.status = "Waiting For File";
	}

	@RequestMapping(value ="/upload", method = RequestMethod.POST)
	public String uploadFile(HttpServletRequest req, Model model) {
		//String action = req.getParameter("action");
		List<FileItem> formItems = processUploadFile(req, model);
		if (null != formItems && !formItems.isEmpty()) {
			String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
			this.status = nService.storeFile(formItems, uploadPath);
			model.addAttribute("status", this.status);
		}

		return "/index";
	}

	private List<FileItem> processUploadFile(HttpServletRequest request, Model model) {
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
			this.status = "File Upload Error";
		}
		
		model.addAttribute("status", this.status);
		return formItems;
	}
	@RequestMapping(value = "partition", method = RequestMethod.GET)
public String performMapReduce(HttpServletRequest req, Model model) {
	List<List<String>> parts = this.doDataPartition(req, model);
	//TODO create threads
	return "/index";
}
	private List<List<String>> doDataPartition(HttpServletRequest req, Model model) {
		String numThread = req.getParameter("numThread");
		List<List<String>> words = null;
		String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
		
		try {
			if (null != numThread && !numThread.isEmpty() && null != uploadPath && !uploadPath.isEmpty()) {
				int nt = Integer.parseInt(numThread);
				words = this.nService.partitionData(uploadPath, nt);
				if (null != words && !words.isEmpty()) {
					this.status = "Completed Map Reduce";
					model.addAttribute("status", this.status);
				}else {
					this.msg = "Map Reduce Error";
					model.addAttribute("message", this.msg);
				}
				
			}else {
				this.msg = "Invalid Input: Make Sure That The Number Of Thread Is Valid And That There Is A File For Partitioning";
				model.addAttribute("message", this.msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Invalid Number Of Threads";
		}
		return words;
	}
	
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public String displayResults(HttpServletRequest req, Model model) {
		String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
		HashMap<String, String> resultMap = this.nService.displayResults(uploadPath);
		if(resultMap != null && !resultMap.isEmpty()) {
			model.addAttribute("words", resultMap);
			this.status = "Displaying Results";
			model.addAttribute("status", this.status);
		}else {
			this.msg = "Nothing To Display, Make Sure You Have Uploaded A File And Perform A Map Reduce";
			model.addAttribute("message", this.msg);
		}
		return "/index";
	}

}
