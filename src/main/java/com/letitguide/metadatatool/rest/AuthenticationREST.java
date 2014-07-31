package com.letitguide.metadatatool.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response isLogged() throws IOException {
		Map response = new HashMap();
		response.put("logged", true);
		
		JSONSerializer serializer = new JSONSerializer().include("*").prettyPrint(true);
		
		return Response.status(200)
				.entity(serializer.serialize(response))
				.build();
    }
	
}
