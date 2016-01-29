<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add/Edit</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF8");
		String tbname = request.getParameter("tbname");
		String sid = request.getParameter("id");
		int user_id=Tools.getUserId(session);
		int id = -1;
		if (sid != null) {
			try {
				id = Integer.parseInt(sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (tbname != null) {
			tbname = tbname.replace("`", "");

			Enumeration<String> names = request.getParameterNames();
			Map<String, String> values = new HashMap<String, String>();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				values.put(name, request.getParameter(name));
			}
			if (sid != null && id != -1) {
				if (DAO.update(user_id,tbname, id, values) ==0)
					DAO.insert(user_id,tbname, values);
				response.sendRedirect("list.jsp?tb=" + tbname);
			} else { 
				if (DAO.insert(user_id,tbname, values) > 0)
					response.sendRedirect("list.jsp?tb=" + tbname);
				else
					out.println("fail");
			}
		}
		tbname = request.getParameter("tb");
		if (tbname == null)
			return;

		tbname = Tools.toHTML(tbname);
	%>
	<form id=addForm action=add.jsp method=post>
		
		<input type=hidden name=tbname value="<%=tbname%>">
		<%
		System.err.println(id);
			if (id == -1) {
		%> 
		<%=Tools.toTable(DAO.getForm(user_id,tbname, false),
						"table table-striped table-hover")%>
		<% 
			} else {
				List<List> values=DAO.queryList("select * from `"+tbname+"` where "+DAO.getPrimaryKeyFieldName(tbname)+"=?", false, String.valueOf(id));
				List<String> value=null;
				if(values.size()>0){
					value=values.get(0);
					value.remove(0);
				}
				
		%><input type=hidden name="id" value="<%=id%>">
		<%=Tools.toTable(DAO.getForm(user_id,tbname, false,value),
						"table table-striped table-hover")%>
		<%
 			}
		%>
		<input id='buttonOK' class="btn" onclick="submitAdd('<%=tbname%>');"
			type=button value="确定">
	</form>

</body>
</html>
