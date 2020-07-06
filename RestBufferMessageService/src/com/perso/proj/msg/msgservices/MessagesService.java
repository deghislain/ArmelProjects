/**
 * 
 */
package com.perso.proj.msg.msgservices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@SessionScoped
public class MessagesService implements IMessagesService, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2416326753619681768L;
	private static final String RESULTS_FILE_NAME = "/messages.xml";
	protected final Logger logger = LogManager.getLogger(MessagesService.class);

	@Override
	public String sendMsgService(String senderID, String receiverID, String msg, String filePath)
			throws JDOMException, IOException {
		return this.storeMsg(senderID, receiverID, msg, filePath);
	}

	private String storeMsg(String senderID, String receiverID, String msg, String filePath)
			throws JDOMException, IOException {
		logger.info("Entered storeMsg");
		String result = null;

		File messagesDir = new File(filePath);
		String completePath = filePath + RESULTS_FILE_NAME;
		Element newMsg = new Element("Message");
		Element messages = null;
		Document document = null;
		if (!messagesDir.exists()) {// we create the first message
			messagesDir.mkdir();

			newMsg.setAttribute("SenderID", senderID);
			newMsg.setAttribute("ReceiverID", receiverID);
			newMsg.setText(msg);
			messages = new Element("Messages");
			messages.addContent(newMsg);
		} else {
			newMsg.setAttribute("SenderID", senderID);
			newMsg.setAttribute("ReceiverID", receiverID);
			newMsg.setText(msg);

			// we get the existing file then we append the new message to it
			File inputFile = new File(completePath);
			SAXBuilder saxBuilder = new SAXBuilder();
			document = saxBuilder.build(inputFile);
			messages = document.getRootElement();
			messages.addContent(newMsg);
		}
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(document, new FileOutputStream(completePath));
		result = "Message Successfully Sent";
		
		logger.info("message stored at: " + completePath);
		logger.info("Exiting storeMsg");
		return result;
	}

	@Override
	public String[] receiveMsgService(String receiverID, Boolean purge, String filePath) {
		// TODO Auto-generated method stub
		return null;
	}
}
