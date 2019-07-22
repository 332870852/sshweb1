<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery/jquery.min.js"></script>
</head>
<body>
	<!-- 数据表单 -->
	<div class="easyui-layout" data-options="fit:true">
		<table id="usersDatagrid"></table>
		<div id="userToolbar">
			<div class="userToolbar_button" style="height: 40px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="users_openAdd()" plain="true">添加</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="users_openEdit()" plain="true">修改</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="users_remove()" plain="true">删除</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="users_cancel()" plain="true">取消</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="users_reload()" plain="true">刷新</a>
			</div>
			<div style="height: 40px;">
				<label>用户名:</label> <input class="easyui-textbox" id="user_searchbox" style="width: 150px; height: 25px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="user_searchbtn" style="height: 25px;">开始检索</a>
			</div>
		</div>
	</div>
	
	<!-- Dialog -->
	<div id="user_dialog" class="easyui-dialog" title="添加用户" data-options="closed:true,iconCls:'icon-save'" style="width: 500px; padding: 10px;">
		<form  id="user_dialog_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">用户名:<input type="hidden" id="id" name="id"/></td>
					<td><input type="text" id="username" name="username" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<td width="60" align="right">密 码:</td>
					<td><input type="text" id="password" name="password" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<td width="60" align="right">用户状态:</td>
					<td><input type="text" id="state" name="state" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<td width="60" align="right">注册日期:</td>
					<td><input type="text" id="regDate" name="regDate" class="easyui-datebox"/></td>
				</tr>
				<tr>
					<td width="60" align="right">角色:</td>
					<td><input type="text" id="rolesId" name="roles" class="easyui-combotree"
					data-options="url:'/sshweb1/getAllRoles',multiple:true,required:true,pompt:'请选择...'"/></td>
				</tr>
			</table>
		</form>
	</div>
	
<script type="text/javascript">
$(function(){
	
	$("#user_searchbtn").bind("click",function(){
		var queryParams=$("#usersDatagrid").datagrid("options").queryParams;
		queryParams.username=$("#user_searchbox").textbox("getValue");
		$("#usersDatagrid").datagrid("load");
	});
	$("#usersDatagrid").datagrid({
		url:"/sshweb1/getAllPagerUsers",
		pagination:true,
		pageSize:10,
		toolbar:"#userToolbar",
		//fit:true,
		columns:[[
			{field:"ck",checkbox:true},
			{field:"id",sortable:true,title:"用户编号",width:200},
			{field:"username",title:"用户名",width:200},
			{field:"password",title:"用户密码",width:200},
			{field:"state",title:"用户状态",width:200,formatter:function(value,row,index){
				if(value==1){
					return "正常状态";
				}else{
					return "禁用状态";
				}
			}
			},
			{field:"regDate",title:"注册日期",width:200},
			{field:"roles",title:"用户角色",width:200,formatter:function(value,row,index){
				var rolesStr="";
				if(value!=null&&value.length>0){
					for(var i=0;i<value.length;i++){
						rolesStr+="【"+value[i].roleName+"】";
					}					
				}
				return rolesStr;
			}}
		]],
	});
});

	function users_openAdd() {
		$("#user_dialog_form").form("clear");//清空
		$("#user_dialog").dialog({
			title:"添加用户",
			closed:false,
			model:true,
			onOpen:function(){ //打开dialog后触发
				$("#username").textbox({disabled:false}); //禁用
				$("#password").textbox({required:true});
			},
			buttons:[
				{
					text:"确定",iconCls:"icon-ok",
					handler:user_add	
					/* handler:function(){
						//实现数据的添加
						$("#user_dialog_form").form("submit",{
							url:"/sshweb1/addUser",
							success:function(data){ //后台成功返回ok，否则error
								if(data=="ok"){					
									$("#usersDatagrid").datagrid("reload");
									$("#user_dialog").dialog("close");	
									$.messager.alert("信息提示","提交成功","info");
								}else{
									$.messager.alert("信息提示","提交失败","info");
				
								}
							}
						});
					} */
									
				},
				{
					text:"取消",iconCls:"icon-cancel",
					handler:function(){
						$("#user_dialog").dialog("close");
					}
				}
			],
			
		});
	}
	
	function users_openEdit() {
		$("#user_dialog_form").form("clear");//清空
		var rows=$("#usersDatagrid").datagrid("getSelections");
		if(rows.length>1){
			$.messager.alert("信息提示","一次只能修改一条记录","info");
		}else if(rows.length==0){
			$.messager.alert("信息提示","请✔要修改的数据","info");
		}else{
			var user=rows[0];
			user.password="";	//密码不显示
			$("#user_dialog_form").form("load",user);
			$("#user_dialog").dialog({
				closed:false,
				model:true,
				onOpen:function(){ //打开dialog后触发
					$("#username").textbox({disabled:true}); //禁用
				},
				title:"修改用户",
				buttons:[
					{
						text:"确定",iconCls:"icon-ok",
						handler:user_edit						
						
					},
					{
						text:"取消",iconCls:"icon-cancel",
						handler:function(){
							$("#user_dialog").dialog("close");
						}
					}
				],
				
			});
		}
	}
//添加用户	
	function user_add() {
		doAjax("/sshweb1/addUser");
	}
//修改用户
	function user_edit() {
		doAjax("/sshweb1/updateUser");
	}
//删除用户
	function users_remove() {
		var rows=$("#usersDatagrid").datagrid("getSelections");
		if(rows.length<1){
			$.messager.alert("信息提示","请✔要删除的数据","info");
		}else{
			$.messager.confirm("信息提示","确定要删除选中的记录吗？",function(result){			
			if(result){
				var ids=[];
				$(rows).each(function() {
					ids.push(this.id);
				});
				
				$.ajax({
					url:"/sshweb1/deleteUser",
					method:"post",
					data:{
						ids:ids
					},
					success:function(data){ //后台成功返回ok，否则error
						if(data=="ok"){					
							$("#usersDatagrid").datagrid("reload");				
							$.messager.alert("信息提示","删除成功","info");
						}else{
							$.messager.alert("信息提示","删除失败","info");

						}
					}
				});
				}
			});
		}	
	}
	
//撤销
function users_cancel() {
	$("#usersDatagrid").datagrid("rejectChanges");
}

//刷新
function users_reload() {
	$("#usersDatagrid").datagrid("reload");
}
//封装ajax
	function doAjax(param) {
				
			var rolesArr=$("#rolesId").val().split(",");
			var roleObjStr="[";
			for(var i=0;i<rolesArr.length;i++){
				roleObjStr+="{id:"+rolesArr[i]+"},";
			}
			//变成[{id:1},{id:2}]
			roleObjStr=roleObjStr.substring(0,roleObjStr.length-1)+"]";
			var roleObj=eval("("+roleObjStr+")");
			
		$.ajax({
			url:param,
			method:"post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				id:$("#id").val(),
				username:$("#username").val(),
				password:$("#password").val(),
				state:$("#state").val(),
				regDate:$("#regDate").val(),
				roles:roleObj
			}),
			success:function(data){ //后台成功返回ok，否则error
				if(data=="ok"){					
					$("#usersDatagrid").datagrid("reload");
					$("#user_dialog").dialog("close");	
					$.messager.alert("信息提示","提交成功","info");
				}else if(data=="exits"){
					$.messager.alert("信息提示","账号已存在,不能重复添加!","info");
				}else{			
					$.messager.alert("信息提示","提交失败","info");
				}
			}
		});
		
	}
</script>	
</body>
</html>