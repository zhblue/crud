<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择文件</title>
</head>
<body>
	<%
	int user_id=-1;
	try{
		user_id=Tools.getUserId(session);
		if(!Auth.canUploadFile(user_id)) return;
	}catch(Exception e){return;}
	request.setCharacterEncoding("UTF8");
	String input_name=request.getParameter("input_name");
	
	%>
<form style="margin:0px;display: inline" method="post" action="ckeditor/uploader/upload.jsp?input_name=<%=Tools.toHTML(input_name)%>" enctype='multipart/form-data'>
<span style="white-space:nowarp"><input type="file" name="upload"><input type="submit" value="上传"></span>
</form>	
</body>
</html>
