<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*,java.lang.reflect.*"%><%@ include file="checkLogin.jsp" %><%
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
		try{
			m=c.getMethod(methodName, HttpServletRequest.class);
			Object ret=m.invoke(null,request);
			if(ret!=null){
				out.println(ret.toString());
			}
		}catch(NoSuchMethodException e2) {
			m=c.getMethod(methodName, Long.class);
			Object ret=m.invoke(null,Long.parseLong(request.getParameter("id")));
			if(ret!=null){
				out.println(ret.toString());
			}
		}	
	}
}
%>
