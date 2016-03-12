<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,java.util.*,com.newsclan.crud.*"%>
<%@ include file="checkLogin.jsp" %>
<%
	request.setCharacterEncoding("UTF-8");
	String orderNum = request.getParameter("orderNum");
	String sql="update t_order set orderStatus='已通关' where orderStatus='已支付' and orderNum=?";
	DAO.executeUpdate(sql, new String[]{orderNum});
	

%>
