<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Select</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF8");
		String tbname = request.getParameter("tbname");
		String value=request.getParameter("value");
		String keyword=request.getParameter("keyword");
		String keyvalue=request.getParameter("keyvalue");
		String input_name=request.getParameter("input_name");
		
		if (tbname != null) {
			tbname=tbname.replace("`", "");
			if(!DAO.hasTable(tbname)&&DAO.hasTable(DAO.table_prefix+tbname))
				tbname=DAO.table_prefix+tbname;
			out.println(Tools.toSelect(tbname,value,keyword,keyvalue,input_name)); 
		} 
		
	%>
	
</body>
</html>
