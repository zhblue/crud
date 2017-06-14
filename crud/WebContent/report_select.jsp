<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,com.newsclan.crud.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Select Report</title>
</head>
<link type="text/css" rel="stylesheet" href="../date/styles/main.css" />

 
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
		pstmt = conn.prepareStatement("select * from "+Config.sysPrefix+"config where type='report' ");
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
	<input class="input_date" name=start value="<%=Tools.today()%>">
	<input class="input_date" name=end value="<%=Tools.tomorrow()%>">
	<input class="text" name=filter  >
	<input type=submit>
	</form>
	</p>
	<%	
}else{
	int iid=Integer.parseInt(id); 
	String sql=DAO.queryString("select value from "+Config.sysPrefix+"config where id=?",iid);
	String name=DAO.queryString("select name from "+Config.sysPrefix+"config where id=?",iid);
	String start=request.getParameter("start");
	String end=request.getParameter("end");
	String filter=request.getParameter("filter");
	if(null!=start)sql=sql.replace("START_DATE",start);else start="";
	if(null!=end)sql=sql.replace("END_DATE",end);else end="";
	if(null!=filter)sql=sql.replace("FILTER",filter);else end="";
	sql=sql.replace("USER_ID",String.valueOf(session.getAttribute("user_id")));
	sql=sql.replace("USER_NAME",(String)session.getAttribute("user_name"));
	
	session.setAttribute("sql",sql);
	session.setAttribute("report_name", name+"_"+start+"-"+end);
	response.sendRedirect("report.jsp?"+Math.random());
}
%> 
</body>
</html>