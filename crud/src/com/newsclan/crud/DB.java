package com.newsclan.crud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DB {
	private static final String driver = Config.get("db.driver");
	private static final String connectString = Config.get("db.connectString");
	private static final String dbusername = Config.get("db.username");
	private static final String dbpassword = Config.get("db.password");
	public static boolean pool=false;
	public static Queue <Connection>queue=new ConcurrentLinkedDeque<Connection>();
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			if(pool&&!queue.isEmpty()){
				return queue.poll();
			}else{
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(connectString, dbusername,
						dbpassword);
			}
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		
	}
	public static void close(Object toClose){
		if(toClose==null) return;
		Class theClass=toClose.getClass();
		try {
			Method close=theClass.getMethod("close");
			if(close!=null){
				try {
					if(pool&&toClose instanceof Connection){
						 queue.add((Connection)toClose);
						 if(Config.debug)
							 	System.err.format("DB pool size:%d\n",queue.size());
					}else{
						close.invoke(toClose);
					}
				} catch (Throwable t) {
					// TODO Auto-generated catch block
					t.printStackTrace();
				}
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static boolean isChar(int type) {
		// TODO Auto-generated method stub
		return type==Types.CHAR||
				type==Types.VARCHAR||
				type==Types.NCHAR||
				type==Types.LONGVARCHAR||
				type==Types.LONGNVARCHAR;
	}
}
