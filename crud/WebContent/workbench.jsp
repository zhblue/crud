<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.newsclan.crud.*,java.util.*"%>
<%@ include file="checkLogin.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<title>工作台</title>
<link rel=stylesheet href='bs/css/bootstrap.css' type='text/css'>
<script src="jq/jquery.min.js"></script>
<script src="filesaver.min.js"></script>
<script src="bs/js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="jq/css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="jq/css/jquery.wysiwyg.css" />
<link type="text/css" href="jq/css/jquery-ui-timepicker-addon.css"
	rel="stylesheet" />

<script type="text/javascript" src="jq/jquery-ui.min.js"></script>
<script type="text/javascript" src="jq/jquery-ui-datepicker.js"></script>
<script type="text/javascript" src="jq/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="jq/jquery-ui-timepicker-zh-CN.js"></script>


<script src="ckeditor/ckeditor.js"></script>
<script src="ckeditor/adapters/jquery.js"></script>
<style>
#subtitle {
	width: 200px;
	height: 30px;
	padding: 0.5em;
	background-color: rgba(0, 0, 0, 0.4);
	color: rgb(255, 255, 255);
	position: absolute;
	left: 155px;
	top: 300px;
	text-align: left;
	font-weight: bolder;
}

#preview, #mapper {
	margin: 0px;
	position: absolute;
	left: 0px;
	top: 0px;
}

#preview {
	z-index: 0;
}

#mapper {
	background-color: rgba(55, 0, 0, 0);
	cursor: crosshair;
	z-index: 100;
}

#XY {
	width: 60px;
	height: 30px;
	padding: 0.5em;
	background-color: rgba(0, 0, 0, 0);
	color: rgb(255, 255, 255);
	position: absolute;
	left: 155px;
	top: 0px;
}
</style>
</head>
<%


request.setCharacterEncoding("UTF-8");
String tbname = request.getParameter("tb");
String sid = request.getParameter("id");


int user_id=Tools.getUserId(session);
int id = -1;
if (sid != null) {
	try {
		id = Integer.parseInt(sid);
	} catch (Exception e) {
	//	e.printStackTrace();
	}
}
String movie_id=DAO.queryString("select tb_movie_id from tb_manual_task where id=?", id);
System.out.println("movie id:"+movie_id);
String video_url=DAO.queryString("select movie_file from tb_movie where id=?", movie_id);

