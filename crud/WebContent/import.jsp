<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="checkLogin.jsp" %>
<%@ page import="com.newsclan.crud.*,java.sql.*"%>
<html>
<link rel=stylesheet href='../bootstrap/css/bootstrap.min.css' type='text/css'>
<%
	int user_id=(Integer)session.getAttribute("user_id");
	if (!(
			Auth.isAdmin(user_id)
			||Auth.checkPrivilegeForRightOfTable(user_id, "", "import")
			
		))
		return;
	String path=request.getParameter("xls_file");
	if(path!=null){
		String realpath=pageContext.getServletContext().getRealPath(path);
		System.out.println(path);
		System.out.println(realpath);
		if(realpath!=null){
			Tools.importXLS(realpath); 
		}
	}
%>
<form id="frm_import" action="import.jsp" method="post" >
<table  width="82%">
<tr><td width="82%">
<input id="xls_file" name="xls_file" type="text" onLoad="loadFile($(this))">
</td></tr>
<tr><td>
<input type="button" onclick="do_import()" value="导入">
</td></tr>
</table>
</form>
<script type="text/javascript">
function do_import(){
	var data=$("#frm_import").serialize();
	$.post("import.jsp",data,new function(){
		 window.setTimeout("window.location.reload();",1000);
	});
	
}

</script>
</html>