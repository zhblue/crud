
	function dbid(evt){
		var data=$(evt.target).parent().parent().attr("id");
		return parseInt(data);
	}
	function view(rowid){
		var tbname=$("#tbname").val();
		$("#main").load("view.jsp",{"tb":tbname,"id":rowid},reformatform);
		
		//window.open("view.jsp?tb="+tbname+"&id="+rowid);
	}
	function edit(rowid){
		var tbname=$("#tbname").val();
		$("#main").load("add.jsp",{"tb":tbname,"id":rowid},reformatform);
	}
	function del(rowid){
		var ans=confirm('ȷ��ɾ�����'+rowid);
		var tbname=$("#tbname").val();
		if(ans){
			$.post("del.jsp",{"tbname":tbname,"id":rowid},new function(){
				 window.setTimeout("mainLoad('"+tbname+"')",1000);
			});
		}
	}
	function addButton(main){
		//$("#main tr :last-child").css("background","#eeeeee");
		$("#main tr").append("<td>"+
				"<span class='glyphicon glyphicon-eye-open'></span>"+
				"<span class='glyphicon glyphicon-edit'></span>"
										+"<span class='glyphicon glyphicon-trash' ></span></td>");
		$(".glyphicon-eye-open").bind("click",function(evt){
			view(dbid(evt));
		});
		$(".glyphicon-trash").bind("click",function(evt){
			del(dbid(evt));
		});
		$(".glyphicon-edit").bind("click",function(evt){
			edit(dbid(evt));
		});
		
		
	}
	function loadSelect(input){
		var input_name=input.attr("name");
		var default_value=input.val();
		var tbname=input_name.substring(0,input_name.length-3);
		var td=input.parent();
		td.load("select.jsp?tbname="+tbname+"&value="+default_value);
		
	}
	function reformatform(){
		$("input[name$=_id]").each(function(){
			loadSelect($(this));
		});
		$(".input_date").datepicker();
		$("textarea").ckeditor();
	}

	function mainLoad(tbname,pageNum,keyword){
		if (pageNum=='undefined') pageNum=0;
		if (keyword=='undefined') keyword='';
		tableName=tbname; 
		window.clearInterval(inter);
		$("#main").load("list.jsp",{"tb":tbname,"pageNum":pageNum,"keyword":keyword},function(text,status,http){
			
			if(status=="success"){
				addButton($("#main"));
				$("#tbname").after("<span id='addrow' class='glyphicon glyphicon-plus'></span>");
				$("#addrow").click(function(){
					$("#main").load("add.jsp",{tb:tbname},reformatform);
				});
			}
		});
		
	}
	function pageUp(tbname,pageNum){
		mainLoad(tbname,pageNum-1,searchKeyword);
	}
	function pageDown(tbname,pageNum){
		mainLoad(tbname,pageNum+1,searchKeyword);
	}
	function submitAdd(tbname){
		var data=$("#addForm").serialize();
		$.post("add.jsp",data,new function(){
			 mainLoad(tbname);
		});
	}
	function submitTable(){
		var data=$("#frmAddTable").serialize();
		var tbname=$("#tb_name").val();
		
		$.post("addTable.jsp",data,new function(){
			 window.setTimeout("mainLoad('"+tbname+"')",1000);
		});
	}
	function addTable(){
		$("#main").empty();
		$("#main").append("<form method=post onSubmit='submitTable()' id='frmAddTable'><table id='tb_adding' class='table table-striped table-hover'></table></form>");
		$("#tb_adding").append("<tr><td>����<input type=text id=tb_name name=tb_name value='tb_'></td><td>����<input type=text name=tb_title value='��Ϣ'></td></tr>");
		$("#tb_adding").append("<tr><td>����</td><td>����</td><td>����</td><td><a href=# onclick='addColumn();'><span class='glyphicon glyphicon-plus'></span></a></td></tr>");
		$("#frmAddTable").append("<input type=button onclick='submitTable()' value='ȷ��'><input type=reset value='����'>");
		addColumn();
		
	}
	function addColumn(){
		var row="<tr>";
		row+="<td><input name=fd_name type=text></td>";
		row+="<td><input name=fd_type type=text value='varchar(32)'></td>";
		row+="<td><input name=fd_title type=text></td>";
		row+="</tr>";
		$("#tb_adding").append(row);
	}
	function search(keyword){
		window.clearTimeout(stid);
		searchKeyword=keyword;
		stid=window.setTimeout("mainLoad(tableName,0,'"+keyword+"');",500);
	}
	var stid=null;
	var tableName="user";
	var searchKeyword="";