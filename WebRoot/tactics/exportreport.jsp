<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<base href="<%=basePath%>"></base>
		<title>test</title>
		<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/reasyui/outlook2.js"></script>
		<script type="text/javascript" src="<%=basePath%>/easyui/export.js"></script>	
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>/styles/contact.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/main.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/default.css"></link>
</head>
   <body style="font-family:microsoft yahei">
			<select class="easyui-combobox" id="reportlist" style="width:200px;">
					<option id=list01 value="0">客户端基本信息报表</option>
					<option id=list02 value="1">客户端未关机终端报表</option>
					<option id=list03 value="2">客户端软件安装统计报表</option>
					<option id=list04 value="3">客户端违规统计报表</option>
			</select>
			<button class="easyui-linkbutton" onclick="exportReport();" >导出报表</button>
			<div id="tt" class="easyui-tabs"  style="width:780px;height:600px">
			
			<div title="客户端基本信息报表" style="padding:0px;">
			<table id="softlist" class="easyui-datagrid"></table>
			</div>
			
			<div title="客户端未关机终端报表" style="padding:0px;">
			<table id="unclosedReport" class="easyui-datagrid" title=""></table>
			</div>
			
			<div title="客户端软件安装统计报表" style="padding:0px;">
			<table id="installReport" class="easyui-datagrid" title=""></table>
			</div>
			
			<div title="客户端违规统计报表" style="padding:0px;">
			<table id="illegalaceessReport" class="easyui-datagrid" title=""></table>
			</div>
			
			</div>
			
	
</body>
</html>