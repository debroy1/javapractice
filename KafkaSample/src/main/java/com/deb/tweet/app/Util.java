package com.deb.tweet.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
	private static final String APP_PROPERTIES_NAME = "app.properties";
	public static String BOOTSTRAP_SERVER;
	public static String TWITTER_TOPIC;
	public static String CONSUMER_KEY;
	public static String CONSUMER_SECRET;
	public static String TOKEN;
	public static String SECRET;

	public static void loadProperties() {
		System.out.println("Loading application properties...");
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + APP_PROPERTIES_NAME;
		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(appConfigPath));
			BOOTSTRAP_SERVER = appProps.getProperty("BOOTSTRAP_SERVER");
			TWITTER_TOPIC = appProps.getProperty("TWITTER_TOPIC");
			CONSUMER_KEY = appProps.getProperty("CONSUMER_KEY");
			CONSUMER_SECRET = appProps.getProperty("CONSUMER_SECRET");
			TOKEN = appProps.getProperty("TOKEN");
			SECRET = appProps.getProperty("SECRET");
			System.out.println("Successfully loaded application properties...");
		}
		catch (IOException e) {
			System.err.println("Error while loading application properties...");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Util.loadProperties();
		System.out.println(BOOTSTRAP_SERVER);
	}

}
