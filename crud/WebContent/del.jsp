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
			int id=Integer.parseInt(request.getParameter("id"));
			String class_id="-1";
			if("t_student_class".equals(tbname)){
				class_id=DAO.queryString("select t_class_id from t_student_class where id=?",id);
				
			}
			
			if(Auth.canDeleteTableById(user_id, tbname,id))
				DAO.update("delete from `"+tbname+"` where id=?", id);
			if("t_student_class".equals(tbname)){		
				String selected=DAO.queryString("select count(1) from t_student_class where t_class_id=?", class_id);
				DAO.executeUpdate("update t_class set selected=? where id=?", selected,class_id);	
			}
			
		}
		%> 
</body>
</html>
