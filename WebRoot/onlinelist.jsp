<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'onlinelist.jsp' starting page</title>
    
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
	<!-- <script type="text/javascript" src="js/onlinelist.js" charset="UTF-8"></script> -->
	<script type="text/javascript">
	var refreshDataId;
	$(function (){
		getDataList();
		refreshDataId = setInterval("refreshData()",5000);	
	});

	function getDataList(){
		var flowWarningClient;
		$("#dg").datagrid({
			url:"getAllPCByPageAction.action",
			columns:[[
				{field:'clientname',title:'用户名',align:'center',
					formatter: function(val,row,index){
						if(row.onlinestate == '0'){
							return "<span style='color:red'>"+val+"</span>";
						}else{
							var html = "<a href='javascript:pcinfo("+index+")' style='color:green'>"+val+"</a>";
							return html;
						}
					}
				},
				{field:'ip',title:'ip地址',align:'center'},
				{field:'mac',title:'网卡地址',align:'center'},
				{field:'os',title:'操作系统版本',align:'center'},
				{field:'upflow',title:'上行流量(KB/S)',align:'center'},
				{field:'loadflow',title:'下行流量(KB/S)',align:'center'},
				{field:'onlinestate',align:'center',title:'在线状态',
					formatter: function(val,row,index){
						var html;
						if(val == '0'){
							html = "<span style='color:red'>不在线</span>";
						}else{
							html = "<span style='color:green'>在线</span>"
						}
						return html;
					}
				},
				{field:'operator',align:'center',title:'操作',
					formatter: function(val,row,index){
						var html;
						if(row.onlinestate == '0' ){
							 html = "<a class='l-btn	 l-btn-plain opbt' onclick='downline()' style='background-color: graytext;'>发消息</a>"+
									"<a class='l-btn l-btn-plain opbt' onclick='downline()' style='background-color: graytext;'>截屏</a>"+
									"<a class='l-btn l-btn-plain opbt' onclick='downline()' style='background-color: graytext;'>立即关机</a>";
						}else{
							 html = "<a class='l-btn	 l-btn-plain opbt' onclick='createMessageWindow("+index+")'>发消息</a>"+
									"<a class='l-btn l-btn-plain opbt' onclick='sendCopyScreen("+index+")'>截屏</a>"+
									"<a class='l-btn l-btn-plain opbt' onclick='sendShutDown("+index+")'>立即关机</a>";						
						}
						return html;
					}
				},
				{field:'warnstate',align:'center',title:'流量警告状态',hidden:'true',
					formatter: function(val,row,index){
						if(row.onlinestate == '1' ){
							if(val == '1'){
								flowWarn(row.clientname,'上行');	
							}else if(val == '2'){
								flowWarn(row.clientname,'下行');	
							}else if(val == '3'){
								flowWarn(row.clientname,'上行、下行');	
							}
						}
					}
				}
			]],
			singleSelect: true,
		    pagination:true,
		    fit: true,
		    rownumbers:true,//行号  
		   	pageSize:30,
		   	toolbar: '#tb',//搜索栏
		   	striped: true
		});
		$("#dg").datagrid('fitColumns');
		statistics();		
	}
	
	function pcinfo(i){
		$('#dg').datagrid('selectRow',i);
  		var sr = $("#dg").datagrid('getSelected');
		//alert(sr.mac);
    	$('#pcinfo').dialog({    
		    title: '客户端详细信息',     
		    width: 450,    
		    height: 300,    
		    closed: false,    
		    cache: false,    
		    href: 'pcinformationAction.action?mac='+sr.mac,    
		    modal: true
		});    
		$('#cs_img').dialog('refresh', 'pcinformationAction.action?mac='+sr.mac);  	
	}
	//拿到统计数据
	function statistics(){
		$.ajax({
			type:"POST",
			url:"getStatisticsAction.action",
			success:function(msg){
				//分页信息
				var p = $("#dg").datagrid("getPager");//拿到当前页面对象  
			    $(p).pagination({  
			        pageSize: 30,//每页显示的记录条数，默认为10  
			        pageList: [10,15,20,30],//可以设置每页记录条数的列表  
			        beforePageText: '第',//页数文本框前显示的汉字  
			        afterPageText: '页    共 {pages} 页',  
			        displayMsg: '当前显示 {from} - {to} 客户端   共 {total} 台电脑    '+msg
			    });
			}
		});
	}
	
	//对不在线用户不支持任何操作
	function downline(){
		$.messager.alert('警告','该用户不在线，您不能向他发送任何请求！');
	}
	
	//流量警告消息
	function flowWarn(clientname,warncontent){
		$.messager.show({
			title:'流量警告',
			msg:clientname+warncontent+'流量超过警告值!',
			timeout:3000,
			showType:'slide'
		});
	}
	
	function refreshData(){
		$("#dg").datagrid('reload');
		$("#dg").datagrid('loaded');
		statistics();
	}
	
	</script>
  </head>
  <script type="text/javascript">
	function trim(str){ //删除左右两端的空格
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	function ltrim(str){ //删除左边的空格
	   return str.replace(/(^\s*)/g,"");
	}
	 function rtrim(str){ //删除右边的空格
	  return str.replace(/(\s*$)/g,"");
	 }
</script>
  
  <script type="text/javascript">
	//message.jsp中的js
	var getClientMsgId = null;
	var selectedRow = null;
	
	function createMessageWindow(index){
		$('#win').removeData();
		$('#win').window({    
		    title: '消息',    
		    width: 400,    
		    height: 300,    
		    closed: false, 
		    minimizable: false,     
		    href: 'message.jsp', 
		    modal: true,
		    onOpen: function(){
		    	load(index);
	    		getClientMsgId = setInterval("getClientMsg()",2000);
		    },
		    onClose: function(){
		    	clearInterval(getClientMsgId);
		    } 
		});
		$('#win').window('refresh', 'message.jsp');  
	}
	
  	function load(index){
  		$('#dg').datagrid('selectRow',index);
  		selectedRow = $("#dg").datagrid('getSelected');
  		$("#msgContent").focus();
  	}
  		
  	function sendMsg(){
  		
  		var msgContent = document.getElementById("msgContent").value;
  		msgContent = trim(msgContent);
  		var mac = selectedRow.mac;
  		if(msgContent == ""){
  			return;
  		}
  		$.ajax({
		   type: "POST",
		   url: "sendMessageAction.action",
		   data: "mac="+mac+"&msgContent="+msgContent,
		   success: function(msg){
		   //	alert(msg);
		     if(msg != "fail"){
		     	var now = new Date();
		     	showMsg("服务器","("+now.toLocaleString()+")"+msgContent);
		     	$("#msgContent").val("");
		     }
   			}
		});
  	}	
  	function getClientMsg(){
  		var clientname = selectedRow.clientname;
  		var mac = selectedRow.mac;
  		$.ajax({
		   type: "POST",
		   url: "getMessageAction.action",
		   data: "mac="+mac,
		   success: function(msg){
		   		$.each( msg, function(i, n){
  					showMsg(clientname,"("+n["recordtime"]+")"+n["content"]);
				});
		   }
		});
  	}
  	
  	function showMsg(name,content){
  		var tmpContent = $("#sendedMsg").val();
  		if(tmpContent == ""){
  			$("#sendedMsg").val(name+":"+content);
  		}else{
  			$("#sendedMsg").val(tmpContent+"\n"+name+":"+content);
  		}
  		
  	}
  	
  	//截屏的命令
  	var copyscreenfunctionId;
  	var copytime;
  	function sendCopyScreen(index){
  		$('#dg').datagrid('selectRow',index);
  		selectedRow = $("#dg").datagrid('getSelected');
  		var mac = selectedRow.mac;
  		var op = window.confirm("您是否要求"+selectedRow.clientname+"截屏！");
  		if(op){
  			$.ajax({
			   type: "POST",
			   url: "sendCommodAction.action",
			   data: "mac="+mac+"&optionType=8",
			   success: function(msg){
			   		if(msg == 'success'){
			   			$.messager.show({
							title:'提示',
							msg: selectedRow.clientname+'的截屏命令已发送成功，请耐心等待！',
							showType:'show',
							style:{
								right:'',
								top:document.body.scrollTop+document.documentElement.scrollTop,
								bottom:''
							}
						});
			   			
						 //发送截屏命令成功后获得截屏的图片
						 copytime = new Date();
						 copyscreenfunctionId = window.setInterval("getCopyScreen()",5000);
			   			
			   		}else{
			   			$.messager.show({
							title:'提示',
							msg: selectedRow.clientname+'的截屏命令发送失败，请重新发送！',
							showType:'show',
							style:{
								right:'',
								top:document.body.scrollTop+document.documentElement.scrollTop,
								bottom:''
							}
						});
			   		
			   		}
			   			
	   		  }
			});
  		}else{
  			return;
  		}
		
		
	}
	//取得截屏的图片并显示
	function getCopyScreen(){
		$.ajax({
		   type: "POST",
		   url: "copyScreenByOneAction.action",
		   data: "mac="+selectedRow.mac+"&time="+copytime.getTime(),
		   success: function(msg){
		     if(msg != "fail"){
		    	clearInterval(copyscreenfunctionId);
		    	$('#cs_img').dialog({    
				    title: '截屏',     
				    width: 650,    
				    height: 450,    
				    closed: false,    
				    cache: false,    
				    href: 'copyscreen.jsp',    
				    modal: true,
				    onLoad: function(){
				    	$("#copyscreen_img").attr({ src: "copyscreen/"+msg+".bmp"});
				    },
				    onClose: function(){
				    	$.ajax({
						   type: "POST",
						   url: "deleteCopyScreenAction.action",
						   data: "mac="+selectedRow.mac
						});
				    }  
				});    
				$('#cs_img').dialog('refresh', 'copyscreen.jsp');  	
		     }
   			} 
		});
	}
	
	//发送关机的操作
	function sendShutDown(index){
	 	$('#dg').datagrid('selectRow',index);
		selectedRow = $("#dg").datagrid('getSelected');
  		var mac = selectedRow.mac;
  		var op = window.confirm("您是否要求"+selectedRow.clientname+"立即关机！");
  		if(op){
  			$.ajax({
			   type: "POST",
			   url: "sendCommodAction.action",
			   data: "mac="+mac+"&optionType=7",
			   success: function(msg){
					alert("立即关机请求发送成功！");
	   		  }
			});
  		}else{
  			return;
  		}
	}
  	
  </script>
  <style type="text/css">
  		.opbt {
  			margin-left: 10px;
  			background-color: #ADD8E6;
  			color: white;
  		}
  </style>
  <style type="text/css">
	table[name="t"]{border-collapse:collapse; margin:0 auto;border: 1px #ADD8E6 solid; }
	table[name="t"] tr td{border:1px #eee solid; padding:5px; width:200px}
	table[name="t"] tr{background:#F0F8FF}
  </style>
  <body>
	<table id="dg" class="easyui-datagrid" title="在线监控列表"></table>
	<div id='win'></div>
	<div id="tb">		
		<span>查询条件：</span><input id="ss" class="easyui-searchbox" style="width:300px" 
				data-options="searcher:doSearch,prompt:'请输入查询的值',menu:'#mm'"></input> 
		<div id="mm" style="width:100px"> 
			<div data-options="name:'clientname'">用户名</div>
			<div data-options="name:'ip'">ip地址</div> 
			<div data-options="name:'mac'">网卡地址</div>	  
		</div> 
		<script type="text/javascript"> 	
			function doSearch(value,name){
				$("#dg").datagrid({
					url:'findPcinfoByConditionAndPageAction.action',   
					queryParams:{
						condition:name,
						conditionValue:value					
					},
				});
				paging(); 
			} 
			
			function back(){
				$('#dg').datagrid({url:'getAllPCByPageAction.action'});
				paging();  
			}
		</script> 
		<a href="#" title="返回全部在线客户端" class="easyui-linkbutton easyui-tooltip" data-options="iconCls:'icon-back',plain:true" 
			onclick="back()" />
	</div>
	<div id="cs_img"></div>
	<div id="pcinfo"></div>
  </body>
</html>
