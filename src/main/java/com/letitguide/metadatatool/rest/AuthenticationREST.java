package com.letitguide.metadatatool.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.letitguide.metadatatool.mongo.Collections;
import com.letitguide.metadatatool.mongo.MongoConnection;
import com.letitguide.metadatatool.rest.ErrorFactory.Error;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import flexjson.JSONDeserializer;
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
		response.put("user", session.getAttribute("user"));
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}
	
	/* GET /auth/login */
	/* returns wether a user is logged */
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(String content, @Context HttpServletRequest hsr) throws IOException {
		Map form = new JSONDeserializer<HashMap>().deserialize(content);
		Map response = new HashMap();
		HttpSession session = hsr.getSession(true);

		
		if(!form.containsKey("username") || !form.containsKey("password")) {
			return ErrorFactory.getError(ErrorFactory.Error.MISSING_FIELDS);
		}
		
		DB db = MongoConnection.getDatabase();
		DBCollection users = db.getCollection(Collections.USERS);
		
		BasicDBObject query = new BasicDBObject("user", form.get("username").toString() );
		
		DBCursor cursor = users.find();
		
		if (!cursor.hasNext()) {
			return ErrorFactory.getError(ErrorFactory.Error.USER_NOT_FOUND);
		}
		Map user = cursor.next().toMap();
		
		if (!user.get("password").toString().equals(form.get("password").toString())) {
			return ErrorFactory.getError(ErrorFactory.Error.AUTHENTICATION_FAILED);
		}
		
		session.setAttribute("user", user.get("user").toString());
		response.put("user", user.get("user").toString());
		
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
		
		response.put("user", null);
		
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}

}
