package com.letitguide.metadatatool;

public class Constants {
	
	//MongoDB credentials
	public static final String MONGO_DB_URI = "localhost";
	public static final int MONGO_DB_PORT = 27017;
	public static final String MONGO_DB_DATABASE = "metadatatool";
	public static final String MONGO_DB_USERNAME = "";
	public static final String MONGO_DB_PASSWORD = "";
	
	//Mail credentials
	public static final String MAIL_HOSTNAME = "mail.letitguide.com";
	public static final String MAIL_USERNAME = "support@letitguide.com";
	public static final String MAIL_PASSWORD = "supF-tr39Adv";
	public static final String MAIL_SMTP_PORT = "25";
	
	//misc
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss"; //all dates must be UTC/GMT
	public static final String WEBSITE_URL = "http://localhost:8080/metadatatool/public/";
}
