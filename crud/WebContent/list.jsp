<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
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
int pageNum=Tools.getRequestInt(request,"pageNum");
String tbname=request.getParameter("tb").replace("`", "");
String keyword=request.getParameter("keyword");
%>
<input id='tbname' type=hidden value='<%=Tools.toHTML(request.getParameter("tb").replace("'", "")) %>' >
<%=Tools.toHTML(DAO.translate(tbname)) %>
<a href="javascript:pageUp('<%=tbname %>',<%=pageNum %>);" >上一页</a> 
<a href="javascript:pageDown('<%=tbname %>',<%=pageNum %>);" >下一页</a> 
<script></script>

<%
 List data=null;
 int user_id=Tools.getUserId(session);
 if(keyword!=null&&!keyword.equals("")){
	 System.out.format("keyword=%s\n",keyword);
	data=DAO.getList(user_id, tbname,keyword,pageNum,Config.pageSize);
 }else{
	data=DAO.getList(user_id, tbname,pageNum,Config.pageSize);
 }
%>
<%=Tools.toTable(data,"table table-striped table-hover") %>	
</body>
</html>