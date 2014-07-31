package com.letitguide.metadatatool.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.letitguide.metadatatool.mongo.MongoConnection;
import com.mongodb.DB;

import flexjson.JSONSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("/hello")
public class HelloServlet {

	@GET
    public Response service() throws IOException {
		//MongoTest mongoTest = new MongoTest();
		DB db = MongoConnection.getDatabase();
		List asistentes = db.getCollection("asistentes").find().toArray();
		
		JSONSerializer serializer = new JSONSerializer()
			.exclude("_id")
			.include("*")
			.prettyPrint(true);
		
		return Response.status(200).entity(serializer.serialize(asistentes)).build();
    }
}
