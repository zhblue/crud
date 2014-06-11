<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.newsclan.crud.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add</title>
</head>
<body>
<%
	String tbname=request.getParameter("tb");
	tbname=tbname.replace("'", "");
	

%>
<%=Tools.toTable(DAO.getForm(tbname,false),"table table-striped table-hover") %>
</body>
</html> 