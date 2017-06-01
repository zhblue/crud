<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.newsclan.crud.*"%>
<%@ include file="checkLogin.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<link rel=stylesheet href='bs/css/bootstrap.css' type='text/css'>
<script src="jq/jquery.min.js"></script>
<script src="bs/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="jq/css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="jq/css/jquery.wysiwyg.css" />
<link type="text/css" href="jq/css/jquery-ui-timepicker-addon.css" rel="stylesheet" />
     
<script type="text/javascript" src="jq/jquery-ui.min.js"></script>
<script type="text/javascript" src="jq/jquery-ui-datepicker.js"></script>
<script type="text/javascript" src="jq/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="jq/jquery-ui-timepicker-zh-CN.js"></script>


<script src="ckeditor/ckeditor.js"></script>
<script src="ckeditor/adapters/jquery.js"></script>

</head>
<body>


<div class="navbar navbar-default" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  
  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="navbar bs-navbar" >
    <a class="navbar-brand" href="." target='_blank'><%=Config.get("system.name") %></a>
      <%
      
      int user_id=Tools.getUserId(session);
      	List<String> tables=com.newsclan.crud.DAO.getTables(user_id);
		Iterator<String> it=tables.iterator(); 
		while(it.hasNext()){ 
			String table=it.next();
			out.println("<a class=\"btn navbar-brand\" href=\"#"+table+"\" onclick='mainLoad(\""+table+"\",0)'>"
			 +DAO.translate(table)+"</a>");
		}
      %>
      <%
      if(Auth.checkPrivilegeForRightOfTable(user_id, "", "report")||Auth.isAdmin(user_id)){
      %>
      	 <a class="btn navbar-brand" href="#" onclick='loadReport();'>报表</a>
      <%} %>
         <%
      if(Auth.checkPrivilegeForRightOfTable(user_id, "", "import")||Auth.isAdmin(user_id)){
      %>
      	 <a class="btn navbar-brand" href="#" onclick='loadImport();'>导入</a>
      <%} %>
    
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input placeholder="查找" class="nav-brand form-control" onkeyup="search(this.value)" type="text">
        </div>
        <input id="auto" type="checkbox" onclick="autoRefresh()">自动刷新
      </form>
       <%
     if (Auth.isAdmin(user_id)
			||Auth.checkPrivilegeForRightOfTable(user_id, "", "addTable")){
		%>
     	<a class="btn navbar-brand" href="#" onclick='addTable();'><span class='glyphicon glyphicon-plus'></span></a> 
     <%
     }
     %>
     <a class="btn navbar-brand" href="#passChange"  onclick='passChange();'><span class='glyphicon glyphicon-user'></span></a>
     <a class="btn navbar-brand" href="logout.jsp" ><span class='glyphicon glyphicon-log-out'></span></a>
   
  </div><!-- /.navbar-collapse -->
</div>

<div id="main" class="container"></div>


</body>
<script src="main.js?v=0.1"></script>
<script src="LodopFuncs.js"></script>
<object id="LODOP1" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
</object> 
<script type="text/javascript">
	var LODOP;
	function A4Print(){
		//alert(arguments.length);
		var baseline,i;
		var title=new Array("条码","代理号","寄件人姓名","寄件人电话","寄件人地址","收件人姓名","收件人地址","收件人邮编","收件人电话","备注信息","身份证号码","快递单号","内件品名");
		if(LODOP==null) LODOP=getLodop(LODOP1);
		LODOP.PRINT_INIT("A4面单打印");
		LODOP.SET_PRINT_STYLEA(2,"FontName","宋体");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
		for(baseline=0;baseline<1000;baseline+=513){
			LODOP.ADD_PRINT_RECT(baseline,55,600,1,0,1);
			LODOP.ADD_PRINT_IMAGE(baseline+15,15,120,120,"<img border='0' height=120 width=120 src='download/logo.png'>");
			LODOP.ADD_PRINT_BARCODE(baseline+33,314,300,80,"128Auto",arguments[0]);
			for(i=1;i<arguments.length;i++){
				LODOP.ADD_PRINT_TEXT(baseline+126+i*25,41,546,22,title[i]+"："+arguments[i]);
			}
		}
		LODOP.PREVIEW();
	}
</script>
</html>
