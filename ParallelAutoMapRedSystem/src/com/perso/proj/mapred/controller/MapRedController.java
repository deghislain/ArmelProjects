package com.perso.proj.mapred.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.perso.proj.mapred.services.IMapReduceBufferService;
import com.perso.proj.mapred.services.INameNodeService;
import com.perso.proj.mapred.services.ITaskTrackerService;
import com.perso.proj.mapred.services.JobTrackerThread;
import com.perso.proj.mapred.services.MapReduceBufferService;
import com.perso.proj.mapred.services.NameNodeService;
import com.perso.proj.mapred.services.TaskTrackerService;

/**
 * Servlet implementation class MapRedController
 */
@Controller
@RequestMapping("/")
public class MapRedController{
	private static final long serialVersionUID = 1L;
	protected final Logger log = LogManager.getLogger(MapRedController.class);
	private static final String UPLOAD_DIRECTORY = "uploadedFiles";
	private String msg;
	private String currentStatus;

    @Inject
	private INameNodeService nService;
    
    
    public MapRedController() {
 
    }
	public MapRedController(NameNodeService nns) {
		this.nService = nns;
		this.currentStatus = "Waiting For File";
		this.msg = "";
	}
	
	@RequestMapping(value ="upload", method = RequestMethod.POST)
	public ModelAndView uploadFile(HttpServletRequest req, HttpServletResponse resp, ModelMap model) {
		log.info("Entered uploadFile");
		List<FileItem> formItems = processUploadFile(req, model);
		if (null != formItems && !formItems.isEmpty()) {
			String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
			this.currentStatus = nService.storeFile(formItems, uploadPath);
			model.addAttribute("status", this.currentStatus);
		}else {
			this.currentStatus = "File Upload Error";
			model.addAttribute("status", this.currentStatus);
		}
		ModelAndView mv = new ModelAndView("index", model);
		log.info("Exit uploadFile");
		return mv;
	}

	private List<FileItem> processUploadFile(HttpServletRequest request, ModelMap model) {
		log.info("Entered processUploadFile");
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
			this.currentStatus = "File Upload Error";
		}
		
		model.addAttribute("status", this.currentStatus);
		log.info("Exit processUploadFile");
		return formItems;
	}
	
	
	@RequestMapping(value = "partition", method = RequestMethod.POST)
public ModelAndView performMapReduce(HttpServletRequest req,HttpServletResponse resp, ModelMap model) {
		log.info("Entered performMapReduce");
	List<List<String>> parts = this.doDataPartition(req, model);
	String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
	
	
	if (null != parts && !parts.isEmpty()) {
		String numThread = req.getParameter("numThread");
		int nt = Integer.parseInt(numThread);
		IMapReduceBufferService mrbs = new MapReduceBufferService(parts);
		ITaskTrackerService tts = new TaskTrackerService();
		
		for (int i = 0; i < nt; i++) {
			JobTrackerThread jobi =   new JobTrackerThread(mrbs, tts, this.nService, uploadPath);
			jobi.setName("AC" + i);
			jobi.start();
			
		}
		//onStatusChange(req, resp, model, mrbs);
	}
	ModelAndView mv = new ModelAndView("index", model);
	log.info("Exit performMapReduce");
	return mv;
}
	private List<List<String>> doDataPartition(HttpServletRequest req, ModelMap model) {
		log.info("Entered doDataPartition");
		String numThread = req.getParameter("numThread");
		List<List<String>> words = null;
		String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
		
		try {
			if (null != numThread && !numThread.isEmpty() && null != uploadPath && !uploadPath.isEmpty()) {
				int nt = Integer.parseInt(numThread);
				words = this.nService.partitionData(uploadPath, nt);
				if (null != words && !words.isEmpty()) {
					this.currentStatus = "Completed Partition";
					model.addAttribute("status", this.currentStatus);
				}else {
					this.msg = "Error while processing the file. Make sure you have uploaded an appropriate file and try again";
					model.addAttribute("message", this.msg);
					this.currentStatus = "Map Reduce Error";
					model.addAttribute("status", this.currentStatus);
				}
				
			}else {
				this.msg = "Invalid Input: Make Sure That The Number Of Thread Is Valid And That There Is A File For Partitioning";
				model.addAttribute("message", this.msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Invalid Number Of Threads";
		}
		log.info("Exit doDataPartition");
		return words;
	}
	
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView displayResults(HttpServletRequest req, ModelMap model) {
		log.info("Entered displayResults");
		String uploadPath = req.getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
		HashMap<String, String> resultMap = this.nService.displayResults(uploadPath);
		if(resultMap != null && !resultMap.isEmpty()) {
			model.addAttribute("words", resultMap);
			this.currentStatus = "Displaying Results";
			model.addAttribute("status", this.currentStatus);
		}else {
			this.msg = "Nothing To Display, Make Sure You Have Uploaded A File And Perform A Map Reduce";
			model.addAttribute("message", this.msg);
		}
		ModelAndView mv = new ModelAndView("index", model);
		log.info("Exit displayResults");
		return mv;
	}

}
