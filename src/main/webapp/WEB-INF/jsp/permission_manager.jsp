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
		<table id="permissionDatagrid"></table>
		<div id="permissionToolbar">
			<div class="permissionToolbar_button" style="height: 40px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="permission_openAdd()" plain="true">添加</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="permission_openEdit()" plain="true">修改</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="permission_remove()" plain="true">删除</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="permission_cancel()" plain="true">取消</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="permission_reload()" plain="true">刷新</a>
			</div>
		</div>
	</div>

<!-- Dialog -->
	<div id="permission_dialog" class="easyui-dialog" title="添加用户" data-options="closed:true,iconCls:'icon-save'" style="width: 400px; padding: 10px;">
		<form  id="permission_dialog_form" method="post">
			<table>
				<tr>
					<td width="60" align="right">权限名称:<input type="hidden" id="id" name="id"/></td>
					<td><input type="text" id="resource" name="resource" class="easyui-textbox"/></td>
				</tr>
				<tr>
					<td width="60" align="right">权限状态:</td>
					<td><input type="text" id="state" name="state" class="easyui-textbox"/></td>
				</tr>
			</table>
		</form>
	</div>

<script type="text/javascript">
$(function(){
	
	$("#permissionDatagrid").datagrid({
		url:"/sshweb1/getAllPagerpermissions",
		pagination:true,
		pageSize:10,
		toolbar:"#permissionToolbar",
		//fit:true,
		columns:[[
			{field:"ck",checkbox:true},
			{field:"id",sortable:true,title:"权限编码",width:200},
			{field:"resource",title:"权限名称",width:200},
			{field:"state",title:"权限状态",width:200,formatter:function(value,row,index){
				if(value==1){
					return "正常状态";
				}else{
					return "禁用状态";
				}
			}
			},	
		]]
	});
});

	function permission_openAdd() {
		$("#permission_dialog_form").form("clear");//清空
		$("#permission_dialog").dialog({
			title:"修改权限",
			closed:false,
			model:true,
			buttons:[
				{
					text:"确定",iconCls:"icon-ok",
					handler:permission_add	
													
				},
				{
					text:"取消",iconCls:"icon-cancel",
					handler:function(){
						$("#permission_dialog").dialog("close");
					}
				}
			],
			
		});
	}
	
	function permission_openEdit() {
		$("#permission_dialog_form").form("clear");//清空
		var rows=$("#permissionDatagrid").datagrid("getSelections");
		if(rows.length>1){
			$.messager.alert("信息提示","一次只能修改一条记录","info");
		}else if(rows.length==0){
			$.messager.alert("信息提示","请✔要修改的数据","info");
		}else{
			var permission=rows[0];
			$("#permission_dialog_form").form("load",permission);
			$("#permission_dialog").dialog({
				title:"修改角色",
				closed:false,
				model:true,
				buttons:[
					{
						text:"确定",iconCls:"icon-ok",
						handler:permission_edit						
						
					},
					{
						text:"取消",iconCls:"icon-cancel",
						handler:function(){
							$("#permission_dialog").dialog("close");
						}
					}
				],
				
			});
		}
	}
//添加用户	
	function permission_add() {
		doAjax("/sshweb1/addPermission");
	}
//修改用户
	function permission_edit() {
		doAjax("/sshweb1/updatePermission");
	}
//删除用户
	function permission_remove() {
		var rows=$("#permissionDatagrid").datagrid("getSelections");
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
					url:"/sshweb1/deletePermission",
					method:"post",
					data:{
						ids:ids
					},
					success:function(data){ //后台成功返回ok，否则error
						if(data=="ok"){					
							$("#permissionDatagrid").datagrid("reload");				
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
function permission_cancel() {
	$("#permissionDatagrid").datagrid("rejectChanges");
}

//刷新
function permission_reload() {
	$("#permissionDatagrid").datagrid("reload");
}
//封装ajax
	function doAjax(param) {
			
		$.ajax({
			url:param,
			method:"post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify({
				id:$("#id").val(),
				resource:$("#resource").val(),
				state:$("#state").val(),
			}),
			success:function(data){ //后台成功返回ok，否则error
				if(data=="ok"){					
					$("#permissionDatagrid").datagrid("reload");
					$("#permission_dialog").dialog("close");	
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