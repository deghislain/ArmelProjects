/**
 * 
 */
package com.perso.proj.msgsresources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * @author deghislain
 *
 */
@Path("messages")
public class MessagesResource {
	@POST
public Response sendMsg(@PathParam("msg") String msg) {
		
	return Response.ok().build();
}
}
