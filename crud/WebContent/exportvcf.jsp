<%@ page language="java" contentType="application/csv; charset=UTF-8"
        pageEncoding="UTF-8"%><%@ page import="com.newsclan.crud.*,java.sql.*"%><%
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
                        + new String((name+".vcf").getBytes("gb2312"), "iso8859-1"));

        Field[] fds = DAO.getFields(sql);
        Connection conn = DB.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
/*
BEGIN:VCARD

VERSION:3.0

FN:Ray

TEL;CELL;VOICE:15901320698

EMAIL;PREF;INTERNET:nuoone@163.com

URL:http://www.geek-era.com

ROLE:技术部

TITLE:工程师

ADR;WORK;POSTAL:北京市丰台区丰台科技园;100101

END:VCARD
*/
out.println("BEGIN:VCARD");
out.println("VERSION:3.0");
out.println("FN;CHARSET=UTF-8:"+rs.getString("name")+"");
out.println("TEL;CELL;VOICE:"+rs.getString("phone")+"");
out.println("EMAIL;PREF;INTERNET:"+rs.getString("qq")+"@qq.com");
out.print("NOTE;CHARSET=UTF-8:"+rs.getString("xh")+"");
out.print(""+rs.getString("class")+"");
out.print(""+rs.getString("room")+"");
out.print(""+rs.getString("special").replace("\n","")+"");
out.print("["+rs.getString("home_contacter_relation_ship")+"]"+rs.getString("home_contacter")+rs.getString("home_contacter_tel")+"/["+rs.getString("home_contacter2_relation_ship")+"]"+rs.getString("home_contacter2")+rs.getString("home_contacter2_tel"));
out.println(""+rs.getString("home_address")+"");
out.println("END:VCARD");

        }
        DB.close(conn);
%>
