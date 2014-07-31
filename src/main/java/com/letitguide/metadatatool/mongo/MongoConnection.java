package com.letitguide.metadatatool.mongo;

import java.net.UnknownHostException;

import com.letitguide.metadatatool.Constants;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoConnection {
	public static DB getDatabase() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( Constants.MONGO_DB_URI , Constants.MONGO_DB_PORT );
		DB db = mongoClient.getDB(Constants.MONGO_DB_DATABASE);
		if (!Constants.MONGO_DB_USERNAME.isEmpty() && !Constants.MONGO_DB_PASSWORD.isEmpty()) {
			boolean auth = db.authenticate(Constants.MONGO_DB_USERNAME, Constants.MONGO_DB_PASSWORD.toCharArray());
		}
		return db;
	}
}
