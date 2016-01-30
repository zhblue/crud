package com.newsclan.crud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedDeque;




public class DB {
	private static final String driver = Config.get("db.driver");
	private static final String connectString = Config.get("db.connectString");
	private static final String dbusername = Config.get("db.username");
	private static final String dbpassword = Config.get("db.password");
	public static boolean pool=false;
	public static Queue <Connection>queue=new ConcurrentLinkedDeque<Connection>();
	private static Keeper keeper = new Keeper(queue);

	public static Connection getConnection(){
		Connection conn=null;
		try {
			if(pool&&!queue.isEmpty()){
				conn= queue.poll();
			}
			if(!testOK(conn)){
				if(pool&&conn!=null){
					queue.remove(conn);
					Keeper.dropConnection(conn);
				}
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
						// if(Config.debug)
						//	 	System.err.format("DB pool size:%d\n",queue.size());
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
	public static boolean testOK(Connection conn) {
		// TODO Auto-generated method stub
		if (conn == null)
			return false;
		Statement stmt = null;
		try {
			if (conn.isClosed())
				return false;
			if (!conn.isValid(10))
				return false;

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select 1");
			rs.next();
			rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
			}

		}

		return true;
	}
}
class Keeper extends TimerTask{

	public static boolean stop;
	private Queue<Connection> pool;
	private Timer timer=new Timer();

	public Keeper(Queue<Connection> queue) {
		// TODO Auto-generated constructor stub
		this.pool=queue;
		if(DB.pool){
			this.timer.schedule(this, 0, 30000);
		}
	}

	@Override
	public void run() {
		if(Config.debug)
			Tools.debug(this.getClass().getName()+":Keeper Working...");
		// TODO Auto-generated method stub
		int checked = 0;
		for (int i = 0; i < pool.size(); i++) {

			Connection conn = pool.poll();
			checked++;
			if (!DB.testOK(conn) || i > 10) {
				dropConnection(conn);
				i--;
			} else {
				pool.add(conn);

			}
		}
		if(Config.debug)
			Tools.debug(checked+" Connection checked OK!\n" );
		if(stop){
			this.cancel();
			timer.cancel();
			timer.purge();
		}
	}

	public static void dropConnection(Connection conn) {
		if(conn==null) return;
		try {
		
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Tools.debug("Connection releasing Error!"+e.getMessage());
		}
		if(Config.debug)
			Tools.debug("Connection released OK!\n" );
		// pool.remove(conn);
	}
}
