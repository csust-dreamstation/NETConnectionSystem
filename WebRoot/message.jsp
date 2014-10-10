<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
    <div id="display" style="margin-left: 5px;">
    	<p>消息显示：</p>
    	<textarea rows="7" cols="50" name="sendedMsg" id="sendedMsg" style="border: 0px;" readonly="readonly"  value=""></textarea>
    	
    </div>
    <hr size="5" color="#ADD8E6" width="98%">
    <div style="margin-left: 5px;">
    	<p>消息内容：</p>
	   		<input type="hidden" name="mac" id="mac">
	   		<textarea rows="2" cols="50" name="msgContent" id="msgContent" style="border: 0px;"></textarea>

	   		<br>
	   		<div style="width:99%; height: 26px;background-color: #ADD8E6; margin-top: 10px;">
	   			<input type="button" value="发送消息" 
	   				style="float: right;position: relative;right:40px;" onclick="sendMsg()">
	   		</div>
    </div>
  </body>
</html>
