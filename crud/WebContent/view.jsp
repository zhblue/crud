<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
		String tbname = request.getParameter("tbname");
		String sid = request.getParameter("id");
		int user_id=Tools.getUserId(session);
		int id = -1;
		if (sid != null) {
			try {
				id = Integer.parseInt(sid);
			} catch (Exception e) {
			//	e.printStackTrace();
			}
		}
	
		tbname = request.getParameter("tb");
		if (tbname == null)
			return;

		tbname = Tools.toHTML(tbname);
	%>
		
		<%
		System.err.println(id);
			if (id == -1) {
		%> 
		<%=Tools.toTable(DAO.getForm(user_id,tbname, false),
						"table table-striped table-hover")%>
		<% 
			} else {
				List<List> values=DAO.queryList("select * from `"+Config.sysPrefix+tbname+"` where "+DAO.getPrimaryKeyFieldName(tbname)+"=?", false, String.valueOf(id));
				List<String> value=null;
				if(values.size()>0){
					value=values.get(0);
					value.remove(0);
				}
				
		%><input id=data_id type=hidden name=id value="<%=id%>">
		<%=Tools.toTable(DAO.getView(user_id,tbname, false,value),
						"table table-striped table-hover")%>
		<%
 			} 
		%>
		
<img width="150px" height="150px" id="qc" src="http://qr.liantu.com/api.php?text=x"/>
<script>
   //alert(document.getElementById("qc").src);
   document.getElementById("qc").src="http://qr.liantu.com/api.php?text="+
		   encodeURIComponent(window.location.href);
   
</script>
</body>
</html>
