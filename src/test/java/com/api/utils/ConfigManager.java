package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties prop = new Properties();
	private static String path = "config/config.properties";
	private static String env;
	private ConfigManager()
	{
		
	}
	static
	{
		env = System.getProperty("env","qa");
		env = env.toLowerCase().trim();
		switch(env)
		{
		case "dev" -> env = "config/config.dev.properties";
	
		case "qa" -> env = "config/config.qa.properties";
		
		case "uat" -> env = "config/config.uat.properties";
		
		default -> env = "config/config.qa.properties";
		}
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if(input == null)
		{
			throw new RuntimeException("Cannot find the file"+path);
		}
		
//		File configFile = new File(System.getProperty("user.dir")+File.separator+"src"+
//		File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");
//      FileReader fileReader = null;
		try {
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) 
	{
		
		return prop.getProperty(key);
	}
	

}
