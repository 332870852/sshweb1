$(function() {
	$(".sshweb_side_tree li a").bind("click",function(){
		var title=$(this).text();
		var iconCls=$(this).attr("data-icon");
		var url=$(this).attr("data-link");
		var iframe=$(this).attr("iframe")==1?true:false;
		addTab(title,url,iconCls,iframe); 	//添加选项卡
		
	});
});

function addTab(title,url,iconCls,iframe) {	
	var tabPanel=$("#sshweb_tabs");	
	if(!tabPanel.tabs("exists",title)){ //通过判断标题存不存在
		//不存在，打开新的
		var content="<iframe scrolling='auto' frameborder='0' src='"+url+"' style='width:100%;height:100%;'></iframe>";
		if(iframe){// iframe=0时，用远程href
			tabPanel.tabs("add",{
				title:title,
				content:content,  //<iframe> 放到tabs，灵活，与外面网页相互独立，不受外边网页影响 ，缺点：重复加载js，css文件
				iconCls:iconCls,
				fit:true,
				cls:"pd3",
				closable:true
			});
		}else{
			tabPanel.tabs("add",{
				title:title,
				href:url,		//本质是url指向页面的<body>内容 ,不用重复加载js，ccs，js，ccs必须写在<body>标签里
				iconCls:iconCls,
				fit:true,
				cls:"pd3",
				closable:true
			});
		}
	}else{
		tabPanel.tabs("select",title)
	}
};

