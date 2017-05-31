package com.newsclan.crud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class Config implements ServletContextListener{

	private static String configFilePath="WEB-INF/classes/db.prop";
	private static Properties prop=new Properties();
	public static boolean debug;
	public static int pageSize;
	public static boolean loginCheck;
	public static String sysPrefix="";
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		Keeper.stop=true;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext context = sce.getServletContext();
		String path = context.getRealPath("/");
		Config.configFilePath = path+"/WEB-INF/db.prop";
		try {
			Tools.log("loading..."+path);
			prop.load(new InputStreamReader(new FileInputStream(configFilePath),"UTF-8"));
			DB.pool="true".equalsIgnoreCase(prop.getProperty("db.pool"));
			Config.debug="true".equalsIgnoreCase(prop.getProperty("system.debug"));
			Config.loginCheck="true".equalsIgnoreCase(prop.getProperty("login.check"));
			Config.sysPrefix=Config.get("db.sys.prefix");
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
