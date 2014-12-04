<%@ page import="com.newsclan.crud.*,java.sql.*"%>
<%
	String username, passwd, sql;
	Connection conn=DB.getConnection();
	username = request.getParameter("username");
	passwd = request.getParameter("passwd");
	String rand = request.getParameter("rand");
	if (rand != null && rand.equals(session.getAttribute("rand"))){
 
		if (Tools.login(username, passwd)) {
			sql = "SELECT  * from user where name=? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				session.setAttribute("user_id", rs.getString("id"));
				session.setAttribute("user_name", username);
			}
			sql = "SELECT  `right` from privilege where user_id=?";
			int user_id = rs.getInt("id");
			DB.close(rs);
			DB.close(pstmt);

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				session.setAttribute(rs.getString(1), true);
			}
			DB.close(rs);
			DB.close(pstmt);
			response.sendRedirect("main.jsp");
		}else{
			session.invalidate();
			response.sendRedirect("../index.jsp");
		}
	} else {
		session.invalidate();
		response.sendRedirect("../index.jsp");
	}

	DB.close(conn);
%>
