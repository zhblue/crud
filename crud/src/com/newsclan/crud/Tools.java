package com.newsclan.crud;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;


public class Tools {
	public static void log(String msg) {
		System.out.println(msg);
	}
	public static boolean login(String username,String password){
		boolean ret=false;
		Connection conn=DB.getConnection();
		String sql="select password from user where name=? ";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String dbhash=rs.getString("password");
				String salt=dbhash.substring(32);
				String thishash=getHash(password,salt);
				ret=thishash.equals(dbhash);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.close(rs);
		DB.close(pstmt);
		DB.close(conn);
		return ret;
	}
	public static int getUserId(HttpSession session){
		Integer user_id = (Integer) session.getAttribute("user_id");
		if(user_id==null) user_id=0;
		return user_id;
	}
	public static String getHash(String origin,String salt){
		return Md5(origin+salt)+salt;
	}
	public static String getRandomSalt(){
		return Md5(new Date().toString()).substring(0,8);
	}
	private static String Md5(String plainText) {
		String ret=plainText;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			ret=buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return ret;
	}
	public static int getRequestInt(HttpServletRequest req, String paraName) {
		int ret = 0;
		try {
			ret = Integer.parseInt(req.getParameter(paraName));
		} catch (Exception e) {
		}
		return ret;

	}

	public static void addTable(String tb_name, String tb_title,
			String[] fd_names, String[] fd_types, String[] fd_titles) {
		StringBuffer sql = new StringBuffer("create table " + tb_name + "(");
		sql.append("id int(10) unsigned NOT NULL auto_increment");

		String dict = "INSERT INTO `datadic` (`field`,`name`) VALUES (?,?)";

		DAO.executeUpdate(dict, new String[] { tb_name, tb_title });

		for (int i = 0; i < fd_names.length; i++) {
			fd_names[i] = filteSQL(fd_names[i]);
			fd_titles[i] = filteSQL(fd_titles[i]);
			String[] values = { fd_names[i], fd_titles[i] };
			DAO.executeUpdate(dict, values);

			sql.append(",`" + fd_names[i] + "` " + fd_types[i] + "");

		}
		sql.append(",PRIMARY KEY  (`id`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
		DAO.executeUpdate(sql.toString(), new String[] {});

	}

	private static String filteSQL(String sql) {
		// TODO Auto-generated method stub
		return sql.replace("'", "\\'");
	}

	public static String toSelect(String tbname, String value) {
		StringBuffer ret = new StringBuffer();
		String nameFD = DAO.getFirstCharFieldName(tbname);
		String sql = "select id," + nameFD + " from `" + tbname + "`";
		List<List> data = DAO.queryList(sql, false);
		ret.append("<select");
		ret.append(" name='");
		ret.append(tbname);
		ret.append("_id'>");

		for (List row : data) {
			ret.append("<option value='");
			ret.append(row.get(0));
			ret.append("'");
			if (String.valueOf(row.get(0)).equals(value))
				ret.append(" selected");
			ret.append(">");
			ret.append(row.get(1));
			ret.append("</option>");
		}
		ret.append("</select>");
		return ret.toString();

	}

	public static String toHTML(String text) {
		return StringEscapeUtils.escapeHtml4(text);
	}

	public static String toTable(List<List> list, String... css) {
		StringBuffer ret = new StringBuffer();
		ret.append("<table id='dataForm' class='");
		for (String css1 : css) {
			ret.append(css1);
		}
		ret.append("'>");
		boolean title = true;

		for (List row : list) {
			boolean dbid = true;
			if (title) {
				ret.append("<thead>");
			}
			ret.append("<tr id='" + String.valueOf(row.get(0)) + "'>");
			for (Object object : row) {
				if (title) {
					ret.append("<th>");
				} else {
					ret.append("<td");
					if (dbid) {
						ret.append(" class='dbid'");
						dbid = false;
					} else {
						ret.append(" class='dbdata'");
					}
					ret.append(">");
				}
				ret.append(String.valueOf(object));
				if (title) {
					ret.append("</th>");
				} else {
					ret.append("</td>");
				}

			}

			ret.append("</tr>");
			if (title) {
				ret.append("</thead>");
				ret.append("<tbody>");
			}
			title = false;
		}
		ret.append("</tbody></table>");
		return ret.toString();
	}

	public static String shortString(String s, int len) {
		if (s != null) {
			if (s.length() > len && len > 0) {
				s = s.substring(0, len);
				if (len > 6) {
					s = s.substring(0, len - 3) + "...";
				}
			}
		} else {
			s = "";
		}
		return s;
	}
	public static String tomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, +1);
		//cal.set(cal.DATE, cal.getActualMaximum(cal.DATE));
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
	}
	
	public static String lastMonthFirstDay() {

		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		cal.set(cal.DATE, 1);
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
	}

	public static String lastMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONTH, -1);
		cal.set(cal.DATE, cal.getActualMaximum(cal.DATE));
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
	}
	
	public static String today() {
		Calendar cal = Calendar.getInstance();
		//cal.set(cal.DATE, cal.getActualMaximum(cal.DATE));
		return new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
	}
	public static void debug(String msg) {
		// TODO Auto-generated method stub
		if (Config.debug)
			System.out.println(msg);
	}

}
