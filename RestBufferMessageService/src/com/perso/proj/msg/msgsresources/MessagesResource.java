/**
 * 
 */
package com.perso.proj.msg.msgsresources;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.JDOMException;

import com.perso.proj.msg.msgservices.IMessagesService;


/**
 * @author deghislain
 *
 */
@Path("messages")
public class MessagesResource{
	protected final Logger logger = LogManager.getLogger(MessagesResource.class);
	
	IMessagesService msgServ;
	
	@Inject
	public MessagesResource(IMessagesService serv) {
		this.msgServ = serv;
	}
	
	@POST
	@Consumes("text/plain")
	public void sendMsg(@PathParam("senderID") String senderID, @PathParam("receiverID") String receiverID,
			@PathParam("msg") String msg, @PathParam("filePath") String filePath) throws JDOMException, IOException{
		logger.info("Entered sendMsg" );
			this.msgServ.sendMsgService(senderID, receiverID, msg, filePath);
		logger.info("Exiting sendMsg" );
	}
}
