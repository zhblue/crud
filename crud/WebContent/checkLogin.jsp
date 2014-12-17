<%@page import="com.newsclan.crud.Config"%>
<%
if(Config.loginCheck&&session.getAttribute("user_id")==null){
	response.sendRedirect("login.jsp");
	return ;
}
%>