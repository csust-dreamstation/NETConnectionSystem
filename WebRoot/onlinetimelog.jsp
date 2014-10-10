<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'onlinetimelog.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
				用户名:
				<input id="clientname" type="text" style="width: 50px;"/>
			</div>
			<div style="float: left;">
				ip:
				<input id="ip" type="text" style="width: 100px;"/>
			</div>
			<div style="float: left;">
				mac:
				<input id="mac" type="text" style="width: 150px;"/>
			</div>
			<div style="width:350px;height:30px;float: left;">
				时间段选择:
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
			url:"findAllOnlineRecordAction.action",
			columns:[[
				{field:'clientname',title:'用户名',align:'center'},
				{field:'ip',title:'ip地址'},
				{field:'mac',title:'mac地址'},
				{field:'begintime',title:'上线时间',align:'center',
					formatter: function(value,row,index){
						var startdate = new  Date(value);
						return startdate.toLocaleString();
					}
				},
				{field:'endtime',title:'下线时间',align:'center',
					formatter: function(value,row,index){
						if(value == 0){
							return "<span style='color:#ddd'>该客户端当前在线</span>";
						}else{
							var enddate = new  Date(value);
							return enddate.toLocaleString();
						}
					}
				},
				{field:'timestatment',title:'在线时长',align:'center',
					formatter: function(value,row,index){
						var endtime;
						if(row.endtime == 0){
							var now = new Date();
							endtime = now.getTime();
						}else{
							endtime = row.endtime;
						}
						//alert("endtime:"+endtime+"begin:"+row.begintime);
						var seconds = (endtime-row.begintime)/1000;
						//将时长付给timestatment以秒为单位
						value = seconds;
						var hour = Math.floor(seconds/3600);
						seconds = seconds%3600;
						var min = Math.floor(seconds/60);
						var s = Math.floor(seconds%60);
						//alert(hour+"小时"+min+"分钟"+s+"秒");
						return hour+"小时"+min+"分钟"+s+"秒";
						
					}
					
				}
			]],
			title:"客户端在线时间记录表",
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
		if($("#clientname").val() != ""){
			name=$("#clientname").val();
		}else {
			name="#";
		}
		if($("#ip").val() != ""){
			name+=";"+$("#ip").val();
		}else {
			name+=";#";
		}
		if($("#mac").val() != ""){
			name+=";"+$("#mac").val();
		}else{
			name+=";#";
		}
		name+=";"+$("#begindate").datetimebox('getValue')+"to"+$("#enddate").datetimebox('getValue');
		
		$("#dg").datagrid({
			url:'findOnlineTimeByConditionAction.action',   
			queryParams:{
				condition:name		
			}
		}); 
		//alert("condition is success:"+name);
	
	}
	
	function exportFile(){
		
		var parame = "";
		var datas=$("#dg").datagrid("getData");
		var rows = datas.rows;
		for(var i=0;i<rows.length;i++) {
			var start = new  Date(rows[i].begintime);
			var end = new  Date(rows[i].endtime);
			var seconds = (rows[i].endtime-rows[i].begintime)/1000;
			var hour = Math.floor(seconds/3600);
			seconds = seconds%3600;
			var min = Math.floor(seconds/60);
			var s = Math.floor(seconds%60);
				parame+=rows[i].clientname+"\t"+
				rows[i].ip+"\t"+
				rows[i].mac+"\t"+
				start.toString()+"\t"+
				end.toString()+"\t"+ 
				hour+"小时"+min+"分钟"+s+"秒"+"\n";
			
		}
		alert(parame);	
		
		
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
