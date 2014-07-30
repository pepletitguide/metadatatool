package com.letitguide.metadatatool.mongo;

import java.net.UnknownHostException;

import com.googlecode.mjorm.ObjectIterator;
import com.googlecode.mjorm.query.DaoQuery;
import com.letitguide.metadatatool.mongo.objects.Asistente;


public class MongoTest {

	public String testMongoDB() throws UnknownHostException {
		
		// connect to mongo
		MongoDAO dao = new MongoDAO("asistentes", Asistente.class);
		
		DaoQuery query = dao.createQuery();
		//query.setCollection("asistentes");
		//query.eq("first_name", "Alfonso");

		ObjectIterator<Asistente> asistentes = query.findObjects(Asistente.class);
		StringBuffer sb = new StringBuffer();
		for (Asistente a: asistentes) {
			sb.append(a.getId()).append(" - ").append(a.getFirstName()).append("\n");
		}
		return sb.toString();
	}
}
