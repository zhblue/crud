<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,com.newsclan.crud.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Select Report</title>
</head>
<link type="text/css" rel="stylesheet" href="../date/styles/main.css" />
<script src="../date/scripts/jquery.js"></script>
<script src="../date/scripts/eye-base.js"></script>
<script src="../date/scripts/eye-all.js"></script>
 
<body background="bg2.jpg" > 
<%
int user_id=(Integer)session.getAttribute("user_id");
if (!(
		Auth.isAdmin(user_id)||
		Auth.checkPrivilegeForRightOfTable(user_id, "", "report")
	))
	return;
%>
<%
String id=request.getParameter("id");

if(id==null){
	
	Connection conn = DB.getConnection();
	PreparedStatement pstmt;
	String selection="";
	try {
		pstmt = conn.prepareStatement("select * from config where name like '%报表'");
		// pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			selection+="<option value=\""+rs.getString("id")+"\">"+rs.getString("name")+"</option>\n";
		}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	DB.close(conn);
	
	%>
	<p align="center">
	<br><br><br><br><br>
	<form method=post onSubmit="return showReport(this)">
	<select type=text name=id >
	<%=selection%> 
	</select>
	<input onClick="eye.datePicker.show(this);" name=start value="<%=Tools.lastMonthFirstDay()%>">
	<input onClick="eye.datePicker.show(this);" name=end value="<%=Tools.lastMonthLastDay()%>">
	<input type=submit>
	</form>
	</p>
	<%	
}else{
	int iid=Integer.parseInt(id);
	String sql=DAO.queryString("select value from config where id="+iid);
	String name=DAO.queryString("select name from config where id="+iid);
	String start=request.getParameter("start");
	String end=request.getParameter("end");
	sql=sql.replace("START_DATE",start);
	sql=sql.replace("END_DATE",end);
	session.setAttribute("sql",sql);
	session.setAttribute("report_name", name+"_"+start+"-"+end);
	response.sendRedirect("report.jsp?"+Math.random());
}
%>
</body>
</html>