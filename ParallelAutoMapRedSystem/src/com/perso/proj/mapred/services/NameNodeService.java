/**
 * 
 */
package com.perso.proj.mapred.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
public class NameNodeService implements INameNodeService{
	private static final String RESULTS_FILE_NAME = "/results.xml";
	
	//This method store the uploaded file on disk
	public String storeFile(List<FileItem> formItems, String filePath) {
		return this.store(formItems, filePath);
	}
	
	//upload file implementation
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
		List<String> words = getWords(lines);
		List<List<String>> parts = null;
		if(words != null && !words.isEmpty()) {
			parts = this.splitData(words, numThread);
		}
		
		return parts;
	}

	private List<String> loadDocument(String filePath) {
		List<String> lines = new ArrayList<String>();
		String[] files = new File(filePath).list();
		if(null != files) {
			for(String f :files) {
				if(f.endsWith(".txt")) {
					filePath = filePath + "/" +f;
					break;
				}
			}
		}
		//Path path = Paths.get(filePath);
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(filePath);
			reader = new BufferedReader(fr);
			String line = "";
			while(line != null) {
				line = reader.readLine();
				if(null != line && !line.isEmpty()) {
					lines.add(line);
				}
			}
			//lines = Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fr.close();
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return lines;
	}
	
	private List<String> getWords(List<String> lines){
		List<String>words = new ArrayList<String>();
			for(String line : lines) {
				String[] ws = line.split(" ");
				words.addAll(Arrays.asList(ws));
			}
		return words;
	}

	//This method split the list of words into N list of words
	private List<List<String>> splitData(List<String> allLines, int n) {
		 int rest = allLines.size()%n;//we might have some extra lines when spliting the document in n parts
         int numElt = allLines.size() / n;
         List<List<String>> parts = new ArrayList<List<String>>();
         int fromIndex = 0;
         int toIndex = numElt;
       //here we copy the first n-1 parts excluding the last part and eventual reste
         while (fromIndex < allLines.size()- (numElt+rest)) {
        	 //here we generate parts by splitting the list in small subsets
        	 List<String> part = allLines.subList(fromIndex, toIndex);
        	 parts.add(part);
        	 fromIndex += numElt;
        	 toIndex += numElt;
         }
       //here we copy the last part + eventual extra
         List<String> part = allLines.subList(fromIndex, toIndex + rest);
         parts.add(part);

		return parts;
	}
	
	//This method stores the combined results of map reduce operations into results.xml file;
	public String storeResults(HashMap<String, Integer> results, String resultStoragePath) {
		return this.store(results, resultStoragePath);
	}
	
	private String store(HashMap<String, Integer> results, String resultStoragePath) {
		String result = "";
		try {
			Element words = new Element("words");
			for(String r : results.keySet()) {
				Element word = new Element("word");
				word.setAttribute("Frequency", String.valueOf(results.get(r)) );
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
			File inputFile = new File(filePath);
			SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(inputFile);
			Element root = document.getRootElement();
			List<Element> words = root.getChildren();
			for(Element word : words) {
				resultMap.put(word.getText(), word.getAttributeValue("Frequency"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultMap;
	}

}
