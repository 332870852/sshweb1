<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/js/easyui/themes/default/easyui.css"></link>
<%-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/js/easyui/themes/icon.css"></link> --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/icon.css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/main.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/main.js"></script>
	
<body>
		<!-- 数据表单 -->
	<div class="easyui-layout" data-options="fit:true">
		<table id="roleDatagrid"></table>
		<div id="roleToolbar">
			<div class="roleToolbar_button" style="height: 40px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="role_openAdd()" plain="true">添加</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="role_openEdit()" plain="true">修改</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="role_remove()" plain="true">删除</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="role_cancel()" plain="true">取消</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="role_reload()" plain="true">刷新</a>
			</div>
		</div>
	</div>

<!-- Dialog -->
	<div id="role_dialog" class="easyui-dialog" title="添加用户" data-options="closed:true,iconCls:'icon-save'" style="width: 400px; padding: 10px;">
		<form  id="role_dialog_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">角色名称:<input type="hidden" id="id" name="id"/></td>
					<td><input type="text" id="roleName" name="roleName" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<td width="60" align="right">角色状态:</td>
					<td><input type="text" id="state" name="state" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<td width="100" align="right">角色权限:</td>
					<td><input type="text" id="permissionsId" name="permissionsId" class="easyui-combotree"
					data-options="url:'/sshweb1/getAllPermissions',multiple:true,required:true,pompt:'请选择...'"/></td>
				</tr>
			</table>
		</form>
	</div>

<script type="text/javascript">
$(function(){
	
	$("#roleDatagrid").datagrid({
		url:"/sshweb1/getAllPagerRoles",
		pagination:true,
		pageSize:10,
		toolbar:"#roleToolbar",
		//fit:true,
		columns:[[
			{field:"ck",checkbox:true},
			{field:"id",sortable:true,title:"角色编码",width:200},
			{field:"roleName",title:"角色名",width:200},
			{field:"state",title:"角色状态",width:200,formatter:function(value,row,index){
				if(value==1){
					return "正常状态";
				}else{
					return "禁用状态";
				}
			}
			},
			{field:"permissions",title:"角色权限",formatter:function(value,row,index){
				var permissionsStr="";
				if(value!=null&&value.length>0){
					for(var i=0;i<value.length;i++){
						permissionsStr+="【"+value[i].resource+"】";
					}					
				}
				return permissionsStr;
			}}
		]],
		onLoadSuccess:function(){
			var dgbw=$(".datagrid-view div.datagrid-view2 div.datagrid-body").css("width");
			var dgbh=$(".datagrid-view div.datagrid-view2 div.datagrid-body").css("height");
			$(".datagrid-view div.datagrid-view2 div.datagrid-body").css("width",parseInt(dgbw)+2);
			$(".datagrid-view div.datagrid-view2 div.datagrid-body").css("height",parseInt(dgbh)+2);		
		}
	});
});

	function role_openAdd() {
		$("#role_dialog_form").form("clear");//清空
		$("#role_dialog").dialog({
			title:"添加角色",
			closed:false,
			model:true,
			buttons:[
				{
					text:"确定",iconCls:"icon-ok",
					handler:role_add	
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
						$("#role_dialog").dialog("close");
					}
				}
			],
			
		});
	}
	
	function role_openEdit() {
		$("#role_dialog_form").form("clear");//清空
		var rows=$("#roleDatagrid").datagrid("getSelections");
		if(rows.length>1){
			$.messager.alert("信息提示","一次只能修改一条记录","info");
		}else if(rows.length==0){
			$.messager.alert("信息提示","请✔要修改的数据","info");
		}else{
			var role=rows[0];
			$("#role_dialog_form").form("load",role);
			$("#role_dialog").dialog({
				title:"修改角色",
				closed:false,
				model:true,
				buttons:[
					{
						text:"确定",iconCls:"icon-ok",
						handler:role_edit						
						
					},
					{
						text:"取消",iconCls:"icon-cancel",
						handler:function(){
							$("#role_dialog").dialog("close");
						}
					}
				],
				
			});
		}
	}
//添加用户	
	function role_add() {
		doAjax("/sshweb1/addRole");
	}
//修改用户
	function role_edit() {
		doAjax("/sshweb1/updateRole");
	}
//删除用户
	function role_remove() {
		var rows=$("#roleDatagrid").datagrid("getSelections");
		if(rows.length<1){
			$.messager.alert("信息提示","请✔要修改的数据","info");
		}else{
			$.messager.confirm("信息提示","确定要删除选中的记录吗？",function(result){			
			if(result){
				var ids=[];
				$(rows).each(function() {
					ids.push(this.id);
				});
				
				$.ajax({
					url:"/sshweb1/deleteRole",
					method:"post",
					data:{
						ids:ids
					},
					success:function(data){ //后台成功返回ok，否则error
						if(data=="ok"){					
							$("#roleDatagrid").datagrid("reload");				
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
function role_cancel() {
	$("#roleDatagrid").datagrid("rejectChanges");
}

//刷新
function role_reload() {
	$("#roleDatagrid").datagrid("reload");
}
//封装ajax
	function doAjax(param) {
				
			var permissionsArr=$("#permissionsId").val().split(",");
			var permissionsObjStr="[";
			for(var i=0;i<permissionsArr.length;i++){
				permissionsObjStr+="{id:"+permissionsArr[i]+"},";
			}
			//变成[{id:1},{id:2}]
			permissionsObjStr=permissionsObjStr.substring(0,permissionsObjStr.length-1)+"]";
			var permissionsObj=eval("("+permissionsObjStr+")");
			
		$.ajax({
			url:param,
			method:"post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				id:$("#id").val(),
				roleName:$("#roleName").val(),
				state:$("#state").val(),
				permissions:permissionsObj
			}),
			success:function(data){ //后台成功返回ok，否则error
				if(data=="ok"){					
					$("#roleDatagrid").datagrid("reload");
					$("#role_dialog").dialog("close");	
					$.messager.alert("信息提示","提交成功","info");
				}else{
					$.messager.alert("信息提示","提交失败","info");

				}
			}
		});
		
	}
</script>	
</body>
</html>