System.out.println("video_url:"+video_url);
%>
<body>



	<div id="main" class="container">

		<div class="row" style="height: 340px">
			<div id="zero"
				class="col-md-6 label-default align-middle center embed-responsive embed-responsive-16by9"
				style="text-align: center; height: 100%;">
				<video id="preview" src="<%=video_url %>"
					controls='true'
					style="height:100%;margin:5px;vertical-align: middle;"
					onfocus="useShort=false;" onblur="useShort=true"> </video>
				<div id="mapper"></div>
				<div id="subtitle">当前标注</div>
				<div id="XY">X,Y</div>

			</div>
			<div class="col-md-6 label-info pre-scrollable" style="height: 100%">
				<table class="table well">
					<thead>
						<tr>

							<th scope="col col-md-1" style="width: 125px">开始</th>
							<th scope="col col-md-1" style="width: 125px">结束</th>
							<th scope="col" style="width: 200px">标签</th>
							<th scope="col">删除</th>
						</tr>
					</thead>
					<tbody id="tagList">
						<!--    测试数据   -->
						<tr start_time="00:00:03,600" end_time="00:00:06,739" tagtext="交谈"
							x="76" y="25" w="176" h="110">
							<td><button class="btn" onclick="jump($(this).text())">00:00:03,600</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:06,739</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">交谈</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:07,942" end_time="00:00:12,957"
							tagtext="伪3D" x="21" y="25" w="166" h="161">
							<td><button class="btn" onclick="jump($(this).text())">00:00:07,942</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:12,957</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:13,157" end_time="00:00:18,536" tagtext="爆炸"
							x="39" y="54" w="283" h="173">
							<td><button class="btn" onclick="jump($(this).text())">00:00:13,157</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:18,536</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">爆炸</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:19,602" end_time="00:00:24,885"
							tagtext="伪3D" x="87" y="62" w="267" h="162">
							<td><button class="btn" onclick="jump($(this).text())">00:00:19,602</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:24,885</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:25,451" end_time="00:00:30,743" tagtext="地图"
							x="154" y="46" w="285" h="203">
							<td><button class="btn" onclick="jump($(this).text())">00:00:25,451</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:30,743</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:31,449" end_time="00:00:33,294" tagtext="地图"
							x="89" y="104" w="313" h="175">
							<td><button class="btn" onclick="jump($(this).text())">00:00:31,449</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:33,294</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:33,523" end_time="00:00:42,390" tagtext="卡通"
							x="207" y="122" w="192" h="94">
							<td><button class="btn" onclick="jump($(this).text())">00:00:33,523</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:42,390</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">卡通</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:43,439" end_time="00:00:48,994" tagtext="赛车"
							x="204" y="106" w="220" h="86">
							<td><button class="btn" onclick="jump($(this).text())">00:00:43,439</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:48,994</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">赛车</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:52,434" end_time="00:00:55,026" tagtext="3D"
							x="95" y="100" w="210" h="144">
							<td><button class="btn" onclick="jump($(this).text())">00:00:52,434</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:00:55,026</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:00:55,816" end_time="00:01:00,980" tagtext="射击"
							x="174" y="100" w="168" h="173">
							<td><button class="btn" onclick="jump($(this).text())">00:00:55,816</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:00,980</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<!--    测试数据   -->
						<tr start_time="00:01:01,347" end_time="00:01:06,870" tagtext="地图"
							x="23" y="64" w="372" h="197">
							<td><button class="btn" onclick="jump($(this).text())">00:01:01,347</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:06,870</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:07,882" end_time="00:01:08,578" tagtext="射击"
							x="138" y="95" w="202" h="169">
							<td><button class="btn" onclick="jump($(this).text())">00:01:07,882</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:08,578</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:13,804" end_time="00:01:18,672" tagtext="赛车"
							x="127" y="115" w="229" h="117">
							<td><button class="btn" onclick="jump($(this).text())">00:01:13,804</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:18,672</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">赛车</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:19,537" end_time="00:01:21,939" tagtext="迷宫"
							x="308" y="89" w="96" h="144">
							<td><button class="btn" onclick="jump($(this).text())">00:01:19,537</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:21,939</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">迷宫</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:31,257" end_time="00:01:36,833" tagtext="卡通"
							x="123" y="62" w="195" h="154">
							<td><button class="btn" onclick="jump($(this).text())">00:01:31,257</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:36,833</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">卡通</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:38,274" end_time="00:01:39,487" tagtext="地图"
							x="99" y="67" w="251" h="200">
							<td><button class="btn" onclick="jump($(this).text())">00:01:38,274</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:39,487</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:43,209" end_time="00:01:48,972" tagtext="格斗"
							x="229" y="93" w="210" h="177">
							<td><button class="btn" onclick="jump($(this).text())">00:01:43,209</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:48,972</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">格斗</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:50,028" end_time="00:01:50,403"
							tagtext="伪3D" x="146" y="6" w="231" h="165">
							<td><button class="btn" onclick="jump($(this).text())">00:01:50,028</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:50,403</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:51,392" end_time="00:01:54,829" tagtext="格斗"
							x="9" y="100" w="410" h="174">
							<td><button class="btn" onclick="jump($(this).text())">00:01:51,392</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:54,829</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">格斗</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:01:55,717" end_time="00:01:59,360" tagtext="格斗"
							x="270" y="120" w="194" h="107">
							<td><button class="btn" onclick="jump($(this).text())">00:01:55,717</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:01:59,360</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">格斗</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:01,517" end_time="00:02:04,254" tagtext="射击"
							x="151" y="143" w="169" h="131">
							<td><button class="btn" onclick="jump($(this).text())">00:02:01,517</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:04,254</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:08,025" end_time="00:02:08,903" tagtext="飞机"
							x="313" y="182" w="157" h="95">
							<td><button class="btn" onclick="jump($(this).text())">00:02:08,025</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:08,903</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:10,017" end_time="00:02:11,017" tagtext="飞机"
							x="328" y="278" w="0" h="0">
							<td><button class="btn" onclick="jump($(this).text())">00:02:10,017</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:11,017</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:11,467" end_time="00:02:12,871" tagtext="飞机"
							x="81" y="242" w="185" h="35">
							<td><button class="btn" onclick="jump($(this).text())">00:02:11,467</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:12,871</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:13,065" end_time="00:02:13,071" tagtext="飞机"
							x="300" y="17" w="175" h="106">
							<td><button class="btn" onclick="jump($(this).text())">00:02:13,065</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:13,071</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:13,098" end_time="00:02:16,868" tagtext="射击"
							x="95" y="121" w="353" h="133">
							<td><button class="btn" onclick="jump($(this).text())">00:02:13,098</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:16,868</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:19,580" end_time="00:02:21,935" tagtext="地图"
							x="103" y="54" w="336" h="216">
							<td><button class="btn" onclick="jump($(this).text())">00:02:19,580</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:21,935</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:22,539" end_time="00:02:24,961" tagtext="卡通"
							x="27" y="89" w="249" h="142">
							<td><button class="btn" onclick="jump($(this).text())">00:02:22,539</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:24,961</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">卡通</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:25,446" end_time="00:02:28,255" tagtext="射击"
							x="132" y="109" w="162" h="113">
							<td><button class="btn" onclick="jump($(this).text())">00:02:25,446</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:28,255</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:28,521" end_time="00:02:29,806" tagtext="射击"
							x="350" y="115" w="96" h="75">
							<td><button class="btn" onclick="jump($(this).text())">00:02:28,521</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:29,806</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:29,990" end_time="00:02:30,904" tagtext="燃烧"
							x="275" y="92" w="86" h="80">
							<td><button class="btn" onclick="jump($(this).text())">00:02:29,990</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:30,904</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">燃烧</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:31,578" end_time="00:02:36,999" tagtext="卡通"
							x="18" y="123" w="365" h="118">
							<td><button class="btn" onclick="jump($(this).text())">00:02:31,578</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:36,999</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">卡通</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:37,327" end_time="00:02:39,688" tagtext="飞机"
							x="207" y="219" w="116" h="51">
							<td><button class="btn" onclick="jump($(this).text())">00:02:37,327</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:39,688</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:39,947" end_time="00:02:40,428" tagtext="飞机"
							x="98" y="212" w="118" h="62">
							<td><button class="btn" onclick="jump($(this).text())">00:02:39,947</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:40,428</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:40,515" end_time="00:02:40,883" tagtext="飞机"
							x="236" y="213" w="121" h="61">
							<td><button class="btn" onclick="jump($(this).text())">00:02:40,515</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:40,883</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:41,002" end_time="00:02:42,525" tagtext="飞机"
							x="98" y="196" w="188" h="81">
							<td><button class="btn" onclick="jump($(this).text())">00:02:41,002</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:42,525</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:42,577" end_time="00:02:43,002" tagtext="飞机"
							x="269" y="205" w="128" h="59">
							<td><button class="btn" onclick="jump($(this).text())">00:02:42,577</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:43,002</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:43,251" end_time="00:02:47,888" tagtext="地图"
							x="47" y="65" w="343" h="195">
							<td><button class="btn" onclick="jump($(this).text())">00:02:43,251</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:47,888</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:50,722" end_time="00:02:50,774" tagtext="射击"
							x="181" y="156" w="0" h="0">
							<td><button class="btn" onclick="jump($(this).text())">00:02:50,722</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:50,774</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:51,268" end_time="00:02:52,351" tagtext="射击"
							x="140" y="101" w="230" h="162">
							<td><button class="btn" onclick="jump($(this).text())">00:02:51,268</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:52,351</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:02:58,497" end_time="00:02:58,801" tagtext="地图"
							x="240" y="124" w="183" h="151">
							<td><button class="btn" onclick="jump($(this).text())">00:02:58,497</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:02:58,801</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:02,616" end_time="00:03:06,987" tagtext="射击"
							x="257" y="203" w="85" h="72">
							<td><button class="btn" onclick="jump($(this).text())">00:03:02,616</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:06,987</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:10,608" end_time="00:03:11,194" tagtext="射击"
							x="269" y="212" w="171" h="63">
							<td><button class="btn" onclick="jump($(this).text())">00:03:10,608</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:11,194</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:08,812" end_time="00:03:11,194" tagtext="射击"
							x="183" y="197" w="437" h="273">
							<td><button class="btn" onclick="jump($(this).text())">00:03:08,812</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:11,194</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:11,281" end_time="00:03:11,318" tagtext="射击"
							x="425" y="271" w="0" h="0">
							<td><button class="btn" onclick="jump($(this).text())">00:03:11,281</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:11,318</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:12,050" end_time="00:03:12,393" tagtext="射击"
							x="277" y="205" w="138" h="69">
							<td><button class="btn" onclick="jump($(this).text())">00:03:12,050</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:12,393</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:13,818" end_time="00:03:18,670" tagtext="飞机"
							x="68" y="43" w="211" h="171">
							<td><button class="btn" onclick="jump($(this).text())">00:03:13,818</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:18,670</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">飞机</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:19,695" end_time="00:03:24,126" tagtext="地图"
							x="239" y="51" w="178" h="208">
							<td><button class="btn" onclick="jump($(this).text())">00:03:19,695</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:24,126</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:25,629" end_time="00:03:30,471" tagtext="赛车"
							x="161" y="112" w="258" h="124">
							<td><button class="btn" onclick="jump($(this).text())">00:03:25,629</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:30,471</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">赛车</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:31,544" end_time="00:03:32,228" tagtext="射击"
							x="125" y="153" w="265" h="82">
							<td><button class="btn" onclick="jump($(this).text())">00:03:31,544</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:32,228</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:33,709" end_time="00:03:34,234" tagtext="射击"
							x="269" y="162" w="71" h="58">
							<td><button class="btn" onclick="jump($(this).text())">00:03:33,709</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:34,234</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:34,472" end_time="00:03:37,056" tagtext="射击"
							x="140" y="158" w="104" h="72">
							<td><button class="btn" onclick="jump($(this).text())">00:03:34,472</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:37,056</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:39,125" end_time="00:03:43,176" tagtext="爆炸"
							x="112" y="244" w="59" h="35">
							<td><button class="btn" onclick="jump($(this).text())">00:03:39,125</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:43,176</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">爆炸</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:43,459" end_time="00:03:44,731" tagtext="交谈"
							x="64" y="8" w="316" h="93">
							<td><button class="btn" onclick="jump($(this).text())">00:03:43,459</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:44,731</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">交谈</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:46,264" end_time="00:03:48,412" tagtext="地图"
							x="99" y="31" w="331" h="202">
							<td><button class="btn" onclick="jump($(this).text())">00:03:46,264</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:48,412</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:48,774" end_time="00:03:50,589"
							tagtext="伪3D" x="254" y="65" w="126" h="199">
							<td><button class="btn" onclick="jump($(this).text())">00:03:48,774</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:50,589</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:50,680" end_time="00:03:55,208"
							tagtext="伪3D" x="116" y="81" w="296" h="190">
							<td><button class="btn" onclick="jump($(this).text())">00:03:50,680</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:03:55,208</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:03:55,558" end_time="00:04:00,868" tagtext="地图"
							x="111" y="61" w="338" h="197">
							<td><button class="btn" onclick="jump($(this).text())">00:03:55,558</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:00,868</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:03,394" end_time="00:04:06,913" tagtext="射击"
							x="147" y="41" w="164" h="166">
							<td><button class="btn" onclick="jump($(this).text())">00:04:03,394</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:06,913</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:07,357" end_time="00:04:10,797" tagtext="地图"
							x="100" y="48" w="303" h="179">
							<td><button class="btn" onclick="jump($(this).text())">00:04:07,357</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:10,797</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">地图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:10,959" end_time="00:04:13,309" tagtext="男性"
							x="225" y="23" w="165" h="220">
							<td><button class="btn" onclick="jump($(this).text())">00:04:10,959</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:13,309</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">男性</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:13,493" end_time="00:04:19,294"
							tagtext="伪3D" x="121" y="62" w="336" h="203">
							<td><button class="btn" onclick="jump($(this).text())">00:04:13,493</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:19,294</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:19,500" end_time="00:04:25,147" tagtext="赛车"
							x="75" y="43" w="374" h="235">
							<td><button class="btn" onclick="jump($(this).text())">00:04:19,500</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:25,147</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">赛车</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:27,995" end_time="00:04:29,991" tagtext="射击"
							x="78" y="110" w="141" h="84">
							<td><button class="btn" onclick="jump($(this).text())">00:04:27,995</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:29,991</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">射击</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:30,122" end_time="00:04:31,017" tagtext="爆炸"
							x="321" y="69" w="135" h="196">
							<td><button class="btn" onclick="jump($(this).text())">00:04:30,122</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:31,017</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">爆炸</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:33,526" end_time="00:04:34,424" tagtext="爆炸"
							x="69" y="134" w="344" h="117">
							<td><button class="btn" onclick="jump($(this).text())">00:04:33,526</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:34,424</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">爆炸</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:34,774" end_time="00:04:36,746" tagtext="爆炸"
							x="305" y="155" w="95" h="81">
							<td><button class="btn" onclick="jump($(this).text())">00:04:34,774</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:36,746</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">爆炸</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:36,921" end_time="00:04:36,937" tagtext="爆炸"
							x="67" y="145" w="127" h="84">
							<td><button class="btn" onclick="jump($(this).text())">00:04:36,921</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:36,937</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">爆炸</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:37,998" end_time="00:04:38,998"
							tagtext="伪3D" x="176" y="109" w="161" h="150">
							<td><button class="btn" onclick="jump($(this).text())">00:04:37,998</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:38,998</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:41,128" end_time="00:04:43,065"
							tagtext="伪3D" x="80" y="53" w="339" h="203">
							<td><button class="btn" onclick="jump($(this).text())">00:04:41,128</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:43,065</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:44,283" end_time="00:04:45,949"
							tagtext="星空图" x="87" y="44" w="320" h="235">
							<td><button class="btn" onclick="jump($(this).text())">00:04:44,283</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:45,949</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">星空图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:46,469" end_time="00:04:47,236"
							tagtext="星空图" x="189" y="48" w="205" h="126">
							<td><button class="btn" onclick="jump($(this).text())">00:04:46,469</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:47,236</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">星空图</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:53,819" end_time="00:04:55,054" tagtext="帐篷"
							x="376" y="18" w="112" h="86">
							<td><button class="btn" onclick="jump($(this).text())">00:04:53,819</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:55,054</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">帐篷</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:04:55,440" end_time="00:04:58,221" tagtext="女性"
							x="122" y="21" w="293" h="235">
							<td><button class="btn" onclick="jump($(this).text())">00:04:55,440</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:04:58,221</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">女性</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:05:01,709" end_time="00:05:07,358"
							tagtext="伪3D" x="207" y="12" w="283" h="192">
							<td><button class="btn" onclick="jump($(this).text())">00:05:01,709</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:05:07,358</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">伪3D</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:05:07,587" end_time="00:05:12,657" tagtext="赛车"
							x="105" y="50" w="357" h="227">
							<td><button class="btn" onclick="jump($(this).text())">00:05:07,587</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:05:12,657</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">赛车</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:05:13,860" end_time="00:05:16,684" tagtext="格斗"
							x="89" y="104" w="313" h="175">
							<td><button class="btn" onclick="jump($(this).text())">00:05:13,860</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:05:16,684</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">格斗</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
						<tr start_time="00:05:19,449" end_time="00:05:24,935"
							tagtext="夫妻不合" x="65" y="36" w="386" h="223">
							<td><button class="btn" onclick="jump($(this).text())">00:05:19,449</button></td>
							<td><button class="btn" onclick="jump($(this).text())">00:05:24,935</button></td>
							<td ondblclick="fastEdit(this)" class="text-center"
								style="vertical-align: middle">夫妻不合</td>
							<td class="text-center" style="vertical-align: middle"><span
								class="glyphicon glyphicon-trash"
								onclick="$(this).parent().parent().remove()"></span></td>
						</tr>
					</tbody>

				</table>
			</div>
		</div>
		<div class="row" style="height: 100px">
			<div class="col-md-2 label-warning" style="height: 100%">
				当前时间：<span id="time"></span> s <br> 当前坐标：<span id="statusXY"></span>
				<button class="btn btn-default" type="button" onclick="download()">Save</button>
			</div>
			<div class="col-md-8 label well" style="height: 100%;">

				<div class="col-md-12">
					<div class="row" style="margin: 3px;">
						<div class="col-md-9">
							<div class="input-group">

								<span class="input-group-addon" id="sizing-addon2">标签</span> <input
									id="currentTag" type="text" class="form-control"
									aria-label="..." onfocus="useShort=false;"
									onblur="useShort=true"> <span class="input-group-btn">
									<button class="btn btn-default" type="button"
										onclick="addTag()">确定</button>
								</span>
							</div>
						</div>
						<div class="col-md-3">
							<select id="typeList" class="form-control"
								onChange='$("#currentTag").val(this.value)'>
								<option>交谈</option>
								<option>伪3D</option>
								<option>射击</option>
								<option>地图</option>
								<option>赛车</option>
								<option>格斗</option>
								<option>飞机</option>
								<option>爆炸</option>
								<option>燃烧</option>
								<option>卡通</option>
								<option>男性</option>
								<option>女性</option>
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button"
										onclick="setStartTime()">开始时间[J]</button>
								</span> <input type="timestamp" id="start_time" class="form-control"
									aria-label="...">
							</div>
						</div>
						<div class="col-md-4">
							<div class="input-group">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button"
										onclick="setEndTime()">结束时间[K]</button>
								</span> <input type="timestamp" id="end_time" class="form-control"
									aria-label="...">
							</div>
						</div>
						<div class="col-md-4">
							<button class="btn btn-default" type="button"
								onclick="video.playbackRate=$(this).text()">0.25</button>
							<button class="btn btn-default" type="button"
								onclick="video.playbackRate=$(this).text()">0.5</button>
							<button class="btn btn-default" type="button"
								onclick="video.playbackRate=$(this).text()">1.0</button>
							<button class="btn btn-default" type="button"
								onclick="video.playbackRate=$(this).text()">2.0</button>

						</div>

					</div>
				</div>
			</div>
			<div class="col-md-2 label-info" style="height: 100%">
				<span id="status">
			</div>
		</div>


	</div>


