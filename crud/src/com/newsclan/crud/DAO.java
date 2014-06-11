package com.newsclan.crud;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.*;

public class DAO {
	public static int insert(String tbname, Map<String, String> values) {
		Field[] fds = getFieldsOfTable(tbname);
		StringBuffer sql = new StringBuffer("insert into " + tbname + "(");
		StringBuffer value = new StringBuffer(" values(");
		
		List<String> data = new LinkedList();
		for (Field field : fds) {
			if ("id".equals(field.name))
				continue;
			String d=values.get(field.name);
			if(JspGenerator.isFieldNumber(field)){
				if("".equals(d)){
					d="0";
				}
			}
			data.add(d);
			sql.append(field.name);
			sql.append(",");
			value.append("?,");
		}
		sql.deleteCharAt(sql.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(") ");
		sql.append(value);
		sql.append(") ");
		return update(sql.toString(), data.toArray());

	}

	public static Field[] getFieldsOfTable(String tbname) {
		List<Field> ret = new LinkedList<Field>();
		Connection conn = DB.getConnection();
		ResultSet rs = null;
		try {
			rs = conn.getMetaData().getColumns(null, null, tbname, null);
			while (rs.next()) {
				Field fd = new Field();
				fd.name = rs.getString("COLUMN_NAME");
				fd.type = rs.getInt("DATA_TYPE");
				fd.label = translate(fd.name);
				fd.table = rs.getString("TABLE_NAME");
				ret.add(fd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.close(rs);
		DB.close(conn);
		return ret.toArray(new Field[0]);
	}

	public static List<List> getForm(String tbname, boolean edit) {
		List<List> ret = new LinkedList<List>();
		List title = new LinkedList();
		title.add("Ãû³Æ");
		title.add("Öµ");

		ret.add(title);

		Field[] fds = getFieldsOfTable(tbname);
		for (Field field : fds) {
			if (!edit && "id".equals(field.name))
				continue;
			List row = new LinkedList();
			row.add(field.label);
			row.add(getInputForm(field));
			ret.add(row);
		}
		return ret;

	}

	private static String getInputForm(Field field) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		ret.append("<input name='");
		ret.append(field.name);
		ret.append("' ");
		ret.append("type='");
		ret.append(getFormType(field.type));
		ret.append("'>");
		return ret.toString();
	}

	private static String getFormType(int type) {
		// TODO Auto-generated method stub
		return "text";
	}

	public static List<String> getTables() {
		List<String> ret = new LinkedList<String>();
		Connection conn = DB.getConnection();
		try {
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet tbs = dbmd.getTables(null, null, null, null);
			while (tbs.next()) {
				ret.add(tbs.getString("TABLE_NAME"));
			}
			DB.close(tbs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(conn);
		}
		return ret;

	}

	public static String translate(String value) {
		value=value.replace("'", "\\'");
		String ret = value;
		if(value.endsWith("_id")) {
			value=value.substring(0,value.length()-3);
		}
		ret = queryString("select name from datadic where field=?", value);
		if (ret == null) {
			return value;
		} else {
			return ret;
		}
	}

	public static List<List> getList(String tbname) {
		tbname = tbname.replace("`", "");
		String sql = "select * from `" + tbname + "`";
		sql = JspGenerator.getJoinTableSQL(tbname, true);
		return queryList(sql, true);

	}

	public static List<List> queryList(String sql, boolean title,
			String... values) {
		// TODO Auto-generated method stub
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<List> ret = new LinkedList<List>();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; i++) {
				pstmt.setString(i + 1, values[i]);
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			List row = null;
			if (title) {
				row = new LinkedList();
				int shortLen = Integer.parseInt(Config.get("text.shortLen"));
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.add(Tools.shortString(
							translate(rsmd.getColumnLabel(i)), shortLen));
				}
				ret.add(row);
			}

			while (rs.next()) {
				row = new LinkedList();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.add(rs.getObject(i));
				}
				ret.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return ret;
	}

	private static String queryString(String sql, String... values) {
		List<List> ll = queryList(sql, false, values);
		if (ll != null && ll.size() > 0) {
			List l = ll.get(0);
			if (l != null && l.size() > 0) {
				return String.valueOf(l.get(0));
			}
		}
		return null;
	}

	public static int update(String sql, Object... values) {
		int ret = 0;
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			int i = 1;
			for (Object object : values) {
				pstmt.setObject(i++, object);
			}
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
		}

		DB.close(conn);
		return ret;
	}
}
