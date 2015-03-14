package com.newsclan.crud;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;

public class DAO {
	public static int insert(int user_id, String tbname,
			Map<String, String> values) {
		if (Auth.canInsertTable(user_id, tbname)) {
			return insert(tbname, values);
		} else {
			return -1;
		}
	}

	private static int insert(String tbname, Map<String, String> values) {
		Field[] fds = getFieldsOfTable(tbname);
		StringBuffer sql = new StringBuffer("insert into `" + tbname + "`(");
		StringBuffer value = new StringBuffer(" values(");

		List<String> data = new LinkedList();
		for (Field field : fds) {
			if ("id".equals(field.name))
				continue;
			String d = values.get(field.name);
			if (DAO.isFieldNumber(field)) {
				if ("".equals(d)) {
					d = "0";
				}
			}
			data.add(d);
			sql.append("`");
			sql.append(field.name);
			sql.append("`,");
			value.append("?,");
		}
		sql.deleteCharAt(sql.length() - 1);
		value.deleteCharAt(value.length() - 1);
		sql.append(") ");
		sql.append(value);
		sql.append(") ");
		return update(sql.toString(), data.toArray());

	}

	public static int update(int user_id, String tbname, int id,
			Map<String, String> values) {
		if (Auth.canUpdateTable(user_id, tbname)) {
			return update(tbname, id, values);
		} else {
			return -1;
		}

	}

	private static int update(String tbname, int id, Map<String, String> values) {
		Field[] fds = getFieldsOfTable(tbname);
		StringBuffer sql = new StringBuffer("update  `" + tbname + "` set ");

		List<String> data = new LinkedList();
		for (Field field : fds) {
			if ("id".equals(field.name))
				continue;
			sql.append(field.name);
			sql.append("=?,");

			String d = values.get(field.name);
			if (DAO.isFieldNumber(field)) {
				if ("".equals(d)) {
					d = "0";
				}
			}
			if ("password".equals(field.name)) {
				d = Tools.getHash(d, Tools.getRandomSalt());
			}
			data.add(d);

		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where id=?");
		data.add(String.valueOf(id));
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

	public static List<List> getForm(int user_id, String tbname, boolean edit,
			List<String>... values) {
		if (Auth.canReadTable(user_id, tbname)) {
			return getForm(tbname, edit, values);
		} else {
			return null;
		}
	}

	public static List<List> getView(int user_id, String tbname, boolean edit,
			List<String>... values) {
		if (Auth.canReadTable(user_id, tbname)) {
			return getView(tbname, edit, values);
		} else {
			return null;
		}
	}

	private static List<List> getView(String tbname, boolean edit,
			List<String>... values) {
		List<List> ret = new LinkedList<List>();
		List title = new LinkedList();
		title.add("名称");
		title.add("值");

		ret.add(title);

		Field[] fds = getFieldsOfTable(tbname);
		int i = 0;
		for (Field field : fds) {
			if (!edit && "id".equals(field.name))
				continue;
			List row = new LinkedList();
			row.add(field.label);
			if (values.length > 0) {
				row.add(String.valueOf(values[0].get(i++)));
			} else {
				row.add("");
			}
			ret.add(row);
		}
		return ret;

	}

	private static List<List> getForm(String tbname, boolean edit,
			List<String>... values) {
		List<List> ret = new LinkedList<List>();
		List title = new LinkedList();
		title.add("名称");
		title.add("值");

		ret.add(title);

		Field[] fds = getFieldsOfTable(tbname);
		int i = 0;
		for (Field field : fds) {
			if (!edit && "id".equals(field.name))
				continue;
			List row = new LinkedList();
			row.add(field.label);
			if (values.length > 0) {
				row.add(getInputForm(field, String.valueOf(values[0].get(i++))));
			} else {
				row.add(getInputForm(field));
			}
			ret.add(row);
		}
		return ret;

	}

	public static String getInputForm(Field field, String... value) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		if (isFieldLongText(field))
			getTextArea(field, ret, value);
		else
			getInputText(field, ret, value);
		return ret.toString();
	}

	private static void getTextArea(Field field, StringBuffer ret,
			String[] value) {
		ret.append("<textarea class=ckeditor name='");
		ret.append(field.name);
		ret.append("' ");
		ret.append(">");
		if (value.length > 0)
			ret.append(Tools.toHTML(value[0]));
		ret.append("</textarea>");
	}

	private static boolean isFieldLongText(Field field) {
		// TODO Auto-generated method stub
		return field.type == Types.LONGVARCHAR
				|| field.type == Types.LONGNVARCHAR;
	}

	public static void getInputText(Field field, StringBuffer ret,
			String... value) {
		ret.append("<input name='");
		ret.append(field.name);
		ret.append("' ");
		ret.append("value='");
		if (value.length > 0)
			ret.append(Tools.toHTML(value[0]));
		ret.append("' type='text' class='");
		ret.append(getFormType(field));
		ret.append("'>");
	}

	public static String getFormType(Field f) {
		// TODO Auto-generated method stub
		if (f.type == Types.DATE || f.type == Types.TIMESTAMP
				|| f.name.endsWith("_date"))
			return "input_date";
		return "input_text";
	}

	public static List<String> getTables(int user_id) {

		List ret = getTables();
		for (Iterator tbit = ret.iterator(); tbit.hasNext();) {
			String tbname = (String) tbit.next();
			if (!Auth.canReadTable(user_id, tbname)) {
				tbit.remove();
			}
		}
		return ret;
	}

	private static List<String> getTables() {
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
		value = value.replace("'", "\\'");
		String ret = value;
		if (value.endsWith("_id")) {
			value = value.substring(0, value.length() - 3);
		}
		ret = queryString("select name from datadic where field=?", value);
		if (ret == null) {
			return value;
		} else {
			return ret;
		}
	}

	public static List<List> getList(String tbname) {

		return getList(tbname, 0, Config.pageSize);
	}

	public static List<List> getList(int user_id, String tbname, int pageNum,
			int pageSize) {
		return getList(user_id, tbname, "", pageNum, pageSize);
	}

	public static List<List> getList(int user_id, String tbname,
			String keyword, int pageNum, int pageSize) {
		if (Auth.canReadTable(user_id, tbname)) {
			if("".equals(keyword)){
				return getList(tbname, pageNum, pageSize);
			}else{
				return getList(tbname,keyword, pageNum, pageSize);
			}
		} else
			return null;
	}

	private static List<List> getList(String tbname, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return getList(tbname,"", pageNum, pageSize);
	}

	private static List<List> getList(String tbname,String keyword, int pageNum, int pageSize) {

		tbname = tbname.replace("`", "");
		String sql = "select * from `" + tbname + "`";
		sql = DAO.getJoinTableSQL(tbname, true);
		if(!"".equals(keyword))
			sql+=" where "+ DAO.getKeywordLike(tbname, keyword);
		sql += " limit " + (pageNum * pageSize) + "," + pageSize;
		Tools.debug(sql);
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

	public static int executeUpdate(String sql, String... values) {
		System.out.println(sql);
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		int ret = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; i++) {
				pstmt.setString(i + 1, values[i]);
			}
			ret = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			DB.close(pstmt);
			DB.close(conn);
		}
		return ret;
	}

