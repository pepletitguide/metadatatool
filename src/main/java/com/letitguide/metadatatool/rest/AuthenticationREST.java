package com.letitguide.metadatatool.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import flexjson.JSONSerializer;

/* this is the class for authentication calls */

@Path("/auth")
public class AuthenticationREST {

	/* GET /auth/logged */
	/* returns wether a user is logged */
	@GET
	@Path("/logged")
	@Produces("application/json")
	public Response isLogged(@Context HttpServletRequest hsr) throws IOException {

		Map response = new HashMap();
		HttpSession session = hsr.getSession(true);
		response.put("logged", session.getAttribute("user") != null);
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}
	
	/* GET /auth/login */
	/* returns wether a user is logged */
	@POST
	@Path("/login")
	@Produces("application/json")
	public Response login(@Context HttpServletRequest hsr) throws IOException {
		Map response = new HashMap();
		HttpSession session = hsr.getSession(true);
		session.setAttribute("user", true);
		
		response.put("logged", true);
		
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}
	
	/* GET /auth/logout */
	/* returns wether a user is logged */
	@POST
	@Path("/logout")
	@Produces("application/json")
	public Response logout(@Context HttpServletRequest hsr) throws IOException {
		Map response = new HashMap();
		HttpSession session = hsr.getSession(true);
		session.setAttribute("user", null);
		
		response.put("logged", false);
		
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}

}
