/**
 * 
 */
package com.perso.proj.mapred.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Service;

/**
 * @author deghislain
 *
 */
@Service
public class NameNodeService {
	private static final String RESULTS_FILE_NAME = "/results.xml";

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
			for (FileItem item : formItems) {
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					String completePath = filePath + File.separator + fileName;
					File file = new File(completePath);
					// store the file on disk
					item.write(file);
					result = "File Uploaded";
				}
			}
		} catch (Exception e) {
			result = "File Upload Error";
		}
		return result;
	}

	public List<List<String>> partitionData(String filePath, int numThread) {
		List<String> lines = this.loadDocument(filePath);
		List<List<String>> words = null;
		words = this.splitData(lines, numThread);
		return words;
	}

	private List<String> loadDocument(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			try {
				while(br.readLine() != null) {
					String line = br.readLine();
					if(null != line && !line.isEmpty()) {
						lines.add(line);
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				lines = null;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lines = null;
		}
		return lines;
	}

	//This method split the list of words into N list of words
	private List<List<String>> splitData(List<String> allLines, int n) {
		 int reste = allLines.size()%n;//we might have some extra lines when spliting the document in n parts
         int numElt = allLines.size() / n;
         List<List<String>> parts = new ArrayList<List<String>>();
         int start = 0;
       //here we copy the first n-1 parts excluding the last part and eventual reste
         while (start < allLines.size()- (numElt+reste)) {
        	 //here we generate parts by splitting the list in small subsets
        	 List<String> part = allLines.subList(start, numElt);
        	 parts.add(part);
        	 start += numElt;
         }
       //here we copy the last part + eventual extra
         List<String> part = allLines.subList(start, numElt+start);
         parts.add(part);

		return parts;
	}
	
	//This method stores the combined results of map reduce operations into results.xml file;
	public String storeResults(HashMap<String, String> results, String resultStoragePath) {
		return this.store(results, resultStoragePath);
	}
	
	private String store(HashMap<String, String> results, String resultStoragePath) {
		String result = "";
		try {
			Element words = new Element("words");
			//Document doc = new Document(words);
			
			for(String r : results.keySet()) {
				Element word = new Element("word");
				word.setAttribute("NumberOccurencies", results.get(r));
				word.setText(r);
				words.addContent(word);
			}
			 XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
			 Document doc = new Document(words);
		     xmlOutputter.output(doc, new FileOutputStream(resultStoragePath + RESULTS_FILE_NAME));
			result = "Results Successfully Stored";
		}catch(Exception e) {
			e.printStackTrace();
			result = "Error While Storing Results";
		}
		return result;
	}
	
	//This method read the results of combined map reduce from results.xml file for displays
	public HashMap<String, String> displayResults(String filePath){
		return this.getResults(filePath);
	}
	
	private HashMap<String, String> getResults(String filePath){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		filePath = filePath + RESULTS_FILE_NAME;
		try {
			//URL url = new URL(filePath);
			File inputFile = new File(filePath);
			SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(inputFile);
			Element root = document.getRootElement();
			List<Element> words = root.getChildren();
			for(Element word : words) {
				resultMap.put(word.getText(), word.getAttributeValue("Frequency"));
			}
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}

}
