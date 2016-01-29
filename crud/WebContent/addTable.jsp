<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<%
	if(Auth.isAdmin((Integer)session.getAttribute("user_id"))){
		request.setCharacterEncoding("UTF-8");
		String tb_name = request.getParameter("tb_name");
		String tb_title = request.getParameter("tb_title");
		String[] fd_names = request.getParameterValues("fd_name");
		String[] fd_types = request.getParameterValues("fd_type");
		String[] fd_titles = request.getParameterValues("fd_title");
		Tools.addTable(tb_name,tb_title, fd_names, fd_types, fd_titles);
	}
	%> 
</body>
</html>