</body>

<script>
	var video = null;
	var useShort = true;
	var X = 0, Y = 0;
	var W = 0, H = 0;
	var mouseDown = false;
	function pad(n) {
		if (n < 10)
			return "0" + n;
		return n;
	}
	function jump(timestr) {
		video.currentTime = str2sec(timestr);
	}
	function mksrt() {
		let tagList = $("#tagList");
		let tag = null;
		let ret = "";
		for (let i = 0; i < tagList.children().size(); i++) {
			tag = $(tagList.children()[i]);
			ret += (i + 1) + "\n";
			ret += tag.attr("start_time") + " --" + "> " + tag.attr("end_time")
					+ "\n";
			ret += tag.attr("tagText") + "\n\n";

		}

		return ret;
	}
	function download() {
		let srt = mksrt();
		let blob = new Blob([ srt ], {
			type : "application/vnd.openblox.game-binary"
		});
		saveAs(blob, "tagList.srt");
	}
	function str2sec(str) {
		let dec = str.split(",");
		let sec = dec[0].split(":");
		let ret = parseInt(sec[0] * 3600) + parseInt(sec[1] * 60)
				+ parseInt(sec[2]) + parseFloat("0." + dec[1]);
		// console.log(str+":"+ret);
		return ret;
	}
	function sec2str(sec) {
		let integer = parseInt(sec);
		let dec = sec - integer;
		let ss = integer % 60;
		integer = (integer - ss) / 60;
		let mm = integer % 60;
		integer = (integer - mm) / 60;
		let hh = integer;
		return pad(hh) + ":" + pad(mm) + ":" + pad(ss) + ","
				+ dec.toFixed(3).toString().substring(2);
	}
	function getCurrentTag() {
		let tagList = $("#tagList");
		let i = 0, tag = null;
		let now = video.currentTime;
		let start_time, endtime;
		for (i = 0; i < tagList.children().size(); i++) {
			tag = tagList.children()[i];
			start_time = str2sec($(tag).attr("start_time"));
			end_time = str2sec($(tag).attr("end_time"));
			if (start_time <= now && end_time >= now) {
				return $(tag);
			}
		}
		return null;
	}
	function showTag() {
		let tag = getCurrentTag();
		if (tag != null) {
			$("#subtitle").text(tag.attr("tagText"));
			$("#currentTag").val(tag.attr("tagText"));
			$("#subtitle").css("left", tag.attr("x") + "px");
			$("#subtitle").css("top", tag.attr("y") + "px");
			w = parseInt(tag.attr("W"));
			h = parseInt(tag.attr("H"));
			if (w <= 0)
				w = 120;
			if (h <= 0)
				h = 30;
			$("#subtitle").css("width", w + "px");
			$("#subtitle").css("height", h + "px");
		} else if (mouseDown) {
			//      setStartTime();
			let tagText = $("#currentTag").val();
			$("#subtitle").text(tagText);
			$("#subtitle").css("left", X + "px");
			$("#subtitle").css("top", Y + "px");
			$("#subtitle").css("width", W + "px");
			$("#subtitle").css("height", H + "px");
		} else { //无
			$("#subtitle").text("");
			$("#subtitle").css("width", "0px");
			$("#subtitle").css("height", "0px");

		}
	}
	function showTime() {
		//console.log(video.currentTime);

		$("#time").text(sec2str(video.currentTime));
		$("#statusXY").text("(" + X + "," + Y + ")" + "[" + W + "," + H + "]");
		showTag();

		$("#mapper").css("height", parseInt($(video).css("height")) - 60);
		$("#mapper").css("width", $(video).css("width"));
	}

	function setStartTime() {
		$("#start_time").val(sec2str(video.currentTime));
		if (video.paused) {
			//      video.play();
		}
	}

	function setEndTime() {
		$("#end_time").val(sec2str(video.currentTime));
		if (!video.paused) {
			//      video.pause();
		}
	}
	function fastEdit(td) {
		let val = $(td).text();
		$(td)
				.html(
						"<input id='tagContent' type='text' value='"+val+"' onfocus='useShort=false;' onblur='useShort=true'>");
		let input = $($(td).children()[0]);
		input.change(function() {
			let newValue = $(this).val();
			$(td).html(newValue);
			$(td).parent().attr("tagText", newValue);
		});

	}
	function addTag() {
		let start_time = $("#start_time").val();
		let end_time = $("#end_time").val();
		let tagText = $("#currentTag").val();
		if (end_time == start_time) {
			end_time = sec2str(str2sec(start_time) + 1);

		}
		let newTag = "<tr start_time='"+start_time+"' end_time='"+end_time+"' tagText='"+tagText+"' x='"+X+"' y='"+Y+"' w='"+W+"' h='"+H+"'>"
				+ "<td><button class='btn' onclick='jump($(this).text())' >"
				+ start_time
				+ "</button></td>"
				+ "<td><button class='btn' onclick='jump($(this).text())' >"
				+ end_time
				+ "</button></td><td onDblClick='fastEdit(this)' class='text-center' style='vertical-align:middle'>"
				+ tagText
				+ "</td>"
				+ "<td  class='text-center' style='vertical-align:middle'><span class='glyphicon glyphicon-trash' onclick='$(this).parent().parent().remove()'></span></td>"
				+ "</tr>";

		let tagList = $("#tagList");
		let added = false;
		let i = 0, tag = null;
		for (i = 0; i < tagList.children().size(); i++) {
			tag = tagList.children()[i];
			if (str2sec($(tag).attr("start_time")) <= str2sec(start_time)
					&& str2sec($(tag).attr("end_time")) >= str2sec(start_time)) {
				$(tag)[0].scrollIntoView();
				$(tag).before(newTag);
				$(tag).remove();
				added = true;

				break;
			}
			if (str2sec($(tag).attr("start_time")) > str2sec(start_time)) {
				$(tag)[0].scrollIntoView();
				$(tag).before(newTag);
				added = true;

				break;
			}
		}
		if (!added) {
			tagList.append(newTag);
			$("#tagList").parent().parent()[0].scrollTop = $("#tagList")
					.parent().parent()[0].scrollHeight;
		}
		console.log(start_time + " --" + "> " + tagText);

	}
	function showXY(e) {
		let XY = $("#XY");
		let x = parseInt(e.clientX - $("#zero").position().left);
		let y = parseInt(e.clientY - $("#zero").position().top);
		XY.text("(" + x + "," + y + ")");
		XY.css("left", x);
		XY.css("top", y - parseInt(XY.css("height")));
	}

	function setXY(e) {
		let x = parseInt(e.clientX - $("#zero").position().left);
		let y = parseInt(e.clientY - $("#zero").position().top);
		X = x;
		Y = y;
		let tag = getCurrentTag();
		if (tag != null) {
			tag.attr("X", x);
			tag.attr("Y", y);
		}
	}
	function setWH(e) {
		let x = parseInt(e.clientX - $("#zero").position().left);
		let y = parseInt(e.clientY - $("#zero").position().top);
		W = Math.abs(x - X);
		H = Math.abs(y - Y);
		let tag = getCurrentTag();
		if (tag != null) {
			tag.attr("W", x);
			tag.attr("H", y);
		}

	}
	$(document)
			.ready(
					function() {
						video = $("video")[0];
						document.onkeydown=shortKey;
						$("#status")
								.html(
										"J:Set Start Time<br>  K:Set End Time<br> Space:Play/Pause <br> H:Back <br> L:Forward");

						$("#currentTag").val($("#typeList").val());
						setStartTime();
						setEndTime();
						window.setInterval("showTime();", 100);
						//$( "#subtitle" ).draggable();
						$(video).css("margin", "0px");
						$("#mapper").mousemove(function(e) {

							showXY(e);
							if (mouseDown) {
								setWH(e);
								setEndTime();
								showTag();
							} else {
								//   setStartTime();
							}
						});
						$("#mapper").mousedown(function(e) {
							mouseDown = true;
							setXY(e);
							setStartTime();
							showTag();
							//addTag();
						});
						$("#mapper").mouseup(
								function(e) {
									mouseDown = false;
									setWH(e);
									if (getCurrentTag() != null) {
										$("#end_time").val(
												getCurrentTag()
														.attr("end_time"));
									} else {
										setEndTime();
									}
									addTag();
									showTag();
								});

						//$( "#mapper" ).css("top","-"+$(video).css("height"));

					});
	function shortKey(e) {
		// 兼容FF和IE和Opera
		var theEvent = e || window.event;
		var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
		if (!useShort)
			return true;
		if (code == 13) {
			if (theEvent.target.tagName == "INPUT") {
				return true;
			}
			return false;
		}
		if (code == 74) { // j
			setStartTime();

		}
		if (code == 75) { // k
			setEndTime();
			addTag();
		}
		if (code == 32) { // space
			if (video.paused) {
				video.play();
			} else {
				video.pause();
			}
		}
		if (code == 37 || code == 72) { // <-  H
			if (video.paused)
				video.currentTime -= 0.2;
			else
				video.currentTime -= 1;
		}
		if (code == 39 || code == 76) { // ->   L
			if (video.paused)
				video.currentTime += 0.2;
			else
				video.currentTime += 1;
		}

		console.log("shortKey:" + code);
		return true;
	}
</script>
</html>

