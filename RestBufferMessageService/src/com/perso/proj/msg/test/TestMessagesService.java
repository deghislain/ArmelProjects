/**
 * 
 */
package com.perso.proj.msg.test;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perso.proj.msg.msgservices.IMessagesService;
import com.perso.proj.msg.msgservices.MessagesService;

/**
 * @author deghislain
 *
 */
public class TestMessagesService {
	
	IMessagesService msgServ;
	
	String senderID;
	String receiverID;
	String msg;
	String filePath;
	
	@BeforeEach
	public void setUp() {
		senderID = "sender1";
		receiverID = "receiverID";
		msg = "hello world";
		filePath = "/messagesDir";
		this.msgServ = new MessagesService();
	}
	
	@Test
	public void testSendMsgService() {
		String result = null;
			try {
				result = this.msgServ.sendMsgService(senderID, receiverID, msg, filePath);
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertNotNull(result);
	}
}
