<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Del</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF8");
		String tbname = request.getParameter("tbname");
		int user_id=(Integer)session.getAttribute("user_id");
		if (tbname != null) {
			tbname=tbname.replace("`", "");
			String id=request.getParameter("id");
			if(Auth.canDeleteTable(user_id, tbname))
				DAO.update("delete from `"+tbname+"` where id=?", id);
		}
		%>
</body>
</html>
