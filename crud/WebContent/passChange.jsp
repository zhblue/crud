<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.newsclan.crud.*,java.sql.*"%>
<%@ include file="checkLogin.jsp" %>
<%if(session.getAttribute("user_id")==null) return;%>
<link rel=stylesheet href='bootstrap/css/bootstrap.min.css' type='text/css'>
<link rel=stylesheet href='jsp/mis.css' type='text/css'>
<body background="jsp/bg2.jpg" >
<script src='js/level_active.js'></script>
<%
request.setCharacterEncoding("UTF-8");
String id=null,name=null,password=null;
id=request.getParameter("id");

Connection conn=DB.getConnection();
%>
<%

if (request.getMethod().equals("POST")) {

	name=request.getParameter("name");
	password=request.getParameter("password");
	String sql="update user set`password`=? where id="+session.getAttribute("user_id");
	PreparedStatement pstmt=conn.prepareStatement(sql);

	pstmt.setString(1,Tools.getHash(password, Tools.getRandomSalt()));

	pstmt.executeUpdate();

    pstmt.close();

DB.close(conn);

response.sendRedirect( "passChange.jsp");

}else{%>
<%
String sql="select * from `user` where user.id="+session.getAttribute("user_id");

Statement stmt=conn.createStatement();

ResultSet rs=stmt.executeQuery(sql);
if(rs.next()){
	id=rs.getString("id");
	name=rs.getString("name");
	password=rs.getString("password");
}%>
<form method=post><table class='table-striped table-condensed'>
<thead>
<tr><th class=toprow colspan=3>用户</th></tr></thead>
<tr><td>名称</td> 
	<td><input style='height:32px' disabled type=text name="name"  value="<%=Tools.shortString(name,0) %>">
	</td>
</tr>
<tr><td>密码</td>
	<td><input style='height:32px' type=text name="password"  value="">
	</td>
</tr>
<tr><td><input  class='btn' type=submit value='确定'></td>	<td><input class='btn' type=reset></td></tr>
</table></form>
<%
 DB.close(stmt);


DB.close(conn);
}%>