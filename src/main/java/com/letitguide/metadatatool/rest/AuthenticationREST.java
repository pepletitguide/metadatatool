package com.letitguide.metadatatool.rest;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.letitguide.metadatatool.Base64Converter;
import com.letitguide.metadatatool.Constants;
import com.letitguide.metadatatool.SimpleMail;
import com.letitguide.metadatatool.mongo.Collections;
import com.letitguide.metadatatool.mongo.MongoConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
		
		DBCursor cursor = users.find(query);
		if (cursor.size() < 1) {
			return ErrorFactory.getError(ErrorFactory.Error.AUTHENTICATION_FAILED);
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
	
	/* GET /auth/forgotPassword */
	/* sends an e-mail to reset a user's password */
	@POST
	@Path("/forgotPassword")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(String content) throws IOException {
		Map response = new HashMap();
		Map form = new JSONDeserializer<HashMap>().deserialize(content);
		
		DB db = MongoConnection.getDatabase();
		DBCollection users = db.getCollection(Collections.USERS);
		
		BasicDBObject query = new BasicDBObject("user", form.get("email").toString() );
		
		DBCursor cursor = users.find(query);
		if (cursor.size() < 1) {
			return ErrorFactory.getError(ErrorFactory.Error.USER_NOT_FOUND);
		}
		
		DBObject user = cursor.next();
		Map userMap = user.toMap();
		String token = Base64Converter.UUIDToBase64(UUID.randomUUID());
		users.update( user, new BasicDBObject( "$set", new BasicDBObject( "token", token ) ) );
		
		String tokenUrl = Constants.WEBSITE_URL+"resetPassword/"+token;
		String mailBody = "<a href=\""+tokenUrl+"\">"+tokenUrl+"</a>";
		
		SimpleMail mail = new SimpleMail("support@letitguide.com", userMap.get("user").toString() , "Password Reset mail", mailBody);
		try {
			mail.send();
		} catch (MessagingException e) {
			return ErrorFactory.getError(ErrorFactory.Error.MAIL_ERROR);
		}
		
		return Response.ok().build();
	}
	
	/* GET /auth/resetPassword?token=[token] 
	 * checks if the resetPassword token is valid and returns the user */
	@GET
	@Path("/resetPassword")
	@Produces("application/json")
	public Response getResetPassword(@QueryParam("token") String token) throws UnknownHostException {
		Map response = new HashMap();
		
		if (token == null || token.isEmpty()) {
			return ErrorFactory.getError(ErrorFactory.Error.RESET_TOKEN_ERROR);
		}
		
		DB db = MongoConnection.getDatabase();
		DBCollection users = db.getCollection(Collections.USERS);
		
		BasicDBObject query = new BasicDBObject("token", token);
		DBCursor cursor = users.find(query);
		if (cursor.size() < 1) {
			return ErrorFactory.getError(ErrorFactory.Error.RESET_TOKEN_ERROR);
		}
		Map user = cursor.next().toMap();
		response.put("user", user.get("user"));
		
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.status(200).entity(serializer.serialize(response))
				.build();
	}
	
	/* POST /auth/resetPassword 
	 * checks if the resetPassword token is valid and returns the user */
	@POST
	@Path("/resetPassword")
	@Consumes("application/json")
	@Produces("application/json")
	public Response postResetPassword(String content) throws UnknownHostException {
		Map form = new JSONDeserializer<HashMap>().deserialize(content);
		
		if (!form.containsKey("password") || !form.containsKey("token")) {
			return ErrorFactory.getError(ErrorFactory.Error.MISSING_FIELDS);
		}
		
		DB db = MongoConnection.getDatabase();
		DBCollection users = db.getCollection(Collections.USERS);
		
		BasicDBObject query = new BasicDBObject("token", form.get("token").toString() );
		
		DBCursor cursor = users.find(query);
		if (cursor.size() < 1) {
			return ErrorFactory.getError(ErrorFactory.Error.USER_NOT_FOUND);
		}
		
		DBObject user = cursor.next();
		Map userMap = user.toMap();
		
		query = new BasicDBObject( "$set", new BasicDBObject( "password", form.get("password").toString()))
		.append("$unset", new BasicDBObject( "token" , "" ));
		
		users.update( user, query);
		
		
		JSONSerializer serializer = new JSONSerializer().include("*")
				.prettyPrint(true);

		return Response.ok().build();
	}

}
