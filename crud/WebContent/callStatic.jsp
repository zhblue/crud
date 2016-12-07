<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*,java.lang.reflect.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List</title>
</head> 
<body>
<%
request.setCharacterEncoding("UTF-8");
String className=request.getParameter("c");
String methodName=request.getParameter("m");
int user_id=(Integer)session.getAttribute("user_id");
if(Auth.isAdmin(user_id)||Auth.checkPrivilegeForRightOfTable(user_id, className, methodName)){
	Class c=Class.forName(className);
	Method m=null;
	
	try{ 
		m=c.getMethod(methodName);
		m.invoke(null);
	}catch(NoSuchMethodException e) {
		m=c.getMethod(methodName, HttpServletRequest.class);
		Object ret=m.invoke(null,request);
		if(ret!=null){
			out.println(ret.toString());
		}
	}
	
	
	
}

%>
</body>
</html>