<%@ page language="java" contentType="application/csv; charset=GBK"
	pageEncoding="GBK"%><%@ page import="com.newsclan.crud.*,java.sql.*"%><%
	int user_id=(Integer)session.getAttribute("user_id");
	if (!(
			Auth.isAdmin(user_id)||
			Auth.checkPrivilegeForRightOfTable(user_id, "", "report")
		))
		return;
	String sql = (String) session.getAttribute("sql");
    String name=(String) session.getAttribute("report_name");
	response.setContentType("binary/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename="
			+ new String((name+".csv").getBytes("gb2312"), "iso8859-1"));

	Field[] fds = DAO.getFields(sql);
	Connection conn = DB.getConnection();
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	for (int i = 0; i < fds.length; i++) {
		if (i > 0)
			out.print(",");
		out.print(fds[i].label);
	}
	out.println();
	while (rs.next()) {
		for (int i = 0; i < fds.length; i++) {
			if (i > 0)
				out.print(",");
			out.print("\"");
			String value=rs.getString(i + 1);
			if(value!=null&&value.length()>8)
				out.print("'");
			out.print(value+"\"");

		}
		out.println();

	}
	DB.close(conn);
%>