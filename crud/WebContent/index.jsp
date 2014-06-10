<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.newsclan.crud.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<link rel=stylesheet href='bs/css/bootstrap.min.css' type='text/css'>
<script src="bs/js/bootstrap.js"></script>
<script src="jq/jquery.min.js"></script>
</head>
<body>


<nav class="navbar navbar-default" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <a class="navbar-brand" href="#"><%=Config.get("system.name") %></a>
  </div>
  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse" >
    <ul class="nav navbar-nav navbar-left">
      <%
      	List<String> tables=com.newsclan.crud.DAO.getTables();
		Iterator<String> it=tables.iterator();
		while(it.hasNext()){
			String table=it.next();
			out.println("<li><a href=\"#"+table+"\" onclick='mainLoad(\""+table+"\")'>"
			+DAO.translate(table)+"</a></li>");
		}
      %>
     
    </ul>
   
  </div><!-- /.navbar-collapse -->
</nav>

<div id=main class="container"></div>
	
</body>
<script>
	function mainLoad(tbname){
		$("#main").load("list.jsp?tb="+tbname);
		
	}

</script>
</html>