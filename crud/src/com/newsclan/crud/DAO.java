package com.newsclan.crud;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DAO {
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
		String ret = value;
		ret = queryString("select name from datadic where field=?", value);
		if (ret == null) {
			return value;
		} else {
			return ret;
		}
	}

	public static List<List> getList(String tbname) {
		tbname = tbname.replace("'", "\\'");
		String sql = "select * from `" + tbname + "`";
		sql = JspGenerator.getJoinTableSQL(tbname, true);
		return queryList(sql, true);

	}

	private static List<List> queryList(String sql, boolean title,
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
}
