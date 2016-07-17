package com.newsclan.crud;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringEscapeUtils;


public class Tools {
	public static void log(String msg) {
		System.out.println(msg);
	}
	public static void importXLS(String path) {
		System.out.println(path);
		try {
			// 打开文件
			Workbook book = Workbook.getWorkbook(new File(path));
			// 取得第一个sheet
			Sheet sheet = book.getSheet(0);
			// 取得行数
			int rows = sheet.getRows();
			//System.out.println(rows);
			for (int i = 0; i < rows; i++) {
				Cell[] cell = sheet.getRow(i);
				//System.out.println(cell.length);
				for (int j = 0; j < cell.length; j++) {
					// getCell(列，行)
					System.out.print(cell[j].getContents());
					System.out.print(" ");
				}
				System.out.println("		");
			}
			// 关闭文件
			book.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static boolean login(String username,String password){
		boolean ret=false;
		Connection conn=DB.getConnection();
		String sql="select password from "+Config.sysPrefix+"user where name=? ";
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
		if(user_id==null) user_id=0;  //guest
		return user_id;
	}
	public static String getHash(String origin,String salt){
		return Md5(origin+salt)+salt;
	}
	public static String getRandomSalt(){
		return Md5(new Date().toString()).substring(0,8);
	}
	


	public static String Md5(String plainText) {
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
			if(!"".equals(fd_titles[i])){
				DAO.executeUpdate(dict, values);
			}
			sql.append(",`" + fd_names[i] + "` " + fd_types[i] + "");

		}
		sql.append(",PRIMARY KEY  (`id`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;");
		DAO.executeUpdate(sql.toString(), new String[] {});

	}

	private static String filteSQL(String sql) {
		// TODO Auto-generated method stub
		if(sql==null) return null;
		return sql.replace("'", "\\'");
	}

	public static String toSelect(String tbname, String value,String ... keys) {
		StringBuilder ret = new StringBuilder();
		StringBuilder options = new StringBuilder();
		String nameFD = DAO.getFirstCharFieldName(tbname);
		String input_name=filteSQL(keys[2]);
		String transview=DAO.transview(input_name);
		if(transview!=null) nameFD=transview;
		String sql = "select id," + (nameFD) + " from `" + filteSQL(tbname) + "`";
		boolean noDefault=true;
		if(keys.length>=2){
			System.out.println(keys[0]);
			Field[] fds = DAO.getFieldsOfTable(tbname);
			for(Field fd:fds){
				if(fd.name.equals(keys[0])){
					sql+=" where "+filteSQL(keys[0])+"="+filteSQL(keys[1]);
					break;
				}
				System.out.println(fd.name);
			}
		}
		List<List> data = DAO.queryList(sql, false);
		ret.append("<select");
		ret.append(" name='");
		if(null==input_name) input_name=tbname+"_id";
		ret.append(input_name);
		ret.append("' onChange='filteNext(this);' onLoad='filteNext(this);'>\n");

		for (List row : data) {
			options.append("<option value='");
			options.append(row.get(0));
			options.append("'");
			if (String.valueOf(row.get(0)).equals(value)){
				options.append(" selected");
				noDefault=false;
			}
			options.append(">");
			options.append(row.get(1));
			options.append("</option>\n");
		}
		if(noDefault){
			ret.append("<option value='-1'>请选择</option>\n");
		}
		ret.append(options.toString());
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
