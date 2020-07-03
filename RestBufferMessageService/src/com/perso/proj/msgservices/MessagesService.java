/**
 * 
 */
package com.perso.proj.msgservices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author deghislain
 *
 */
public class MessagesService implements IMessagesService{
	private static final String RESULTS_FILE_NAME = "/messagesDir/messages.xml";
@Override
public String sendMsgService(String senderID, String receiverID, String msg, String filePath) throws JDOMException, IOException {
	return this.storeMsg(senderID, receiverID, msg, filePath);
}

private String storeMsg(String senderID, String receiverID, String msg, String filePath) throws JDOMException, IOException {
	String result = "";
	String completePath = filePath + RESULTS_FILE_NAME;
	
	File messagesDir = new File(completePath);
	Element newMsg = new Element("Message");
	Element messages = null;
	if (!messagesDir.exists()) {// we create the first message
		messagesDir.mkdir();
		
		messages = new Element("Messages");
		newMsg.setAttribute("SenderID", senderID);
		newMsg.setAttribute("ReceiverID", receiverID);
		newMsg.setText(msg);
		messages.addContent(newMsg);
	}else {
		
		newMsg.setAttribute("SenderID", senderID);
		newMsg.setAttribute("ReceiverID", receiverID);
		newMsg.setText(msg);
		
		//we get the existing file then we append the new file to it
		File inputFile = new File(completePath);
		SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(inputFile);
		messages = document.getRootElement();
		messages.addContent(newMsg);
	}
	 XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
	 Document doc = new Document(messages);
     xmlOutputter.output(doc, new FileOutputStream(completePath));
	result = "Message Successfully Sent";
	return result;
}
@Override
public String[] receiveMsgService(String receiverID, Boolean purge, String filePath) {
	// TODO Auto-generated method stub
	return null;
}
}
