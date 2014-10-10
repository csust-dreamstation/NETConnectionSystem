<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
		<base href="<%=basePath%>"></base>
		<title>test</title>
		<script type="text/javascript" src="<%=basePath%>/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/reasyui/outlook2.js"></script>	
		<script type="text/javascript" src="<%=basePath%>/easyui/tacticspc.js"></script>	
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>/styles/contact.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/default/easyui.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/main.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/demo/demo.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/easyui/themes/icon.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/styles/default.css"></link>
	<script type="text/javascript">

	
	</script>
</head>

<body style="font-family:microsoft yahei">
		
			<s:action name="tacticsListAction" id="tactics">
					<s:param name="tacticsid" value="1" />
			</s:action>


				<label for="tacticsname">策略名称</label>
				<select class="easyui-combobox" id="combobox" style="width:200px;" >
					<s:iterator id="a" value="#tactics.list">
					<option value=<s:property value="#a.tacticsname"/>><s:property value="#a.tacticsname"/></option>
					</s:iterator>
				</select>	
		
				
				<button  class="easyui-linkbutton"  onclick="addTacticsPc();">应用</button>
				<div id="tabs" class="easyui-tabs"  style=" width:780px; height:536px"  >
				<div id="elemet" title="客户端列表" ;style="padding:0px; width:780px; height:580px">
							<table id="clientlist" ></table>
				</div>
				</div>
			
	
</body>
</html>