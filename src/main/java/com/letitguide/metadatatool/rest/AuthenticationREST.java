package com.letitguide.metadatatool.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import flexjson.JSONSerializer;

/* this is the class for authentication calls */

@Path("/auth")
public class AuthenticationREST {
	@Resource
	private WebServiceContext context;

	/* GET /auth/logged */
	/* returns wether a user is logged */
	@GET
	@Path("/logged")
	@Produces("application/json")
	public Response isLogged() throws IOException {

		MessageContext mc = context.getMessageContext();
		HttpSession session = ((javax.servlet.http.HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();
		Map response = new HashMap();
		response.put("logged", session.getAttribute("user") != null);
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}
	
	/* GET /auth/login */
	/* returns wether a user is logged */
	@GET
	@Path("/login")
	@Produces("application/json")
	public Response login() throws IOException {
		Map response = new HashMap();
		response.put("logged", true);
		
		MessageContext mc = context.getMessageContext();
		HttpSession session = ((javax.servlet.http.HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();
		
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}

}
