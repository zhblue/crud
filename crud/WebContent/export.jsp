<%@ page language="java" contentType="application/csv; charset=GBK"
	pageEncoding="GBK"%><%@ page import="com.newsclan.crud.*,java.sql.*,jxl.*,jxl.write.*,java.io.*"%><%
	int user_id=(Integer)session.getAttribute("user_id");
	if (!(
			Auth.isAdmin(user_id)||
			Auth.checkPrivilegeForRightOfTable(user_id, "", "report")
		))
		return;
	String sql = (String) session.getAttribute("sql");
    String name=(String) session.getAttribute("report_name");
	response.setContentType("binary/octet-stream");
	response.setHeader("Content-Disposition", "attachment; filename="
			+ new String((name+".xls").getBytes("gb2312"), "iso8859-1"));
	System.out.println(sql);
	Field[] fds = DAO.getFields(sql);
	Connection conn = DB.getConnection();
	OutputStream os=response.getOutputStream();
	WritableWorkbook book=Workbook.createWorkbook(os); 
	WritableSheet sheet=book.createSheet(name, 0);
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery(sql);
	for (int i = 0; i < fds.length; i++) {
// 		if (i > 0)
// 			out.print(",");
// 		out.print(fds[i].label);
		Label c=new Label(i,0,fds[i].label);
		sheet.addCell(c);
	}
//	out.println();
	int j=0;
	while (rs.next()) {
		
		for (int i = 0; i < fds.length; i++) {
// 			if (i > 0)
// 				out.print(",");
// 			out.print("\"");
 			String value=rs.getString(i + 1);
// 			if(value!=null&&value.length()>8)
// 				out.print("'");
// 			out.print(value+"\"");
			
			Label c=new Label(i,j+1,value);
			sheet.addCell(c);
		}
		j++;
	//	out.println();

	}
	book.write();
	book.close();
	os.close();
	DB.close(conn);
	
%>