	public static String queryString(String sql, String... values) {
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

	public static Field[] getFields(String sql) {
		Connection conn = DB.getConnection();
		ResultSetMetaData rsmd = DAO.getRecordSetMetaDataOfSQL(sql, conn);

		Field[] f = null;
		try {
			int c = rsmd.getColumnCount();
			f = new Field[c];
			for (int i = 0; i < c; i++) {
				f[i] = new Field();
				f[i].name = rsmd.getColumnName(i + 1);
				f[i].type = rsmd.getColumnType(i + 1);
				f[i].label = translate(rsmd.getColumnLabel(i + 1));
				f[i].table = rsmd.getTableName(i + 1);
				// System.out.println(f[i].type);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(conn);
		}
		return f;
	}

	public static String getFirstCharFieldName(String tbname) {
		// TODO Auto-generated method stub
		String ret = "name";
		Connection conn = DB.getConnection();
		ResultSetMetaData rsmd = DAO.getRecordSetMetaDataOfSQL(
				"select * from `" + tbname + "`", conn);
		try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				int type = rsmd.getColumnType(i);
				if (DB.isChar(type)) {
					ret = rsmd.getColumnName(i);
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB.close(conn);

		return ret;
	}
	private static String getKeywordLike(String tbname, String keyword) {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		Field[] fds = getFields("select * from " + tbname);
		for (Field field : fds) {
			if("password".equals(field.name))
				continue;				
			sb.append(String.format(" %s.%s like '%%%s%%' or", field.table,field.name,keyword));
		}
		if(sb.length()>2) sb.delete(sb.length()-2, sb.length());
		
		return sb.toString(); 
	}
	public static String getJoinTableSQL(String tbname, boolean... withoutID) {
		// TODO Auto-generated method stub
		Field[] fds = getFields("select * from " + tbname);
		String ret = "select ";
		String fields = tbname + ".id as id";
		String tables = String.format("`%s` `%s`", tbname,tbname) ;
		for (int i = 1; i < fds.length; i++) {
			if (fds[i].name.endsWith("_id")) {
				String join = fds[i].name
						.substring(0, fds[i].name.length() - 3);
				fields += "," + join + "." + getFirstCharFieldName(join)
						+ " as `" + join + "`";
				if (withoutID.length == 0 || !withoutID[0]) {
					fields += "," + join + ".id as `" + fds[i].name + "_value`";
				}
				tables += " left join `" + join + "` `"+join+"` on " + tbname + "."
						+ fds[i].name + "=" + join + ".id";
				;
			} else {
				fields += "," + tbname + "." + fds[i].name + " as `"
						+ fds[i].name + "`";
			}
		}
		ret += fields + " from " + tables + " ";
		return ret;
	}

	public static boolean isFieldNumber(Field f) {
		switch (f.type) {
		case Types.BIGINT:
		case Types.DECIMAL:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.INTEGER:
		case Types.NUMERIC:
		case Types.REAL:
		case Types.SMALLINT:
		case Types.TINYINT:
			return true;
		default:
			return false;
		}

	}

	public static boolean isFieldDate(Field f) {
		switch (f.type) {
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			return true;
		default:
			return false;
		}
	}

	public static ResultSetMetaData getRecordSetMetaDataOfSQL(String sql,
			Connection conn) {

		ResultSetMetaData ret = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ret = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(stmt);
		}

		return ret;
	}
}
