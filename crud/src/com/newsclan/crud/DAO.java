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

	public static String table_prefix = Config.get("db.table.prefix");

	public static int insert(int user_id, String tbname,
			Map<String, String> values) {
		if (!Auth.canInsertTable(user_id, tbname))
			return -1;
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

			if ("password".equals(field.name)) {
				d = Tools.getHash(d, Tools.getRandomSalt());
			}
			if ("input_user".equals(field.name)) {
				d = String.valueOf(user_id);
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
		if (!Auth.canUpdateTable(user_id, tbname))
			return -1;
		Field[] fds = getFieldsOfTable(tbname);
		StringBuffer sql = new StringBuffer("update  `" + tbname + "` set ");

		List<String> data = new LinkedList();
		boolean first = true;
		for (Field field : fds) {
			if ("id".equals(field.name) || first) {
				first = false;
				continue;
			}
			String d = values.get(field.name);
			if ("password".equals(field.name) && "".equals(d))
				continue;
			sql.append("`");
			sql.append(field.name);
			sql.append("`");
			sql.append("=?,");

			if (DAO.isFieldNumber(field)) {
				if ("".equals(d)) {
					d = "0";
				}
			}
			if (DAO.isFieldDate(field)) {
				if ("".equals(d)) {
					d = null;
				}
			}
			if ("password".equals(field.name)) {
				d = Tools.getHash(d, Tools.getRandomSalt());
			}
			if ("input_user".equals(field.name)) {
				d = String.valueOf(user_id);
			}
			data.add(d);

		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where " + DAO.getPrimaryKeyFieldName(tbname) + "=?");
		data.add(String.valueOf(id));
		return update(sql.toString(), data.toArray());

	}

	public static String getPrimaryKeyFieldName(String tbname) {
		String ret = getFieldsOfTable(tbname)[0].name;
		return ret;
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
				fd.transview = transview(fd.name);
				if(fd.transview!=null){
					fd.transview=fd.transview.replace("#",  tbname + "." + fd.name);
				}
				fd.ftable=transname(fd.name);
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
		if (Auth.canReadTable(user_id, tbname)
				|| Auth.canInsertTable(user_id, tbname)) {
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
		title.add("字段");
		title.add("值");

		ret.add(title);

		Field[] fds = getFieldsOfTable(tbname);
		int i = 0;
		for (Field field : fds) {
			if (!edit
					&& ("id".equals(field.name) || field.name.equals(DAO
							.getPrimaryKeyFieldName(tbname))))
				continue;
			List row = new LinkedList();
			row.add(field.label);
			if (values.length > 0) {
				if(field.name.endsWith("_file")){
					row.add("<span name='" + field.name + "'><a href=\""
							+ String.valueOf(values[0].get(i++)) + "\" target='_blank'>下载</a></span>");
				}else{
				row.add("<span name='" + field.name + "'>"
						+ String.valueOf(values[0].get(i++)) + "</span>");
				}	
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
		title.add("字段名");
		title.add("值");

		ret.add(title);

		Field[] fds = getFieldsOfTable(tbname);
		int i = 0;
		for (Field field : fds) {
			if (!edit
					&& ("id".equals(field.name) || field.name.equals(DAO
							.getPrimaryKeyFieldName(tbname))))
				continue;
			if (!edit && "input_user".equals(field.name))
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
		if(field.ftable!=null&&!field.ftable.equals(""))
			ret.append("' postLoad='1' fr='"+field.ftable);
		ret.append("'>");
	} 

	public static String getFormType(Field f) {
		// TODO Auto-generated method stub
		if (f.type == Types.DATE || f.name.endsWith("_date"))
			return "input_date";
		else if (f.type == Types.TIMESTAMP || f.name.endsWith("_time"))
			return "input_datetime";
		else
			return "form-control";
	}

	public static List<String> getTables(int user_id) {

		List ret = getTables();
		for (Iterator tbit = ret.iterator(); tbit.hasNext();) {
			String tbname = (String) tbit.next();
			if (!(Auth.canReadTable(user_id, tbname) 
					|| Auth.canInsertTable(user_id, tbname)
					|| Auth.canUpdateTable(user_id, tbname)
					
					)) {
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
		ret = queryString("select name from "+Config.sysPrefix+"datadic where field=?", value);
		if (ret == null) {
			return value;
		} else {
			return ret;
		}
	}
	public static String transname(String value) {
		value = value.replace("'", "\\'");
		String ret = value;
	
		ret = queryString("select ftable from "+Config.sysPrefix+"datadic where field=?", value);
		if (ret == null) {
			return null;
		} else {
			return ret;
		}
	}
	public static String transview(String value) {
		value = value.replace("'", "\\'");
		String ret = value;
		
		ret = queryString("select transview from "+Config.sysPrefix+"datadic where field=?", value);
		if (ret == null||"".equals(ret.trim())) {
			return null;
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

			return getList(tbname, keyword, pageNum, pageSize);

		} else if (Auth.canInsertTable(user_id, tbname)||Auth.canUpdateTable(user_id, tbname)) {

			return getList(tbname, keyword, pageNum, pageSize, user_id);

		} else {
			return new Vector();
		}
	}

	private static List<List> getList(String tbname, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return getList(tbname, "", pageNum, pageSize);
	}

	private static List<List> getList(String tbname, String keyword,
			int pageNum, int pageSize) {
		return getList(tbname, keyword, pageNum, pageSize, 1);
	}

	public static String getSelectOfTable(String tbname){
		StringBuilder sb=new StringBuilder();
		Field[] fds = DAO.getFieldsOfTable(tbname);
		for(Field fd:fds){
			String transview=transview(fd.name);
			if(transview!=null)
			sb.append(transview);
			sb.append(" ");
			sb.append(fd.name);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	private static List<List> getList(String tbname, String keyword,
			int pageNum, int pageSize, int user_id) {

		tbname = tbname.replace("`", "");
		//String select=getSelectOfTable(tbname);
		String sql = "select * from `" + tbname + "`";
		//Tools.debug(sql);
		sql = DAO.getJoinTableSQL(tbname, true);
		String[] values = {};
		String where = DAO.getKeywordLike(tbname);
		if (!"".equals(keyword)) {

			sql += " where (" + where + " ) ";

			int k = where.split("\\?").length - 1;
			Vector v = new Vector();
			for (int i = 0; i < k; i++) {
				v.add(String.format("%%%s%%", keyword));
			}
			values = (String[]) v.toArray(values);
		}
		if ((!Auth.canReadTable(user_id, tbname))
				&& Auth.canInsertTable(user_id, tbname)
				&& DAO.hasInputer(tbname)) {
			if ("".equals(keyword)) {
				sql += " where ";
			} else {
				sql += " and ";
			}
			sql += " input_user=" + user_id;
		}

		// if (tbname.endsWith("log"))
		sql += " order by "+DAO.getPrimaryKeyFieldName(tbname)+" desc";
		sql += " limit " + (pageNum * pageSize) + "," + pageSize;
		Tools.debug(sql);
		return DAO.editableList(sql, true,true, values);

	}

	private static boolean hasInputer(String tbname) {
		// TODO Auto-generated method stub
		Field[] fds = getFieldsOfTable(tbname);
		for (Field field : fds) {
			if (field.name.equals("input_user")) {
				return true;
			}
		}
		return false;
	}

	public static List<List> editableList(String sql, boolean title,boolean editable,
			Object... values) {
		// TODO Auto-generated method stub
		if(Config.debug)
			System.out.format(sql.replace("%", "%%").replace("?", "'%s'")+"\n",values);
		
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<List> ret = new LinkedList<List>();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < values.length; i++) {
				pstmt.setObject(i + 1, values[i]);
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
					Object v = rs.getObject(i);
					
					if (v == null) {
						row.add("");
					} else {
						if(i>1&&editable){
						StringBuilder sb=new StringBuilder();
						sb.append("<span class='modifiable'");
						sb.append("tb='");
						sb.append(rsmd.getTableName(i));
						sb.append("' fd='");
						sb.append(rsmd.getColumnName(i));
						sb.append("' rid='");
						sb.append(rs.getObject(1)); 
						sb.append("' >");
						sb.append(v.toString());
						sb.append("</span>");
						row.add(sb.toString());
						}else{
							row.add(v);
						}
					}
				}
				ret.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			DB.close(rs);
			DB.close(pstmt);
			DB.close(conn);
		}
		return ret;
	}
	
	public static List<List> queryList(String sql, boolean title,
			Object... values) {
		// TODO Auto-generated method stub
		
		return editableList(sql,title,false,values);
	}
	

	public static int executeUpdate(String sql, String... values) {
//		if(Config.debug)
//			System.out.format(sql.replace("%", "%%").replace("?", "'%s'")+"\n",values);
//		Connection conn = DB.getConnection();
//		PreparedStatement pstmt = null;
//		int ret = -1;
//		try {
//			pstmt = conn.prepareStatement(sql);
//			for (int i = 0; i < values.length; i++) {
//				pstmt.setString(i + 1, values[i]);
//			}
//			ret = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//
//			DB.close(pstmt);
//			DB.close(conn);
//		}
		return update(sql,values);
	}

	public static String queryString(String sql, Object ... values) {
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
		if(Config.debug)System.out.format(sql.replace("%", "%%").replace("?", "'%s'")+"\n",values);
		
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
				f[i].ftable = transname(f[i].name);
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

	private static String getKeywordLike(String tbname) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		Field[] fds = getFieldsOfTable(tbname);
		for (Field field : fds) {
			if ("password".equals(field.name))
				continue;
			//if(!isFieldText(field)) continue;
			String subtable = field.name.endsWith("_id") ? field.name
					.substring(0, field.name.length() - 3) : "";
			if (!DAO.hasTable(subtable)&&hasTable(table_prefix + subtable)) {
				subtable = table_prefix + subtable;
			}
			if(field.ftable!=null&&field.transview!=null&&!"".equals(field.transview.trim())){
				if("".equals(field.ftable)){
					sb.append(String.format(" %s like BINARY  ? or",field.transview));
				}else{
					field.ftable=getJoinTableOfField(tbname,field);
					sb.append(String.format(" `%s`.`%s` like BINARY  ? or",field.ftable,field.transview));
				}
				
			}else	if (field.name.endsWith("_id") && hasTable(subtable)
					&& !field.name.equals(getPrimaryKeyFieldName(tbname))) {

				sb.append(String.format(" `%s`.`%s` like BINARY  ? or", subtable,
						getFirstCharFieldName(subtable)));

			} else if(field.type==Types.TIMESTAMP||field.type==Types.DATE){
				sb.append(String.format(" DATE_FORMAT(`%s`.`%s` ,'%%Y-%%m-%%d %%H:%%i:%%s') like BINARY  ? or", field.table,
						field.name));

				
			}else {
				sb.append(String.format(" `%s`.`%s` like BINARY  ? or", field.table,
						field.name));

			}
		}
		if (sb.length() > 2)
			sb.delete(sb.length() - 2, sb.length());

		return sb.toString();
	}

	public static String getJoinTableSQL(String tbname, boolean... withoutID) { 
		// TODO Auto-generated method stub
		Field[] fds = getFieldsOfTable(tbname);
		String ret = "select ";
		String fields = "";

		String tables = String.format("`%s` `%s`", tbname, tbname);
		for (Field field : fds) {
			String join = getJoinTableOfField(tbname, field);
			if ((field.name.endsWith("_id") || field.ftable != null)
					&& !join.equals(tbname) && hasTable(join)
					&& !field.name.equals(getPrimaryKeyFieldName(tbname))) {
				String dataFieldName = getFirstCharFieldName(join);

				if (field.transview != null&&!"".equals(field.transview)) {
					dataFieldName = field.transview;
				}

				fields += ",`" + join + "`.`" + dataFieldName + "` as `" + join
						+ "`";
				if (withoutID.length == 0 || !withoutID[0]) {
					fields += ",`" + join + "`.`" + getPrimaryKeyFieldName(join)
							+ "` as `" + field.name + "_value`";
				}
				tables += " left join `" + join + "` `" + join + "` on "
						+ tbname + "." + field.name + "=" + join + "."
						+ getPrimaryKeyFieldName(join) + "";

			} else {
				String transview = field.transview;
				if (transview == null) {
					fields += ",`" + tbname + "`.`" + field.name + "` as `"
							+ field.name + "`";
				} else {
					transview = transview.replace("#", "`"+tbname + "`.`"
							+ field.name+"`");
					fields += "," + transview + " as `" + field.name + "`";
				}
			}
		}
		ret += fields.substring(1) + " from " + tables + " ";
		if (Config.debug)
			System.out.println(ret);
		return ret;
	}
	public static String getJoinTableOfField(String tbname, Field field) {
		String join = field.name.endsWith("_id") ? field.name.substring(0,
				field.name.length() - 3) : "";

		if (field.ftable != null && hasTable(field.ftable)) {
			join = field.ftable;
		} else {
			if (!DAO.hasTable(join) && hasTable(table_prefix + join)) {
				join = table_prefix + join;
			}
		}
		
		return join;
	}
 
	public static boolean hasTable(String table) {
		// TODO Auto-generated method stub
		String yes = DAO.queryString(
				"select 'yes' from `" + table + "` limit 1", new String[] {});
		return "yes".equals(yes);
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
