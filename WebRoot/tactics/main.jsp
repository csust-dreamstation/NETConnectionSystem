<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>test</title><base href="<%=basePath%>"></base>
		<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/reasyui/outlook2.js"></script>	
		<script type="text/javascript" src="<%=basePath%>/easyui/tactics.js"></script>	
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>/styles/contact.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/main.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/default.css"></link>
</head>
		
<body style="font-family:microsoft yahei">
		<div style="">
			<s:action name="tacticsListAction" id="tactics">
			<s:param name="tacticsid" value="1" />
			</s:action>
			<div style="float:left;width:550px;height:25px;">
			<form action="">
				策略名称
				<select class="easyui-combobox" id="combox01" style="width:200px;">
					<s:iterator id="a" value="#tactics.list">
					<option value=<s:property value="#a.tacticsname"/>><s:property value="#a.tacticsname"/></option>
					</s:iterator>
				</select>	
				
				<button class="easyui-linkbutton" onclick="confirm1();">删除策略</button>
				<button class="easyui-linkbutton" onclick="prompt1();">添加策略</button>
				<button class="easyui-linkbutton" onclick="addSoftList01();">保存策略</button>  
			</form>
			</div>
			
			<div>策略检查时间配置<button href="javascript:void(0)" class="easyui-linkbutton" onclick="settime();">时间设置</button></div>
		</div>	
			<div id="tabs" class="easyui-tabs"  style=" width:778px; height:580px"  >
			<div id="elemet" title="基本配置信息" style="padding:0px;">
				<div style="width：760px;height:285px;">
				<div>
				<div  style="width：370px;height:285px;float:left">
				<div class="easyui-tabs" style="width:390px;height:285px;">
				<div title="黑名单" style="padding:0px;">
							<table id="blacktable" ></table>
				</div>
				</div>
				</div>
				
				<div  style="width:369px;height:285px;float:left">
				<div class="easyui-tabs" style="width:390px;height:285px;">
				<div title="白名单" style="padding:0px;">
						<table id="whitetable" ></table>
				</div>
				</div>
				</div>
				</div>
				
				<div style="width:778px;height:245px;padding-top:285px;">
				<div class="easyui-tabs" style="width:778px;height:245px;" >
				<div title="补丁名单" style="padding:0px;">
							<table id="patchtable" ></table>
				</div>
				</div>
				</div>
				
			</div>
			</div>
		</div>
		  <div id="time" class="easyui-window" title="配置时间" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:300px;height:300px;padding:10px;">
      
				<form action="">
				<h3 id="p1">Hello World!</h3>
				黑名单检查频率
				<div style="margin-right:0px;">
				<select class="easyui-combobox" id="blacktime" style="width:150px;">
					<option value="5">5秒</option>
					<option value="10">10秒</option>
					<option value="20">20秒</option>
					<option value="30">30秒</option>
					<option value="40">40秒</option>
				</select>
				</div>
				</br>
				黑名单检查频率
				<div style="margin-right:0px;">
				<select class="easyui-combobox" id="whitetime" style="width:150px;">
					<option value="5">5秒</option>
					<option value="10">10秒</option>
					<option value="20">20秒</option>
					<option value="30">30秒</option>
					<option value="40">40秒</option>
				</select>
				</div>
				</br>
				补丁检查频率
				<div style="margin-right:0px;">
				<select class="easyui-combobox" id="patchtime" style="width:150px;">
					<option value="120">50秒</option>
					<option value="130">60秒</option>
					<option value="140">70秒</option>
					<option value="150">80秒</option>
					<option value="160">90秒</option>
				</select>
				</div>
				</br>
				<div style="padding-left:60px;">
				<button class="easyui-linkbutton" onclick="addtime();" >保存</button>
				</div>
			</form>
			
    </div>
</body>
</html>