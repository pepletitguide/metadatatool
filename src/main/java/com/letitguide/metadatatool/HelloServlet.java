package com.letitguide.metadatatool;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import com.letitguide.metadatatool.mongo.MongoTest;

import java.io.IOException;

@WebServlet(value="/hello", name="helloServlet")
public class HelloServlet extends GenericServlet {
	private static final long serialVersionUID = 4476957654534505542L;

	@Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		MongoTest mongoTest = new MongoTest();
		res.getWriter().println(mongoTest.testMongoDB());
    }
}
