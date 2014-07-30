package com.letitguide.metadatatool.mongo;

import java.net.UnknownHostException;

import com.googlecode.mjorm.MongoDao;
import com.googlecode.mjorm.MongoDaoImpl;
import com.googlecode.mjorm.annotations.AnnotationsDescriptorObjectMapper;
import com.googlecode.mjorm.query.DaoQuery;
import com.letitguide.metadatatool.Constants;
import com.mongodb.Mongo;
import com.mongodb.MongoURI;

public class MongoDAO extends MongoDaoImpl {
	private Mongo database;
	private String collection;

	public MongoDAO(String collection, Class object) throws UnknownHostException {
		this.collection = collection;
		String uri = "mongodb://" + Constants.MONGO_DB_URI + ":" + Constants.MONGO_DB_PORT + "/" + Constants.MONGO_DB_DATABASE;
		database = new Mongo(new MongoURI(uri));
		AnnotationsDescriptorObjectMapper objectMapper = new AnnotationsDescriptorObjectMapper();
		objectMapper.addClass(object);
		this.setDb(database.getDB(Constants.MONGO_DB_DATABASE));
		this.setObjectMapper(objectMapper);
	}

	@Override
	public DaoQuery createQuery() {
		// TODO Auto-generated method stub
		DaoQuery query = super.createQuery();
		query.setCollection(collection);
		return query;
	}
	
	
}
