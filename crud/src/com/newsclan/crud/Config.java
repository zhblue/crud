package com.newsclan.crud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class Config implements ServletContextListener{

	private static String configFilePath="WEB-INF/classes/db.prop";
	private static Properties prop=new Properties();
	public static boolean debug;
	public static int pageSize;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext context = sce.getServletContext();
		String path = context.getRealPath("/");
		Config.configFilePath = path+"/WEB-INF/db.prop";
		try {
			Tools.log("loading..."+path);
			prop.load(new FileInputStream(configFilePath));
			DB.pool="true".equalsIgnoreCase(prop.getProperty("db.pool"));
			Config.debug="true".equalsIgnoreCase(prop.getProperty("system.debug"));
			pageSize=Integer.parseInt(prop.getProperty("page.size"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static String get(String key) {
		// TODO Auto-generated method stub
		return prop.getProperty(key);
	}

}
