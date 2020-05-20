/**
 * 
 */
package com.perso.proj.mapred.services;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;

/**
 * @author deghislain
 *
 */
@Service
public class NameNodeService {
	
	public String storeFile(List<FileItem> formItems, String filePath) {
		return this.store(formItems, filePath);
	}
	
	private String store(List<FileItem> formItems, String filePath) {
		String result = "";
		try {
			// creates the directory if it does not exist
	        File uploadedFileDir = new File(filePath);
	        if (!uploadedFileDir.exists()) {
	        	uploadedFileDir.mkdir();
	        }
		for(FileItem item: formItems) {
			if(!item.isFormField()) {
				String fileName = new File(item.getName()).getName();
				String completePath = filePath + File.separator + fileName;
				File file = new File(completePath);
				//store the file on disk
				item.write(file);
				result = "File Successfully Stored";
			}
		}
		}catch(Exception e) {
			result = "Error While Storing The File";
		}
		return result;
	}

}
