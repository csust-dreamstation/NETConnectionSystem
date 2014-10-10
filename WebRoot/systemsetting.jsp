<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'systemsetting.jsp' starting page</title>
     
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

  </head>
  <script type="text/javascript">
   $(function (){
   		if("${message}" == "添加失败"){
   			$.messager.alert('警告',"${message}");
   		}
		getDataList();
		var id="<%=session.getAttribute("type")%>"; 
		$("#type_wifichk").combobox("select",id);
		$("#dnstime").combobox("setValue","<%=session.getAttribute("dnstime")%>");
	});

	function getDataList(){
		$("#dg").datagrid({
			url:"findAllUserAction.action",
			columns:[[
				{field:'username',title:'用户名',align:'center',width:100,
					formatter: function(val,row,index){
						var html = "<input type='text' id='preUserName_"+index+"'  style='border:0px;;width:60px;text-align:center' value='"+val+"' disabled='disabled' />"
						return html;
					}
				},
				{field:'password',title:'密码',align:'center',width:160,
					formatter: function(val,row,index){
						var html = "<input type='password' id='prepassword_"+index+"'  style='border:0px;;width:100px;' value='"+val+"' disabled='disabled' />"
						return html;
					}
				},
				{field:'operator',align:'center',title:'操作',width:120,
					formatter: function(val,row,index){
						var html = "<a class='l-btn	 l-btn-plain opbt' onclick='updateUser("+index+")' id='a_"+index+"'>修改</a>"+
									"<a class='l-btn l-btn-plain opbt' onclick='deleteUser()' style='margin-right: 10px;'>删除</a>"
						return html;
					}
				}
			]],
			singleSelect: true,
		    fit: true
		});
		$("#dg").datagrid('reload');
		$("#dg").datagrid('fitColumns');
	}
	
	function updateUser(index){	
		var btnName = $("#a_"+index).text();
		if( $("#preUserName_"+index).val() != '${user.username}'){
			$.messager.alert('警告',"只能修改自己的个人信息！");
			return;
		} 
		
		if(btnName == "修改"){
			if('${user.username}' != 'admin'){
				$("#preUserName_"+index).removeAttr("disabled");	
			}
			$("#prepassword_"+index).removeAttr("disabled");
			$("#a_"+index).text("保存");
		}
		if(btnName == "保存"){
			var selectedRow = $("#dg").datagrid('getSelected');
			var userName = $("#preUserName_"+index).val();
			
			if(isExitUserName(userName,selectedRow.id) == true){
				$.messager.alert("警告","该用户名已经存在，请输入其他用户名！");
				return;
			}
			
			var password = $("#prepassword_"+index).val();
			if(password == ""){
				$.messager.alert("警告","密码不能为空！");
				return;
			}
			if(password.length<6){
				$.messager.alert('警告','密码长度不足，请重新输入');
				return;
			}
			if(password.match(/[a-z]/g)&&password.match(/[0-9]/g)||password.match(/[A-Z]/g)&&password.match(/[0-9]/g)){
				$.ajax({
					   type: "POST",
					   url: "updateUserAction.action",
					   data: {
					   	  userId:selectedRow.id,
					   	  userName:userName,
					   	  password:password
					   }
					});
					//更新数据
					$('#dg').datagrid('load');
			}
			else{
				$.messager.alert('警告','密码强度过低，请重新输入');
				return;
			}

		}
		
	}
	
	function deleteUser(){
		if('${user.username}' != "admin"){
			$.messager.alert('警告','您没有删除管理员的权限!');
			return;
		}
		
		var sRow = $("#dg").datagrid('getSelected');
		if(sRow.username == 'admin'){
			$.messager.alert('警告','该管理员为系统超级管理员不能删除！');
		}else{
			$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
			    if (r){    
			       $.ajax({
					   type: "POST",
					   url: "deleteUserAction.action",
					   data: {
					   	  userId:sRow.id
					   }
				   });  
				   //操作成功后删除该列
				   var i = $('#dg').datagrid('getRowIndex',sRow);
				   $('#dg').datagrid('deleteRow',i); 
			    }  
			});
		}
		
	}
	
	//判断该用户名是否存在
	function isExitUserName(userName,id){
		var bool = false;
		var dataRows = $('#dg').datagrid('getRows');
		$.each(dataRows,function(index,value){
			if(value.id != id && userName == value.username){
				bool = true;
				return;
			}
		});
		return bool;
		
	}
	
	function validate(){
		var bool = ($("#userName").val() != "")&&($("#password").val() != "")&&($("#repassword").val() != "")
		if(bool == false){
			$.messager.alert('警告','用户名、密码和确认密码不能为空。'); 
			return false;
		}else{
			if(isExitUserName($("#userName").val(),0) == true){
				$.messager.alert('警告','该用户名已经存在,请重新输入。');
				return false;	
			}
			if($("#password").val().length<6){
				$.messager.alert('警告','密码长度少于六位，请重新输入。');
				return false;
			}
			if($("#password").val() != $("#repassword").val()){
				$.messager.alert('警告','两此密码输入不一致,请重新输入。');
				return false; 
			}
			if($("#password").val().match(/[a-z]/g)&&$("#password").val().match(/[0-9]/g)||$("#password").val().match(/[A-Z]/g)&&$("#password").val().match(/[0-9]/g)){
				
				return true;
			}
			else{
				$.messager.alert('警告','密码不能由纯字母或纯数字组成，请重新输入。');
				return false;
			}
			
		
		}
		return true;
	}
	function validateDns(){
		if(/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test($("#dnsadress").val())&&$("#dnsport").val()>=1&&$("#dnsport").val()<=65534){
			return true;
		}
		else {
			alert("请正确格式填写ip");
			return false;}
		return false;
	}
	function validateFlow(){
		if($("#upWarning").val()<0||$("#upWarning").val()>214783647||$("#loadWarning").val()<0||$("#loadWarning").val()>214783647)
		{alert("请正确填写");
		return false;}
		else 
		return true;
	}
	function setvalue(){
		$.messager.prompt('自定义频率','请输入频率:',function(v){
			if (v.match(/[0-9]/g)&&v){
				$('#dnstime').combobox('setValue',v);
			}
		});
		
	}
	function validateServer(){
		if($("#d_day").val()>9999||$("#d_day").val()<0)
			{alert("天数不能大于9999或为负数");
			return false;
			}
		else 
		return true;
	}
  </script>
  <style type="text/css">
  		.opbt{
  			margin-left: 10px;
  			background-color: #ADD8E6;
  			color: white;
  		}
  </style>
  
  <body>
	  <div style="width: 57%;height: 99%;display: inline-block;">
	  	<div id="p1" class="easyui-panel" title="管理员设置" style="padding: 3%"  
        data-options="closable:false,    
                collapsible:false,minimizable:false,maximizable:false">   
   			<fieldset style="padding: 3%;">
   				<legend>添加管理员</legend>
   				<div style="width:380px;height:150px;">
   				<div style="width:230px;height:150px;float:left;">
   				<form action="addUserAction.action" onsubmit="return validate()">
   					用户名：&nbsp;&nbsp;&nbsp;<input type="text" id="userName" name="userName" style="border:2px width: 30px;"/>
   					<br>密码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" id="password" name="password" style="border:2px width: 60px;"/>
   			  		<br>确认密码：<input type="password" id="repassword" style="border:2px width: 60px;"/>
   					<br><br><br>
   					<div >
	   					<input type="submit" value="添加" class="l-btn l-btn-plain opbt" style="width: 80px;height: 25px;"/>
	   					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   					<input type="reset" value="重置" class="l-btn l-btn-plain opbt" style="width: 80px;height: 25px;"/>
   					</div>
   				</form>
   				</div >
   				<div style="width:150px;height:150px;float:right;color:red;">
   				<p>注意：</p>
   				<p>密码长度至少有六位
   				密码不能由纯字母或纯数字组成</p>
   				</div>
   				</div>
   			</fieldset>
   			
   			<fieldset style="padding: 3%;margin-top: 3%;width: 93%;height: 30%;">
   				<legend>删除和修改管理员</legend>
   				 <div style="height: 90%;">
	  					<table id="dg"></table>
	 			 </div>
   			</fieldset>
   			
   			<fieldset style="padding: 3%;margin-top: 3%;width: 93%;height: 17%;">
   				<legend>pc类型及网卡数量策略配置</legend>
   				<br>
				<br>
   				 <div style="height: 90%;">
   				<form action="checkWifiAction.action" method="post">
	  				<div style="margin-right:0px;">
	  				 上网检测策略配置
					<select class="easyui-combobox" id="type_wifichk" name="type_wifichk" style="width:210px;">
						<option value="0">不做处理</option>
						<option value="1">禁止所有笔记本</option>
						<option value="2">禁止拥有无线网卡的pc</option>
						<option value="3">禁止含两块及以上的无线网卡的笔记本</option>
						<option value="4">禁止含一块及以上的无线网卡的台式机</option>
					</select>
					</div>
					<br>
					<br>
					<div style="border-top:thin width: 100%;padding-top: 13px;">
	   						<input type="submit" value="保存设置" class="l-btn l-btn-plain opbt" style="width: 80px;height: 25px;margin-left: 150px;"/>		 
	   				</div>
	   			</form>
	 			 </div>
   			</fieldset>
		</div>  	
	  </div>
	 <div style="width: 41%;height: 99%;float: right;" >
		<div style="height: 210px;" >
		  	<div id="p2" class="easyui-panel" title="流量警告" style="padding: 2%"  
	        data-options="closable:false,collapsible:false,minimizable:false,maximizable:false">
                <fieldset style="padding: 4%;">
	   				<legend>流量警告参数设置</legend>
	   				<form action="saveFlowWarningSettingAction.action" method="post" onsubmit="return validateFlow()">
	   					<span>警告值:</span><br>
	   					<div>
		   					上行:&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="upWarning" name="upWarning" style="border:2px width: 50px;" value="${upWarning}" class="easyui-numberbox"  />kB/s
		   					<br>
		   					下行:&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="loadWarning" name="loadWarning" style="border:2px width: 50px;" value="${loadWarning}" class="easyui-numberbox" />kB/s
	   					</div>
						<div>
						<br>
							警告消息内容：<br>
							<textarea name="warningContent" style="height:30px; width: 220px;margin-left:40px;">${warningContent}</textarea>
						</div>
	   					<div style="border-top:thin width: 100%;padding-top: 0px;">
	   						<input type="submit" value="保存设置" class="l-btn l-btn-plain opbt" style="width: 80px;height: 25px;margin-left: 100px;"/>		 
	   					</div>
	   				</form>
	   			</fieldset>
	         </div>
		</div> 
		
	   <div style="width: 100%;height: 210px;" >
		<div style="height: 210px;" >
		  	<div id="p2" class="easyui-panel" title="违规联网检测" style="padding: 3%"  
	        data-options="closable:false,collapsible:false,minimizable:false,maximizable:false">
                <fieldset style="padding: 4%;">
	   				<legend>违规联网检测参数设置</legend>
	   				<form action="saveDnsSettingAction.action" method="post" onsubmit="return validateDns()">
	   					<div>
		   					<span>DNS-IP:&nbsp;&nbsp;<input type="text" name="dnsadress" id="dnsadress" style="border:2px width: 150px;" value="${dnsadress}"/></span>
		   					<br>
		   					端口:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="dnsport" id="dnsport" style="border:2px width: 100px;" value="${dnsport}" class="easyui-numberbox" />
	   					    <br>
	   					  
	   					           检测频率：
		   					<select id="dnstime" name="dnstime" class="easyui-combobox" style="border:2px;width:50px;">
										<option value="5">5</option>
										<option value="15">15</option>
										<option value="20">20</option>
										<option value="25">25</option>
										<option value="30">30</option>
										<option value="40">40</option>
										
						 </select>秒
						  <a href="javascript:void(0)" class="easyui-linkbutton" onclick="setvalue()">自定义频率</a>
						 </div>
	   					<div style="border-top:thin width: 100%;padding-top: 13px;">
	   						<input type="submit" value="保存设置" class="l-btn l-btn-plain opbt" style="width: 80px;height: 25px;margin-left: 100px;"/>		 
	   					</div>
	   				</form>
	   			</fieldset>
	         </div>
		</div> 
		</div>
		
	  	<div style="height: 210px;margin-top: 3px;" >
			<div id="p3" class="easyui-panel" title="服务器配置" style="padding: 2%"  
			 data-options="closable:false,collapsible:false,minimizable:false,maximizable:false">
			       <fieldset style="padding-left: 5%;padding-right: 5%;">
						<legend>服务器参数设置</legend>
			
						<form action="saveServerSettingAction.action" method="post" name="form1" onsubmit="return validateServer()">
							 端口号：<input type="text" id="serverPort" class="easyui-numberbox" 
							 data-options="min:1,max:65535,precision:0"
							 name="serverPort" style="border:2px width: 50px;" value="${serverPort}"/>
							<br>
							消息处理时间：<select id="messageDealTime" name="messageDealTime" style="border:2px ">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
									  </select>秒<br/>
									  <script type="text/javascript">
									  	 $("#messageDealTime").val('${messageDealTime}');
									  </script>
							定时清除日志：<select id="date" name="date" onchange="gettime(this)" >
							            <c:if test="${deltype==1}"><option value="1" name="day" selected="selected">每天</option></c:if>
							            <c:if test="${deltype!=1}"><option value="1" name="day">每天</option></c:if>
								        <c:if test="${deltype==2}"><option value="2" name="week" selected="selected">每周</option></c:if>
							            <c:if test="${deltype!=2}"><option value="2" name="week">每周</option></c:if>
							            <c:if test="${deltype==3}"><option value="3" name="month" selected="selected">每月</option></c:if>
							            <c:if test="${deltype!=3}"><option value="3" name="month">每月</option></c:if>
							            <c:if test="${deltype==4}"><option value="4" name="year" selected="selected">每年</option></c:if>
							            <c:if test="${deltype!=4}"><option value="4" name="year">每年</option></c:if>								    	
								    </select>
								    <select id="shi" name="shi">
								    	<c:if test="${deltype==1 }"><option value="${deldate }"selected="selected">${deldate }时</option></c:if>
								    	<c:if test="${deltype==2 }"><option value="${deldate }"selected="selected">周${we}</option></c:if>
								    	<c:if test="${deltype==3 }"><option value="${deldate }"selected="selected">第${deldate }天</option></c:if>
								    	<c:if test="${deltype==4 }"><option value="${deldate }"selected="selected">${deldate }月</option></c:if>
								    </select>
								    <br/>
								    过期数据清除：每<input type="text" class="easyui-numberbox" style="width:50px;" name="d_day" id="d_day" value="<%=session.getAttribute("d_day")%>"/>天
							<div style="border-top:thin width: 100%">
								<input type="submit" value="保存设置" class="l-btn l-btn-plain opbt" style="width: 80px;height: 20px;margin-top: 15px;"/>
								<a href="backupDataBaseAction.action" class="l-btn l-btn-plain opbt" style="height: 20px;text-align: center;margin-top: 15px;margin-left: 50px;" >备份服务器</a>
							</div>
						    <script type="text/javascript">
								function gettime(obj){
									    var str=["一","二","三","四","五","六","日"];
										var val = obj.value;
										//获取date下拉框对象
										var sltData=document.form1.date; 
										
										//获得shi下拉框的对象   
										var sltShi=document.form1.shi; 
										
										//清空下拉表
										sltShi.length=0;
										//添加
										if(val==4){
										for(var i=0;i<12;i++){
										    sltShi[i]=new Option((i+1)+"月",i+1);
										}}
										if(val==3){
										for(var i=0;i<31;i++){
										sltShi[i]=new Option("第"+(i+1)+"天",i+1);
										}}
										if(val==2){
										for(var i=0;i<7;i++){
										sltShi[i]=new Option("周"+str[i],i+1);
										}}
										if(val==1){
										for(var i=0;i<24;i++){
										sltShi[i]=new Option(i+"时",i);
										}}
										
								}
							</script>
						</form>
				  </fieldset>
		   </div>
	  </div>
	  </div>
  </body>
</html>
