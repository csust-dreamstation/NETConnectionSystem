<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<base href="<%=basePath%>"></base>
	<!-- <meta charset="UTF-8"> -->
	<title>网络准入系统</title>

	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easyui/outlook2.js"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"/>		
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/default.css"></link>
		
	<script type="text/javascript">
	 var _menus = {"menus":[
							{"menuid":"1","icon":"icon-sys","menuname":"监管信息",
								"menus":[{"menuname":"主机列表","icon":"icon-nav","url":"javascript:addTab('主机列表','onlinelist.jsp')"},
										{"menuname":"实时命令","icon":"icon-add","url":"javascript:addTab('实时命令','monitor.jsp')"}
									]
							},{"menuid":"8","icon":"icon-sys","menuname":"安全策略",
								"menus":[{"menuname":"策略配置","icon":"icon-nav","url":"javascript:addTab('策略配置','tactics/main.jsp?tacticsname=default')"},
								         {"menuname":"策略与主机配置","icon":"icon-nav","url":"javascript:addTab('策略与主机配置','tactics/tacticsPc.jsp?tacticsid=1')"},
								         {"menuname":"软件及补丁基本操作","icon":"icon-nav","url":"javascript:addTab('软件及补丁基本操作','tactics/addsoft.jsp')"},
									]
							},{"menuid":"56","icon":"icon-sys","menuname":"设置",
								"menus":[{"menuname":"系统设置","icon":"icon-nav","url":"javascript:addTab('系统设置','getSystemSettingAction.action')"},
								
									]
							},{"menuid":"56","icon":"icon-sys","menuname":"日志查询",
								"menus":[{"menuname":"管理员操作日志查询","icon":"icon-nav","url":"javascript:addTab('管理员操作日志查询','adminlog.jsp')"},
										 {"menuname":"客户端在线日志查询","icon":"icon-nav","url":"javascript:addTab('客户端在线日志查询','onlinetimelog.jsp')"}
									]
							},{"menuid":"28","icon":"icon-sys","menuname":"报表管理",
								"menus":[{"menuname":"报表导出","icon":"icon-nav","url":"javascript:addTab('报表导出','tactics/exportreport.jsp')"}
									]
							}
					]};	
		function exit(){
  			$.messager.confirm('确认','您确认要注销登录吗？',function(r){    
			    if (r){
			    	window.location.href ="exitAction.action";  
			    }
			}); 
  		}
    </script> 	
  <style type="text/css">
	  		.astyle:HOVER{
	  			color: gray;
	  			text-decoration: none;
	  		}
	  		.astyle {
				color: red;
				text-decoration: none;
			}
	  </style>		
</head>
	
<body class="easyui-layout" style="overflow-y: hidden;"  scroll="no">
<div class="easyui-layout" style="width:1000px;height:100%;border: 2px solid #ADD8E6;margin-left: auto;margin-right: auto;">

     <div region="north" split="true" border="false" style="overflow: hidden; height: 100px;
       			 background: url(image/bg2.jpg); line-height: 20px;color: #fff; font-family: Verdana, 'Microsoft yahei',黑体">
			        <img src="image/logo1.png" alt="" style="margin-top: 18px;margin-left: 3%;"/>
			    	<div style="float: right;position:relative;top:75px;right:53px;">所属单位：衡阳市国税局</div>
			    	<div style="float: right;position:relative;top:55px;right:-107px;;">管理员：${user.username }
			    		<span style="margin-left: 10px;">[ <a id="exit" onclick="return exit()" class="astyle">注销</a> ]</span>
			    	</div>
    </div>
    
   <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        		<div class="footer">湖南省国家税务局信息中心、湖南中软兆通科技有限公司、长沙理工大学版权所有(建议使用IE9及以上的浏览器)</div>
    </div> 
 
    <div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
    		 	<div class="easyui-accordion" style="height:250px;">   
				</div>  
				<div id="calendar" style="width:173px;height:189px;"></div>
    </div>
    
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        	 <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
					<div title="欢迎使用" style="overflow:hidden;" id="home">
						 <img src="image/welcome7.jpg" width="100%;" height="100%;"/>
					</div>
	</div>
    </div>

</div>

</body>
</html>