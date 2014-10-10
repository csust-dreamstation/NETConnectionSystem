<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'adminlog.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script></head>
  <style type="text/css">
  		.opbt{
  			margin-left: 10px;
  			background-color: #ADD8E6;
  			color: white;
  		}
  </style>
  
  <body>
	  <div style="width: 100%;height: 25%;display: inline-block;">
	  	 <fieldset style="padding: 5px;">
	  	 	<legend>查询选择</legend>
	  	 	<div style="margin-top:10px;margin-left:10px;">
	  	 	<div style="float: left; ">
				姓名:
				<input id="adminname" type="text" style="width: 70px;"/>
			</div>
			<div style="float: left;">
				&nbsp;&nbsp;&nbsp;&nbsp;关键字:
				<input id="keywords" type="text" style="width: 60px;"/>
			</div>
			<div style="float: left;">
				&nbsp;&nbsp;&nbsp;&nbsp;操作:
				<select id="optionname" style="width: 90px;">
					<option value="">=选择操作=</option>
					<option value="发送消息">发送消息</option>
					<option value="添加管理员信息">添加管理员信息</option>
					<option value="修改管理员信息">修改管理员信息</option>
					<option value="删除管理员信息">删除管理员信息</option>
					<option value="管理员登录信息">管理员登录信息</option>
					<option value="添加策略">添加策略</option>
					<option value="删除策略">删除策略</option>
				</select>
			</div>
			
			
			<div style="width:350px;height:30px;float: left;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间段选择:
				<span></span><input class="easyui-datetimebox" name="begindate" id="begindate"    
        			data-options="required:true" value="2014-1-1 0:0:00"  style="width:100px">
        		<span>----</span><input class="easyui-datetimebox" name="enddate"  id="enddate"  
        			data-options="required:true" value="2014-6-23 0:0:00" style="width:100px">
			</div>
			<br><br>
			<div style="margin-top: 15px;padding-top: 10px; border-top: 1px solid #eee;">
				<div style="width: 200px;float: right;margin-right: 280px;">
				<a onclick="searchLogByCondition()" class="l-btn l-btn-plain opbt" style="text-align:center; height: 20px;width: 60px;">查询</a>
				<a onclick="exportFile()" class="l-btn l-btn-plain opbt" style="text-align:center;height: 20px;width: 60px;">导出</a>
				</div>
			
			</div>
			</div>
	  	 </fieldset>
	  </div>
	  <div style="width: 100%;height: 70%;float: right;">
	  		<table id="dg"></table>
	  </div>
  </body>
  <script type="text/javascript">
  	$(function (){
		getDataList();
		$('#enddate').datetimebox('setValue', '9999');
	});

	function getDataList(){
		$("#dg").datagrid({
			url:"logByPagingAction.action",
			columns:[[
				{field:'operationername',title:'管理员',align:'center'},
				{field:'operation',title:'操作'},
				{field:'content',title:'内容'},
				{field:'time',title:'操作时间',align:'center'}
			]],
			title:"管理员操作日志表",
			singleSelect: true,
		    pagination:true,
		    fit: true,
		    rownumbers:true,//行号  
		   	pageSize:30,
		   	striped: true
		});
		$("#dg").datagrid('fitColumns');
		
		var p = $("#dg").datagrid("getPager");//拿到当前页面对象  
	    $(p).pagination({  
	        pageSize: 30,//每页显示的记录条数，默认为10  
	        pageList: [10,15,20,30],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 记录   共 {total} 条记录'
	    }); 
	}
	function searchLogByCondition() {
		var name="";
		if($("#adminname").val() != ""){
			name=$("#adminname").val();
		}else {
			name="#";
		}
		if($("#optionname").val() != ""){
			name+=";"+$("#optionname").val();
		}else {
			name+=";#"
		}
		if($("#keywords").val() != ""){
			name+=";"+$("#keywords").val();
		}else{
			name+=";#";
		}
		
		name+=";"+$("#begindate").datetimebox('getValue')+"to"+$("#enddate").datetimebox('getValue');
		
		$("#dg").datagrid({
			url:'findLogByConditionAction.action',   
			queryParams:{
				condition:name		
			}
		});
		/* $("#dg").datagrid('reload');
		$("#dg").datagrid('loaded'); */
	}
	
	function exportFile(){
		var parame = "";
		var datas=$("#dg").datagrid("getData");
		
		var rows = datas.rows;
		
		for(var i=0;i<rows.length;i++) {
			parame+=rows[i].operationername+"\t"+
				rows[i].content+"\t"+
				rows[i].operation+"\t"+
				rows[i].time+"\n";
		}
		
		
		var form = $("<form>");   //定义一个form表单
       form.attr('style','display:none');   //在form表单中添加查询参数
       form.attr('target','');
       form.attr('method','post');
       form.attr('action',"exportLogAction.action");
       var input1 = $('<input>'); 
       input1.attr('type','hidden'); 
       input1.attr('name','parame'); 
       input1.attr('value',parame); 
       $('body').append(form);  //将表单放置在web中
       form.append(input1);   //将查询参数控件提交到表单上
       form.submit();   //表单提交
	}

  </script>
</html>
