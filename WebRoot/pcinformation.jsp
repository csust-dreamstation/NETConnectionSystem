<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'pcinfomation.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <table>
    	<tr>
    		<td colspan="2">用户名：${pc.clientname }</td>
    	</tr>
    	<tr>
    		<td colspan="2">IP地址：${pc.ip }</td>
    	</tr>
    	<tr>
    		<td colspan="2">mac地址：${pc.mac }</td>
    	</tr>
    	<tr>
    		<td colspan="2">操作系统版本：${pc.os }</td>
    	</tr>
    	<tr>
    		<td colspan="2">cpu占用率：${pc.cpu }</td>
    	</tr>
    	<tr>
    		<td colspan="2">已用内存：${pc.memory }MB</td>
    	</tr>
    	<tr>
    		<td colspan="2">总共内存：${pc.allmemory }MB</td>
    	</tr>
    	<tr>
    		<td colspan="2">内存占用率：${pc.availablity }</td>
    	</tr>
    	<tr valign="top">
    		<td>软件运行状态：
    			<table name="t">
    				 <c:forEach items="${softlist }" var="softinstall">
	    				<tr>
	    					<td>
	    						${softinstall.softname }:
	    						<c:if test="${softinstall.installstate==1}">
	    							未运行
	    						</c:if>
	    						<c:if test="${softinstall.installstate==0 }">
	    							正在运行
	    						</c:if>
	    					</td>
	    				</tr>
    				</c:forEach>
    			</table>
    		</td>
    		<td>补丁安装状态：
    			<table name="t">
    				 <c:forEach items="${patchlist }" var="patchinstall">
	    				<tr>
	    					<td>
	    						${patchinstall.patchname }:
	    						<c:if test="${patchinstall.installstate==1}">
	    							未安装
	    						</c:if>
	    						<c:if test="${patchinstall.installstate==0 }">
	    							已经安装
	    						</c:if>
	    					</td>
	    				</tr>
    				</c:forEach>
    			</table>
    		</td>
    	</tr>
    </table>
  </body>
</html>
