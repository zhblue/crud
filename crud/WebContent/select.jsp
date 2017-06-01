<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<%@ include file="checkLogin.jsp" %><%
		request.setCharacterEncoding("UTF-8");
		String tbname = request.getParameter("tbname");
		String value=request.getParameter("value");
		String keyword=request.getParameter("keyword");
		String keyvalue=request.getParameter("keyvalue");
		String input_name=new String(request.getParameter("input_name").getBytes("ISO8859_1"),"UTF-8");
		
		if (tbname != null) {
			tbname=tbname.replace("`", "");
			if(!DAO.hasTable(tbname)&&DAO.hasTable(DAO.table_prefix+tbname))
				tbname=DAO.table_prefix+tbname;
			out.println(Tools.toSelect(tbname,value,keyword,keyvalue,input_name)); 
		} 
		
	%>
