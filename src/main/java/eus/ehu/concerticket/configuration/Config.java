package eus.ehu.concerticket.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private Properties prop;

	public static Config getInstance(){
		return instance;
	}

	public static Config instance = new Config();

	private Config(){
		loadConfig();
	}

	private void loadConfig(){
		prop = new Properties();
		try (InputStream input = new
				FileInputStream("config.properties")) {
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getDataBaseOpenMode() {
		return prop.getProperty("db.openmode");
	}

	public boolean isBusinessLogicLocal() {
		return prop.getProperty("isBusinessLogicLocal").equals("true");
	}

	public String getLocale() {
		return prop.getProperty("locale");
	}

	public boolean isDataAccessLocal() {
		return prop.getProperty("db.local").equals("true");
	}
}