<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.newsclan.crud.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List</title>
</head> 
<body>
<%
int pageNum=Tools.getRequestInt(request,"pageNum");
String tbname=request.getParameter("tb").replace("`", "");
%>
<input id='tbname' type=hidden value='<%=Tools.toHTML(request.getParameter("tb").replace("'", "")) %>' >
<%=Tools.toHTML(DAO.translate(tbname)) %>
<a href="javascript:pageUp('<%=tbname %>',<%=pageNum %>);" >上一页</a> 
<a href="javascript:pageDown('<%=tbname %>',<%=pageNum %>);" >下一页</a> 

<%=Tools.toTable(DAO.getList((Integer)session.getAttribute("user_id"), tbname,pageNum,Config.pageSize),"table table-striped table-hover") %>	
</body>
</html>