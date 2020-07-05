/**
 * 
 */
package com.perso.proj.msgservices;

import java.io.IOException;

import org.jdom2.JDOMException;

/**
 * @author deghislain
 *
 */
public interface IMessagesService {
	public String sendMsgService(String senderID, String receiverID, String msg, String filePath)throws JDOMException, IOException;
	public String[] receiveMsgService(String receiverID, Boolean purge, String filePath);
}
