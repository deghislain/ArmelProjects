/**
 * 
 */
package com.perso.proj.msgsresources;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.JDOMException;

import com.perso.proj.msgservices.IMessagesService;


/**
 * @author deghislain
 *
 */
@Path("messages")
public class MessagesResource{
	protected final Logger logger = LogManager.getLogger(MessagesResource.class);
	@Inject
	IMessagesService msgServ;
	public MessagesResource(IMessagesService serv) {
		this.msgServ = serv;
	}
	
	@POST
	@Consumes("text/plain")
	public void sendMsg(@PathParam("senderID") String senderID, @PathParam("receiverID") String receiverID,
			@PathParam("msg") String msg, @PathParam("filePath") String filePath){
		logger.info("Entered sendMsg" );
		try {
			this.msgServ.sendMsgService(senderID, receiverID, msg, filePath);
		} catch (JDOMException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		logger.info("Exiting sendMsg" );
	}
}
