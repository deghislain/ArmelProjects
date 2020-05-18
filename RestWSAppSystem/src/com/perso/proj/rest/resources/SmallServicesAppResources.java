/**
 * 
 */
package com.perso.proj.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.perso.proj.rest.services.SmallServices;

/**
 * @author deghislain
 *
 */
@Path("/smallService")
public class SmallServicesAppResources {
	
	SmallServices service = new SmallServices();
	
	
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{temp}/{from}")
	public String getConvTemp(@PathParam("temp") int temp, @PathParam("from") String from) {
		String result = this.service.convertTemperature(temp, from.charAt(0));
		
		return result;
	}
	

}
