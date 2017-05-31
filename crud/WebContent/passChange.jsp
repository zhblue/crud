<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.newsclan.crud.*,java.sql.*"%>
<%@ include file="checkLogin.jsp" %>
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
	String sql="update "+Config.sysPrefix+"user set `password`=? where id=?";
	PreparedStatement pstmt=conn.prepareStatement(sql);
	//System.out.println(password);
	password=Tools.getHash(password, Tools.getRandomSalt());
	//System.out.println(password);
	pstmt.setString(1,password);
    pstmt.setInt(2,(Integer)session.getAttribute("user_id"));
	pstmt.executeUpdate();	
    pstmt.close();
	DB.close(conn);

response.sendRedirect( "index.jsp");

}else{%>
<%
String sql="select * from `"+Config.sysPrefix+"user` where id="+session.getAttribute("user_id");

Statement stmt=conn.createStatement();

ResultSet rs=stmt.executeQuery(sql);
if(rs.next()){
	id=rs.getString("id");
	name=rs.getString("name");
	password=rs.getString("password");
}%>
<form method=post action="passChange.jsp" onsubmit="return checkIfPasswordIsSame(this);">
<table class='table-striped table-condensed'>
<thead>
<tr><th class=toprow colspan=3>用户</th></tr></thead>
<tr><td>名称</td> 
	<td><input style='height:32px' disabled type=text name="name"  value="<%=Tools.shortString(name,0) %>">
	</td>
</tr>
<tr><td>密码</td>
	<td><input style='height:32px' id="password" type="password" name="password"  value="">
	</td>
</tr>
<tr><td>重复密码</td>
	<td><input style='height:32px' id="password2" type="password" name="password2"  value="">
	</td>
</tr>
<tr><td><input  class='btn' type=submit value='确定'></td>	<td><input class='btn' type=reset></td></tr>
</table></form>
<%
 DB.close(stmt);


DB.close(conn);
}%>