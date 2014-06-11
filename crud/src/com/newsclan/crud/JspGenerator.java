package com.newsclan.crud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;

public class JspGenerator {

	private static final int PAGE_SIZE = 16;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = "users,news".split(",");
		Connection conn = DB.getConnection();
		Statement stmt = null;
		Vector<String> v = new Vector<String>();
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("show tables");
			while (rs.next()) {
				v.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DB.close(rs);

		DB.close(stmt);

		DB.close(conn);

		args = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			args[i] = (String) v.get(i);
			// if ("abnormallog,device,room_device,".indexOf(args[i]) == -1)
			generateJSP(args[i]);
		}
		// generateJSP("room");
		// generateJSP("powertype");
		// generateJSP("room_status");
		// for(int i=0;i<args.length;i++){
		// System.out.format("atb_%s,", args[i]);
		// }
		generateMenu(args);
		System.exit(0);
	}

	/**
	 * @deprecated Use {@link Tools#shortString(String,int)} instead
	 */
	public static String shortString(String s, int len) {
		return Tools.shortString(s, len);
	}

	public static String getJoinTableSQL(String tbname, boolean... withoutID) {
		// TODO Auto-generated method stub
		Field[] fds = getFields("select * from " + tbname);
		String ret = "select ";
		String fields = tbname + ".id as id";
		String tables = tbname;
		for (int i = 1; i < fds.length; i++) {
			if (fds[i].name.endsWith("_id")) {
				String join = fds[i].name.split("_")[0];
				fields += "," + join + "." + getFirstCharFieldName(join)
						+ " as `" + join + "`";
				if (withoutID.length == 0 || !withoutID[0]) {
					fields += "," + join + ".id as `" + fds[i].name + "_value`";
				}
				tables += " left join " + join + " on " + tbname + "."
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

	private static String getFirstCharFieldName(String tbname) {
		// TODO Auto-generated method stub
		String ret = "name";
		Connection conn = DB.getConnection();
		ResultSetMetaData rsmd = getRecordSetMetaDataOfSQL("select * from `"
				+ tbname + "`", conn);
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

	private static void generateMenu(String[] tables) {
		StringBuffer jsp = new StringBuffer();
		String[] funcs = "list".split(",");
		String[] funcnames = "管理".split(",");
		jsp.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n");
		jsp.append("<%@ include file=\"jsp/checkLogin.jsp\" %>\n");
		jsp.append("<link rel=stylesheet href='bootstrap/css/bootstrap.min.css' type='text/css'>");
		jsp.append("<link rel=stylesheet href='jsp/mis.css' type='text/css'>\n<body background=jsp/bg2.jpg>\n");

		for (int i = 0; i < tables.length; i++) {
			for (int j = 0; j < funcs.length; j++) {
				jsp.append("<%if(session.getAttribute(\"admin\")!=null||session.getAttribute(\"r"
						+ tables[i] + "\")!=null){\n%>");
				jsp.append("<a class='btn' href=\"");
				jsp.append(tables[i]);
				jsp.append("_" + funcs[j] + ".jsp\" target=main>");
				jsp.append(getDataDict(tables[i]));
				jsp.append(funcnames[j]);
				jsp.append("</a><br><%}%>\n");
			}

		}
		jsp.append("<a class='btn btn-success' href=jsp/report_select.jsp target=main>系统报表</a><br>\n");

		jsp.append("<a class='btn btn-danger' href=jsp/logout.jsp target=_top>�?��</a>\n");

		writeFile("menu.jsp", jsp.toString());
	}

	private static void generateJSP(String tbName) {
		// TODO Auto-generated method stub
		String sql = "select * from " + tbName;
		generateView(tbName, sql);
		generateInput(tbName, sql);
		generateEdit(tbName, sql);
		generateDel(tbName);
		generateList(tbName, sql);

	}

	private static void generateDel(String tbName) {
		// TODO Auto-generated method stub
		StringBuffer asp = new StringBuffer();
		asp.append(getInclude("d", tbName));

		asp.append("<%String id,sql;\n");
		asp.append("id=request.getParameter(\"id\");\n");
		asp.append("sql=\"delete from " + tbName + " where id=\"+id;\n");
		asp.append("Statement stmt=conn.createStatement();\n");
		asp.append("stmt.execute(sql);\n");
		asp.append("stmt.close();\n");
		asp.append("\nresponse.sendRedirect (\"" + tbName
				+ "_list.jsp?id=\"+id);\n%>");
		asp.append("<%" + getClose() + "%>");
		writeFile(tbName + "_del.jsp", asp.toString());
	}

	private static String getPrivilegeCode(String crud, String tbName) {
		return ("<%if(session.getAttribute(\"admin\")==null&&session.getAttribute(\""
				+ crud + tbName + "\")==null) return;\n%>\n");
	}

	private static void generateList(String tbName, String sql) {
		StringBuffer asp = new StringBuffer();
		asp.append(getInclude("r", tbName));

		Field[] fs = getFields(sql);

		asp.append(getStringCode(fs));
		asp.append(getListCode(tbName, sql, fs));
		asp.append("<%if(session.getAttribute(\"admin\")!=null||session.getAttribute(\"c"
				+ tbName + "\")!=null){\n%>");

		asp.append("<a class='btn btn-success' href=\"" + tbName
				+ "_input.jsp\">添加</a>\n");
		asp.append("<%}%>\n");

		asp.append("<form method=post>查找:"
				+ "<input type=text name=keyword style='height:32px'>"
				+ "<input  class='btn' type=submit value=确定>" + "</form>");
		asp.append("<%" + getClose() + "%>");
		writeFile(tbName + "_list.jsp", asp.toString());
	}

	private static void generateEdit(String tbName, String sql) {
		StringBuffer asp = new StringBuffer();
		asp.append(getInclude("u", tbName));

		Field[] fs = getFields(sql);
		asp.append(getStringCode(fs));
		asp.append(getUpdateCode(tbName, fs));
		asp.append(getSelectCode(sql, fs));
		asp.append(getEditCode(sql, fs, true));
		asp.append("<%\n DB.close(stmt);\n\n" + getClose() + "}%>");
		writeFile(tbName + "_edit.jsp", asp.toString());
	}

	private static void generateInput(String tbName, String sql) {
		StringBuffer asp = new StringBuffer();
		asp.append(getInclude("c", tbName));

		Field[] fs = getFields(sql);
		asp.append(getStringCode(fs));
		asp.append(getInsertCode(tbName, fs));
		asp.append(getEditCode(sql, fs, false));
		// asp.append(getClose());
		if (tbName.equals("room_device"))
			return;
		writeFile(tbName + "_input.jsp", asp.toString());
	}

	private static void generateView(String tbName, String sql) {
		StringBuffer asp = new StringBuffer();
		asp.append(getInclude("r", tbName));

		Field[] fs = getFields(sql);
		asp.append(getStringCode(fs));
		asp.append(getSelectCode(getJoinTableSQL(tbName), fs));
		asp.append(getViewCode(fs));
		if (tbName.equals("room")) {
			// asp.append("\n<%String hsid=rs.getString(\"hub_id_value\");%>\n");

			asp.append("<script type='text/javascript'>\n"
					+ "function reloadinfo(){\n"
					// + "$('#info').text('实时数据加载�?..');\n"
					+ "$('#info').load('remote/room_view.jsp?hub_id=<%=hsid%>&ammeter=<%=ammeter%>');\n");
			asp.append("}\n$(document).ready(function(){\n"
					+ "	window.setInterval('reloadinfo()',1000);\n"
					+ "});\n</script>\n");
		}
		asp.append("<%\n DB.close(stmt);\n" + getClose() + "%>");
		if (tbName.equals("room"))
			asp.append("<%@ include file=\"remote/room_menu.jsp\"%>\n");
		writeFile(tbName + "_view.jsp", asp.toString());
	}

	private static String getInclude(String crud, String tbName) {
		StringBuffer jsp = new StringBuffer();
		jsp.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n");
		// jsp.append("<%\nrequest.setCharacterEncoding(\"UTF-8\");%>\n");
		jsp.append("<%@ include file=\"jsp/checkLogin.jsp\" %>\n");
		jsp.append(getPrivilegeCode(crud, tbName));
		jsp.append("<%@ include file=\"jsp/conn.jsp\" %>\n");
		jsp.append("<%@ include file=\"date/date.jsp\" %>\n");
		return jsp.toString();
	}

	private static Object getInsertCode(String tbName, Field[] fs) {
		StringBuffer ret = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		StringBuffer val = new StringBuffer();
		StringBuffer set = new StringBuffer();

		ret.append("<%\n");
		ret.append("if (request.getMethod().equals(\"POST\")) {\n\t");
		sql.append("insert into `" + tbName + "` ( ");

		for (int i = 0; i < fs.length; i++) {
			if (fs[i].name.equals("id"))
				continue;
			ret.append("" + fs[i].name == null ? "" : fs[i].name);
			ret.append("=request.getParameter(\"");
			ret.append(fs[i].name == null ? "" : fs[i].name);
			ret.append("\");\n\t");
			sql.append("`");
			sql.append(fs[i].name == null ? "" : fs[i].name);
			sql.append("`,");
			set.append(getSetOfField(i, fs[i]));
			val.append("?,");
		}
		sql.deleteCharAt(sql.length() - 1);
		val.deleteCharAt(val.length() - 1);
		sql.append(") values(");
		sql.append(val);
		ret.append("String sql=\"" + sql + " )\";\n");
		ret.append("\tPreparedStatement pstmt=conn.prepareStatement(sql);\n");
		ret.append(set);
		ret.append("\tpstmt.executeUpdate();\n");
		ret.append("\tDB.close(pstmt);\n");
		ret.append(getClose());
		ret.append("\tresponse.sendRedirect (\"" + tbName
				+ "_list.jsp?id=\"+id);\n");
		ret.append("}%>\n");
		return ret.toString();
	}

	private static String getSetOfField(int i, Field field) {
		// TODO Auto-generated method stub
		// i++;
		if (isFieldNumber(field)) {
			return "\t\npstmt.setInt(" + i + ",Integer.parseInt(" + field.name
					+ "));\n";
		} else if (isFieldDate(field)) {
			return "\t\npstmt.setTimestamp("
					+ i
					+ ",new java.sql.Timestamp(new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse("
					+ field.name + ").getTime()));\n";
		} else {
			return "\t\npstmt.setString(" + i + "," + field.name + ");\n";
		}

	}

	private static String getUpdateCode(String tbName, Field[] fs) {
		StringBuffer ret = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		StringBuffer set = new StringBuffer();

		ret.append("<%\n");
		ret.append("if (request.getMethod().equals(\"POST\")) {\n\t");
		sql.append("update " + tbName + " set ");

		for (int i = 0; i < fs.length; i++) {
			if (fs[i].name.equals("id"))
				continue;
			ret.append("" + fs[i].name == null ? "" : fs[i].name);
			ret.append("=request.getParameter(\"");
			ret.append(fs[i].name == null ? "" : fs[i].name);
			ret.append("\");\n\t");
			sql.append("`");
			sql.append(fs[i].name == null ? "" : fs[i].name);
			sql.append("`");

			sql.append("=?,");
			set.append(getSetOfField(i, fs[i]));

		}
		sql.deleteCharAt(sql.length() - 1);
		ret.append("String sql=\"" + sql + " where id=\"+id;");

		ret.append("\n\tPreparedStatement pstmt=conn.prepareStatement(sql);\n");
		ret.append(set);
		ret.append("\n\t pstmt.executeUpdate();\n");
		ret.append("\n\t pstmt.close();\n");
		ret.append(getClose());

		ret.append("\nresponse.sendRedirect( \"" + tbName
				+ "_list.jsp?id=\"+id);\n");
		ret.append("\n}else{%>\n");
		return ret.toString();
	}

	private static String getSQLValueOfField(Field f) {
		StringBuffer sql = new StringBuffer();
		sql.append((isFieldNumber(f) ? "" : "'") + "\"&replace(");
		sql.append(f.name == null ? "" : f.name);
		sql.append(",\"'\",\"''\")&\"" + (isFieldNumber(f) ? "" : "'"));
		return sql.toString();
	}

	private static boolean isFieldNumber(Field f) {
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

	private static void debug(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}

	private static String getListCode(String tbname, String sql, Field[] fs) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		ret.append("<%String keypre=\"\";\n");
		ret.append("String sql=\"");
		ret.append(getJoinTableSQL(tbname));
		ret.append("\";\n");
		ret.append("String keyword=request.getParameter(\"keyword\");\n");
		if (tbname.equals("abnormallog")) {
			ret.append("if(room_id!=null) {\n	" + "    keyword=null;"
					+ "    sql+=\" where room.id=\"+room_id;" + "\n}else{	\n"
					+ "      sql+=\" where room.id!=0 \";" + "\n}");
		}
		ret.append("if (keyword!=null){ \n");
		ret.append("if(request.getMethod().equalsIgnoreCase(\"get\"))");
		ret.append("keyword=new String(keyword.getBytes(\"iso8859-1\"));");
		ret.append("sql+=\" where \";");

		for (int i = 1; i < fs.length; i++) {
			if (isFieldDate(fs[i]))
				continue;
			ret.append("sql+=\" ");

			if (fs[i].name.endsWith("_id")) {
				ret.append(fs[i].name.replace("_id", ".name"));
			} else {
				if (fs[i].name.equals("name"))
					ret.append(fs[i].table + ".");
				ret.append(fs[i].name);
			}
			ret.append(" like '%\"+keyword+\"%' \";\n");

			if (i < fs.length - 1)
				ret.append("sql+=\"or \";\n");
		}
		ret.append("keypre=\"keyword=\"+java.net.URLEncoder.encode(keyword)+\"&\";");

		ret.append("}\n");
		ret.append(" session.setAttribute(\"sql\", sql); ");

		ret.append("String spage=request.getParameter(\"page\");\n");
		ret.append("if (spage==null) spage=\"0\";\n");
		ret.append("int ipage=Integer.parseInt(spage);\n");
		ret.append("sql +=\" order by id desc limit \"+(ipage*" + PAGE_SIZE
				+ ")+\"," + PAGE_SIZE + "\";\n");

		ret.append("Statement stmt=conn.createStatement();\n");
		ret.append("ResultSet rs=stmt.executeQuery(sql);\n");

		ret.append("%>\n");

		ret.append("<form method=post><table class='table'>\n");
		ret.append("<tr><th class=toprow colspan=" + (fs.length + 1) + ">"
				+ getDataDict(fs[0].table) + "</th></tr>");

		ret.append("<tr class=toprow>");
		for (int i = 0; i < fs.length && i < 10; i++) {
			ret.append("<td>");
			ret.append(fs[i].label == null ? "" : fs[i].label);
			ret.append("</td>");
		}
		ret.append("<td></td></tr>\n");
		ret.append("<%" + "int row=0;\n" + "" + "while (rs.next()){\n");
		ret.append(getFetchData(fs));
		ret.append("%>");
		ret.append("<tr <%=row++%2==1?\"class=oddrow\":\"\"%>>\n");
		for (int i = 0; i < fs.length && i < 10; i++) {
			ret.append("\t<td>");
			if (fs[i].name.endsWith("_id")) {

				String linktable = fs[i].name.substring(0,
						fs[i].name.length() - 3);
				ret.append("<a href=\"" + linktable
						+ "_view.jsp?id=<%=rs.getString(\"" + fs[i].name
						+ "_value\")%>\">");

			}

			ret.append(getJSPValue(fs[i], 100 / fs.length));
			if (fs[i].name.endsWith("_id"))
				ret.append("</a>");
			ret.append("</td>\n");

		}
		// ret.append("\t<%rs.movenext%>\n<td>");
		ret.append("\t<td><a class='btn' href=\"" + tbname
				+ "_view.jsp?id=<%=id%>\">查看</a>\n");
		ret.append("<%if(session.getAttribute(\"admin\")!=null||session.getAttribute(\"u"
				+ tbname + "\")!=null) {\n%>");
		ret.append("\t<a class='btn btn-primary' href=\"" + tbname
				+ "_edit.jsp?id=<%=id%>\">编辑</a>\n");
		ret.append("<%}\n%>");
		ret.append("<%if(session.getAttribute(\"admin\")!=null||session.getAttribute(\"d"
				+ tbname + "\")!=null) {\n%>");
		ret.append("\t<a class='btn btn-danger' href=\"" + tbname
				+ "_del.jsp?id=<%=id%>\">删除</a>\n");
		ret.append("<%}\n%></td>");

		ret.append("</tr>\n");
		ret.append("<%}%>\n");

		ret.append("</table></form>\n");
		ret.append("<%\n\t DB.close(stmt);\n%>");

		ret.append(" <a class='btn btn-success' target=_blank href=jsp/export.jsp>导出</a>  ");
		if (tbname.equals("room"))
			ret.append(" <a class='btn btn-success' href=remote/batch.jsp>批量操作</a>  ");

		ret.append("<%=ipage>0?\"<a class='btn' href="
				+ tbname
				+ "_list.jsp?\"+keypre+\"page=\"+(ipage-1)+\">上一�?/a>\":\"\"%>\n");
		ret.append("<a class='btn' href=\"" + tbname
				+ "_list.jsp?<%=keypre%>page=<%=(ipage+1)%>\">下一�?/a>\n");
		ret.append("\n");
		return ret.toString();
	}

	private static String getEditCode(String sql, Field[] fs, boolean def) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		ret.append("<form method=post><table>\n");
		ret.append("<tr><th class=toprow colspan=" + fs.length + ">"
				+ getDataDict(fs[0].table) + "</th></tr>");

		for (int i = 0; i < fs.length; i++) {
			if (fs[i].name.equals("id"))
				continue;
			ret.append("<tr>");
			ret.append("<td>");
			ret.append(fs[i].label == null ? "" : fs[i].label);
			ret.append("</td>\t<td>");
			ret.append(getEditCodeOfField(fs[i], def));
			ret.append("</td></tr>\n");

		}
		ret.append("<tr>");
		ret.append("<td>");
		ret.append("<input  class='btn' type=submit >");
		ret.append("</td>\t");
		ret.append("<td><input class='btn' type=reset></td>");
		ret.append("</tr>\n");

		ret.append("</table></form>\n");

		return ret.toString();
	}

	private static String getEditCodeOfField(Field f, boolean def) {
		StringBuffer ret = new StringBuffer();
		if (f.name.equalsIgnoreCase("id")) {
			ret.append(getJSPValue(f));
			ret.append("<input type=hidden name=\"");
			ret.append(f.name == null ? "" : f.name);
			ret.append("\" value=\"");
			ret.append(getJSPValue(f));
			ret.append("\">");
		} else {
			if (f.name.equals("content") || f.name.equals("value")) {
				ret.append("<textarea name=\"");
				ret.append(f.name == null ? "" : f.name);
				ret.append("\" cols=50 rows=10>");
				if (def)
					ret.append(getJSPValue(f));
				ret.append("</textarea>");
			} else if (f.name.endsWith("_id")) {
				ret.append("<jsp:include page=\"jsp/getSelect.jsp\">\n");
				ret.append("<jsp:param name=\"tbname\" value=\""
						+ f.name.substring(0, f.name.length() - 3) + "\" />\n");
				ret.append("<jsp:param name=\"fdname\" value=\"name\" />\n");
				if (def)
					ret.append("<jsp:param name=\"fdvalue\" value=\"<%="
							+ f.name + "%>\" />\n");
				ret.append("</jsp:include>\n");
			} else {
				ret.append("<input style='height:32px' type=text name=\"");
				ret.append(f.name == null ? "" : f.name);
				ret.append("\" value=\"");
				if (def)
					ret.append(getJSPValue(f));
				else if (f.name.endsWith("date"))
					ret.append("<%=new java.util.Date().toLocaleString()%>\" onClick=\"eye.datePicker.show(this);");
				else if (isFieldNumber(f)) {
					ret.append("0");
				}
				ret.append("\">");
			}
		}
		return ret.toString();
	}

	private static String getJSPValue(Field f) {

		return getJSPValue(f, 0);
	}

	private static String getJSPValue(Field f, int len) {

		String ret = null;

		ret = "<%=JspGenerator.shortString(" + f.name + "," + len + ") %>";

		return ret;
	}

	// private static String getInputCode(Field[] fs) {
	// // TODO Auto-generated method stub
	// StringBuffer ret = new StringBuffer();
	// ret.append("<table>");
	// for (int i = 0; i < fs.length; i++) {
	// if (fs[i].name.equals("id"))
	// continue;
	// ret.append("<tr>");
	// ret.append("<td>");
	// ret.append(fs[i].label == null ? "" : fs[i].label);
	// ret.append("</td>\t");
	// ret.append("<td><input type=text name=\"");
	// ret.append(fs[i].name == null ? "" : fs[i].name);
	// ret.append("\"></td>");
	// ret.append("</tr>\n");
	//
	// }
	// ret.append("</table>");
	// return ret.toString();
	// }

	private static String getClose() {
		// TODO Auto-generated method stub

		return "\nDB.freeConnection(conn);\n";
	}

	private static String getSelectCode(String sql, Field[] fs) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		ret.append("<%\n");
		ret.append("String sql=\"");
		ret.append(sql);
		ret.append(" where " + fs[0].table + ".id=\"+id;\n");
		ret.append("\nStatement stmt=conn.createStatement();\n");
		ret.append("\nResultSet rs=stmt.executeQuery(sql);\n");

		if (fs[0].table.equals("room")) {
			ret.append("\nString hsid=null;\n");
		}
		ret.append("if(rs.next()){\n");
		ret.append(getFetchData(fs));
		if (fs[0].table.equals("room")) {
			ret.append("\ntry{hsid=rs.getString(\"hub_id_value\");}catch(Exception e){}\n");
		}
		ret.append("}%>\n");

		return ret.toString();
	}

	private static String getFetchData(Field[] fs) {
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < fs.length; i++) {
			ret.append("\t");
			ret.append(fs[i].name == null ? "" : fs[i].name);

			// if (isFieldDate(fs[i])) {
			// // System.out.println(fs[i].name+":"+fs[i].type);
			// ret.append("=new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(rs.getDate(\"");
			// } else {
			ret.append("=rs.getString(\"");
			// }

			ret.append(fs[i].name == null ? "" : fs[i].name);
			ret.append("\")");
			// if (isFieldDate(fs[i])) {
			// ret.append(")");
			//
			// }
			ret.append(";\n");
		}
		return ret.toString();
	}

	private static boolean isFieldDate(Field f) {
		switch (f.type) {
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			return true;
		default:
			return false;
		}
	}

	private static String getStringCode(Field[] fs) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		StringBuffer set = new StringBuffer();

		ret.append("<%\nrequest.setCharacterEncoding(\"UTF-8\");\nString ");
		for (int i = 0; i < fs.length; i++) {
			ret.append(fs[i].name == null ? "" : fs[i].name);
			ret.append("=null,");
			if (fs[i].name.toLowerCase().endsWith("_id")
					|| fs[i].name.equalsIgnoreCase("id"))
				set.append("\n" + fs[i].name + "=request.getParameter(\""
						+ fs[i].name + "\");");
		}
		ret.deleteCharAt(ret.length() - 1);
		ret.append(";");
		ret.append(set);
		ret.append("\n%>\n");
		return ret.toString();
	}

	private static String getViewCode(Field[] fs) {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		ret.append("<table>");
		ret.append("<tr><th class=toprow colspan=" + fs.length + ">"
				+ getDataDict(fs[0].table)
				+ "</th><th rowspan=19><span id=info></span>\n<th></tr>");
		for (int i = 0; i < fs.length; i++) {
			ret.append("<tr>");
			ret.append("<td>");
			ret.append(fs[i].label == null ? "" : fs[i].label);
			ret.append("</td>\t");
			ret.append("<td>");

			if (fs[i].name.endsWith("_id")) {

				String linktable = fs[i].name.substring(0,
						fs[i].name.length() - 3);
				ret.append("<a href=\"" + linktable
						+ "_view.jsp?id=<%=rs.getString(\"" + fs[i].name
						+ "_value\")%>\">");

			}

			ret.append(getJSPValue(fs[i]));
			if (fs[i].name.endsWith("_id"))
				ret.append("</a>");

			ret.append("</td>");
			ret.append("</tr>\n");

		}
		ret.append("</table>");
		ret.append("<%if(session.getAttribute(\"admin\")!=null||session.getAttribute(\"u"
				+ fs[0].table + "\")!=null){\n%>");

		ret.append("<a class='btn btn-primary' href='" + fs[0].table
				+ "_edit.jsp?id=<%=id%>' >编辑</a>");
		ret.append("<%}\n%>");

		ret.append("<a class='btn' href='javascript:history.go(-1);' >后�?</a><br>");
		return ret.toString();
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

	public static Field[] getFields(String sql) {
		Connection conn = DB.getConnection();
		ResultSetMetaData rsmd = getRecordSetMetaDataOfSQL(sql, conn);

		Field[] f = null;
		try {
			int c = rsmd.getColumnCount();
			f = new Field[c];
			for (int i = 0; i < c; i++) {
				f[i] = new Field();
				f[i].name = rsmd.getColumnName(i + 1);
				f[i].type = rsmd.getColumnType(i + 1);
				f[i].label = getDataDict(rsmd.getColumnLabel(i + 1));
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

	private static String getDataDict(String columnLabel) {
		Connection conn = DB.getConnection();
		if (columnLabel.endsWith("_id"))
			columnLabel = columnLabel.substring(0, columnLabel.length() - 3);
		String sql = "select name from datadic where field=?";

		PreparedStatement pstmt = null;
		String ret = columnLabel;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, columnLabel);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			}
			DB.close(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(pstmt);
		}

		DB.close(conn);
		return ret;
	}

	private static ResultSetMetaData getRecordSetMetaDataOfSQL(String sql,
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

	private static void writeFile(String fname, String content) {
		File f = new File("WebContent/" + fname);

		try {
			if (!f.exists())
				f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
