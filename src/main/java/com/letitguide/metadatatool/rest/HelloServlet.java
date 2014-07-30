package com.letitguide.metadatatool.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.letitguide.metadatatool.mongo.MongoTest;

import java.io.IOException;

@Path("/hello")
public class HelloServlet {

	@GET
    public Response service() throws IOException {
		MongoTest mongoTest = new MongoTest();
		return Response.status(200).entity(mongoTest.testMongoDB()).build();
    }
}
