package com.letitguide.metadatatool.mongo;

import java.net.UnknownHostException;

import com.googlecode.mjorm.MongoDao;
import com.googlecode.mjorm.MongoDaoImpl;
import com.googlecode.mjorm.ObjectIterator;
import com.googlecode.mjorm.annotations.AnnotationsDescriptorObjectMapper;
import com.googlecode.mjorm.query.DaoQuery;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;


public class MongoTest {

	public String testMongoDB() throws UnknownHostException {
		
		// connect to mongo
		Mongo mongo = new Mongo(new MongoURI("mongodb://localhost:27017/palma"));

		// create object mapper and add classes
		AnnotationsDescriptorObjectMapper objectMapper = new AnnotationsDescriptorObjectMapper();
		objectMapper.addClass(Asistente.class);

		// create MongoDao
		MongoDao dao = new MongoDaoImpl(mongo.getDB("palma"), objectMapper);
		DaoQuery query = dao.createQuery();
		query.setCollection("asistentes");
		query.eq("first_name", "Alfonso");

		ObjectIterator<Asistente> asistentes = query.findObjects(Asistente.class);
		StringBuffer sb = new StringBuffer();
		for (Asistente a: asistentes) {
			sb.append(a.getId()).append(" - ").append(a.getFirstName()).append("\n");
		}
		return sb.toString();
	}
}
