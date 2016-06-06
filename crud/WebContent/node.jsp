<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%><%
		request.setCharacterEncoding("UTF8");
		String tbname = request.getParameter("tbname");
		if(!"node".equals( tbname)) return;
		String sid = request.getParameter("id");
		int user_id=5;
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
				String data=request.getParameter("data");
				String mac=request.getParameter("mac");
				DAO.executeUpdate("update node set data=? where mac=?", data,mac);
				
				out.println(DAO.queryString("select color from node where mac=?",mac));
			} else { 
				if (DAO.insert(user_id,tbname, values) > 0)
					out.println("1000");
				else
					out.println("fail");
			}
		}%>
