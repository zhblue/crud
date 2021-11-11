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
String movie_name=DAO.queryString("select movie_name from tb_movie where id=?", movie_id);
String fastsave=DAO.queryString("select fastsave from tb_manual_task where id=?", id);

System.out.println("video_url:"+video_url);
%>
<body>



	<div id="workbench" class="container">

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
					<%=VTM.srt2html(fastsave)%>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row" style="height: 100px">
			<div class="col-md-2 label-warning" style="height: 100%">
				当前时间：<span id="time"></span> s <br> 当前坐标：<span id="statusXY"></span>
				<button class="btn btn-default" type="button" onclick="download()"><span class='glyphicon glyphicon-download-alt'/></button>
				<button class="btn btn-default" type="button" onclick="fastsave(<%=id%>)"><span class='glyphicon glyphicon-floppy-disk'/></button>
			</div>
			<div class="col-md-8 label well" style="height: 100%;">

				<div class="col-md-12">
					<div class="row" style="margin: 3px;">
						<div class="col-md-9">
							<div class="input-group">
		<button class="btn" type="button" id="space" ></button>
		<button class="btn btn-primary" type="button" onclick="video.paused?video.play():video.pause();$('#space').focus()">运行</button>
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
								onChange='$("#currentTag").val(this.value);$("#space").focus()'>
								<% List<List> tags=DAO.queryList("select tag_name from tb_tag order by id desc",false);
								   for(List tag:tags){

									out.println("<option>"+tag.get(0)+"</option>");
								   }

								%>
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
      function reload(){
	 alert("成功");
      }
      function fastsave(rid){
		let tagList=$("#tagList");
		var newValue=mksrt();//$(tagList).html();
		let tableName="tb_manual_task";
		let fdname="fastsave";
		//alert(fdname);
		$.post("callStatic.jsp",{"c":"com.newsclan.crud.Tools","m":"update",
			"tbname":tableName,
			"fdname":fdname,
			"value":newValue,
			"id":rid
				
				},function(data,status,xhr){
		 	if(data.indexOf("reload();")!=-1) alert("保存成功!");   	
		    	else alert("保存失败!");
		});
      }
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
			ret += tag.attr("tagText")+"\n("+tag.attr("x")+","+tag.attr("y")+")-["+tag.attr("w")+","+tag.attr("h")+"]" + "\n\n";

		}

		return ret;
	}
	function download() {
		let srt = mksrt();
		let blob = new Blob([ srt ], {
			type : "text/plain"
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

		$("#mapper").css("height", parseInt($(video).css("height")) - 30);
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
						document.title='<%=movie_name%>';
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

