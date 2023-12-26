<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="checkLogin.jsp"%>
<%@ page import="com.newsclan.crud.*,java.sql.*"%>
<html>
<body>

	<div width=80%>

		<%
			int user_id = (Integer) session.getAttribute("user_id");
			if (!(Auth.isAdmin(user_id) || Auth.checkPrivilegeForRightOfTable(user_id, "", "report")))
				return;

			String sql = (String) session.getAttribute("sql");
			String spage = request.getParameter("page");
			if (spage == null)
				spage = "0";
			int ipage = Integer.parseInt(spage);
			String report_name = (String) session.getAttribute("report_name");
			if (report_name != null)
				out.println(report_name);
		%>
		<a class='btn btn-success' target=_blank
			href="export.jsp?<%=Math.random()%>">导出</a>
		<%
			if (ipage > 0) {
		%>
		<a class='btn' href="#"
			onclick="$('#main').load('report.jsp?page=<%=(ipage - 1)%>',null,reformatform);">上一页</a>
		<%
			}
		%>
		<a class='btn' href="#"
			onclick="$('#main').load('report.jsp?page=<%=(ipage + 1)%>',null,reformatform);">下一页</a>
		<a class='btn' href="#"
			onclick="$('#main').load('report.jsp?page=<%=(ipage)%>',null,reformatform);">刷新</a>

		<table class="table table-striped">

			<%
 if (sql.contains("order by")) {
                                        sql += " limit " + (ipage * 16) + ",16";
                                } else if(sql.contains("select")){
                                        sql += " order by id desc limit " + (ipage * 16) + ",16";
                                }
                                Connection conn = DB.getConnection();
                                Statement stmt = conn.createStatement();
                                //stmt.execute("set names utf8;");
                                if(sql.contains("delete")||sql.contains("update")){
                                        stmt.executeUpdate(sql);
                                }else{
                                        Field[] fds = DAO.getFields(sql);
                                        String tbname = fds[0].table;
                                        ResultSet rs = stmt.executeQuery(sql);
                                        out.print("<tr>");
                                        for (int i = 0; i < fds.length && i < 20; i++) {
                                                out.print("<th>");
                                                out.print(fds[i].label);
                                                out.println("</th>");

                                        }
	
					String value = "";
					out.println("</tr>");
					while (rs.next()) {
						out.print("<tr>");
	
						for (int i = 0; i < fds.length && i < 20; i++) {
							value = rs.getString(i + 1);
							out.println("<td>");
							if (tbname.equals(fds[i].table)) {
								StringBuilder sb = new StringBuilder();
								sb.append("<span class='modifiable'");
								sb.append("tb='");
								sb.append(fds[i].table);
								sb.append("' fd='");
								sb.append(fds[i].name);
								sb.append("' rid='");
								if (value == null) {
									sb.append("");
								} else {
									sb.append(rs.getObject(1));
								}
								sb.append("' >");
								if (value == null)
									sb.append("_");
								else
									sb.append(value);
								sb.append("</span>");
								out.println(sb.toString());
	
							} else {
	
								
								if (value != null)
									out.print(value);
	
								
							}
							out.println("</td>");
						}
						
						out.println("</tr>");
						out.println();
	
					}
				%>
				<script>
						document.title="<%=report_name%>";
						tableName="<%=tbname%>";
						$(document).ready(function(){
							reformatform();
						});
					
				</script>
				<%
				}
				DB.close(conn);
			%>
		</table>
	</div>

</body>
</html>
