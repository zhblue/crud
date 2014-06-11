<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF8");
		String tbname = request.getParameter("tbname");
		if (tbname != null) {
			Enumeration<String> names=request.getParameterNames();
			Map <String,String>values=new HashMap<String,String>();
			while(names.hasMoreElements()){
				String name=names.nextElement();
				values.put(name,request.getParameter(name));
			}
			if(DAO.insert(tbname,values)>0)
				response.sendRedirect("list.jsp?tb="+tbname); 
			else
				out.println("fail");
		}
		tbname = request.getParameter("tb");
		if (tbname == null)
			return;

		tbname = Tools.toHTML(tbname);
	%>
	<form id=addForm action=add.jsp method=post>
		<input type=hidden name=tbname value="<%=tbname%>">
		<%=Tools.toTable(DAO.getForm(tbname, false),
					"table table-striped table-hover")%>
		<input id='buttonOK' class="btn" onclick="submitAdd('<%=tbname%>');"
			type=button value="确定">
	</form>
	
</body>
</html>
