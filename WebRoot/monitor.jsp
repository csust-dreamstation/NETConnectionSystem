<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'monitor.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/demo/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
  </head>
  <style type="text/css">
  	.list{
  		border: solid 2px red;
  	}
  </style>
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
  	var refreshDataId;
  	$(function (){
		getDataList();
		refreshDataId = setInterval("refreshData()",5000);
			
	});
	//用来记录被选中的行数//注：只能一开始就将行号记录下来，不能记录对象后用对象去查找，因为刷新后对象改变永远都找不到相同对象
  	var checkRowsIndex = [];
  	
	  function getDataList(){
			$("#dg").datagrid({
				url:"getOnlinePcPageAction.action",
			    pagination:true,
			    fit: true,
			    rownumbers:true,//行号  
			   	pageSize:30,
			   	toolbar: '#tb',//搜索栏
			   	striped: true,
			   	
			   	onCheck: function(rowIndex, rowData){
			   		showSendMan();
			   	},
			   	onUncheck:function(rowIndex, rowData){
			   		showSendMan();
			   	},
			   	onCheckAll:function(rows){
			   		showSendMan();
			   	},
			   	onUncheckAll:function(rows){
			   		$("#sendedman").val("");
			   	},
			   	onLoadSuccess:function(data){ 
			   		//alert(checkRs.length);
			   		if(checkRowsIndex.length != 0){
						$.each(checkRowsIndex,function(i,v){
							$('#dg').datagrid('checkRow',v);
						});
			   			
			   		}  
				}
			});
			
			var p = $("#dg").datagrid("getPager");//拿到当前页面对象  
		    $(p).pagination({  
		        pageSize: 30,//每页显示的记录条数，默认为10  
		        layout:['first','prev','next','last'],
		        displayMsg: '共 {total} 个用户在线',
		        showPageList: false
		    }); 
	}
	
	var sendman = "";
	var sendmanmac = [];
	//用来记录拿到的选中列方便刷新后继续更新
	var checkedRows = [];
	function showSendMan(){
		//每一次调用时先将数组清零
		sendman = "";
		sendmanmac = [];		
		checkedRows = $("#dg").datagrid('getSelections');
   		$.each(checkedRows,function(i,v){
   			sendman = sendman+v["clientname"]+"<"+v["ip"]+">"+";";
   			sendmanmac.push(v["mac"]);
   		});
   		$("#sendedman").val(sendman);
	}
	
	function refreshData(){
	//清空以前的行号
		checkRowsIndex = [];
		$.each(checkedRows,function(i,v){
			var index = $('#dg').datagrid('getRowIndex',v);
			checkRowsIndex.push(index);
		});
		$("#dg").datagrid('reload');
		//掩饰加载的痕迹
		$("#dg").datagrid('loaded');	
	}
	
	function sendOrder(){
	   if($("#sendedman").val() != ""){
	   		 var chk_value =[];
			  var chk_name = "";  
			  //拿到所有的选中的操作  
			  $('input[name="op"]:checked').each(function(){
			  		var v = $(this).val();
			  		if(v == 6 && $('#msgContent').val() == ''){
			  			alert('您还没有写发送的消息内容,请写好发送的内容!');
			  			return;
			  		}    
			   		chk_value.push($(this).val());
			   		chk_name =  chk_name+ $(this).parent().text()+"  ";   
			  }); 
			  //alert("chk_name:"+chk_name);
			  if(chk_value.length==0){
			  	window.alert("你还没有选择任何操作！请选择您的操作后再按命令发送按钮！");
			  }else{
			  	var flag = window.confirm("您确定要发送以下命令：\n"+chk_name+"\n给:\n"+sendman+"\n吗？");
			  	if(flag){
			  		$.each(sendmanmac,function(i,v){
			  			//alert(i+":"+v);
			  		});
			  		var sendcontent = $("#msgContent").val();
			  		//alert(sendcontent);
			  		$.ajax({
					   type: "POST",
					   url: "sendSomeCommodToSomeAction.action",
					   data: {
					   		macList:JSON.stringify(sendmanmac),
					   		optionList:JSON.stringify(chk_value),
					   		sendMessage:sendcontent
					   },
					   success: function(msg){
					   		 if(msg == "success"){
					   			writeLog(chk_name+'命令发送成功');
					   		}else{
					   			writeLog(chk_name+'命令发送失败');
					   		} 
					   		
			   			}
					});
			  	}
			  }
	   }else{
	   		$.messager.alert('警告',"请先选择接收命令的客户端");
	   }
	      
	}
	
	//命令发送记录
	function writeLog(content){
		var nowDate = new Date();
		$('#recordtable').datagrid('insertRow',{
			index: 1,	// 索引从0开始
			row: {
				msgcontent: content
			}
  		});
	}
	//
	function sendMsg(){
		//没有选择发送通知时该按钮不起作用
		if($("#msgContent").attr('readonly') == 'readonly' || $("#sendedman").val() == ""){
			return;
		}
		//如果接收按钮已经打开就不用打开了
		if($("#acceptMessage").text() == '接收消息'){
			acceptMsg();	
		}
  		var mContent = $("#msgContent").val();
  		mContent = trim(mContent);
  		if(mContent == ""){
  			return;
  		}
  		$.ajax({
		   type: "POST",
		   url: "sendMessageToSomeAction.action",
		   data: {
		   		macList:JSON.stringify(sendmanmac),
		   		msgContent:mContent
		   },
		   success: function(msg){
		     if(msg != "fail"){
		     	var now = new Date();
		     	showMsg("服务器","("+now.toLocaleString()+")"+mContent);
		     	$("#msgContent").val("");
		     }
   			}
		});
  	}
  		
   function getClientMsg(){
  		$.ajax({
		   type: "POST",
		   url: "getSomeMessageAction.action",
		   data: "macList="+JSON.stringify(sendmanmac),
		   success: function(msg){
		   		$.each( msg, function(i, n){
  					showMsg(n["clientname"],"("+n["recordtime"]+")"+n["content"]);
				});
		   }
		});
  	}
  	
  	var getClientMsgId = null;
  	function startGetClientMsg(){
  		getClientMsgId = setInterval(getClientMsg, 10000);
  	}
  	
  	function stopGetClientMsg(){
  		clearInterval(getClientMsgId);
  	} 
  	
  	 function acceptMsg(){
  		if($("#sendedman").val() == "" ){
  			$.messager.alert('警告','没有选择显示接收消息的客户端！');    
			return;
		}
  		if($("#acceptMessage").text() == '接收消息'){
  			startGetClientMsg();
  			$("#acceptMessage").text('停止接收消息');
  		}else{
  			stopGetClientMsg();
  			$("#acceptMessage").text('接收消息');
  		}
  	}
  	
  	function showMsg(name,content){
  		var tmpContent = $("#sendedMsg").val();
  		if(tmpContent == ""){
  			$("#sendedMsg").val(name+":"+content);
  		}else{
  			$("#sendedMsg").val(tmpContent+"\n"+name+":"+content);
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
  <body>
  <div class="easyui-layout" style="width:100%;height:100%;">
		<div data-options="region:'east',split:true,width:247,collapsible:false" title="在线主机" style="width:100px;">
			<div id="list" style="width:100%;height:100%">
			  <table id="dg" class="easyui-datagrid" data-options="method:'post'">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'clientname',width:50">用户名</th>
						<th data-options="field:'ip',width:112">ip地址</th>
					</tr>
				</thead>
			  </table>
				<div id="tb">
					<input id="ss" class="easyui-searchbox" style="width:200px" 
							data-options="searcher:doSearch,prompt:'请输入查询的值',menu:'#mm'"></input> 
					<div id="mm" style="width:120px"> 
						<div data-options="name:'clientname'">用户名</div>
						<div data-options="name:'ip'">ip地址</div>   
					</div> 
					<script type="text/javascript">	
						function doSearch(value,name){
							$("#dg").datagrid({
								url:'findPcinfoByConditionAndPageAction.action',   
								queryParams:{
									condition:name,
									conditionValue:value					
								}
							});
						} 
					</script> 
				</div>
			</div>
		</div>
		<div data-options="region:'center'" style="padding: 1%;">
			<div style="width: 100%;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 60px;font-weight: bold;"><span>发 送 到:</span></td>
						<td><textarea style="width: 100%;overflow:hidden;border:1px solid #ADD8E6;" rows="2" id="sendedman" value="" readonly="readonly"></textarea></td>
					</tr>
				</table>
			</div>
			<div style="width: 100%;height: 67%;margin-top: 2px;display: inline-block;">
				<div id="p2" class="easyui-panel" title="实时命令"
					style="padding:1%;">
					<div style="width: 30%;height: 100%;display: inline-block;">
						<fieldset style="padding: 7%;font-weight: bold;border: 1px solid #ADD8E6;">
							<legend>命令选择</legend>
							<span><input type="checkbox" name="op" value="6" 
								onclick="javascript:
									if(this.checked == true){
										$('#msgContent').removeAttr('readonly');
									}else{
										$('#msgContent').attr({readonly:'readonly'});
										$('#msgContent').val('');
									}"/>发送消息</span><br><br>
	                		<span><input type="checkbox" name="op" value="7" />立即关机</span><br><br>
	                		<span><input type="checkbox" name="op" value="4"  />取消黑名单</span><br><br>
	                		<span><input type="checkbox" name="op" value="2"  />取消白名单</span><br><br>
	                		<span><input type="checkbox" name="op" value="2"  />取消违规外连检测</span><br><br>
		                	<div style="border-top: 1px solid #ADD8E6;width: 100%;height: 29%;padding-top: 18px;">
								 <a id="btn" style="width:60%;text-align:center;height:21px;font-size: 13px;" 
								 		 class="l-btn l-btn-plain opbt" onclick="sendOrder()">发送命令</a>
								 <br>
							</div>
						</fieldset>
					</div>
					<div style="width: 69%;height: 90%;float: right;">
						<div id="p3" class="easyui-panel" title="消息" style="padding:1%;">
							<div id="display" style="width:100%;">
							   	<textarea style="width: 100%;border: 1px solid #ADD8E6;overflow: auto;" name="sendedMsg" 
							   		id="sendedMsg" readonly="readonly"  value="" rows="6"  readonly="readonly"></textarea>
							 </div>
							 <div style="width:100%;height: 25px;background-color: #ADD8E6;">
							 	 <a style="text-align:center;float: right;position: relative;right: 20px;top:4px;" 
								 		 class="l-btn" onclick="acceptMsg()" id="acceptMessage">接收消息</a>
							 </div>
							 <div>
						   		<textarea rows="3"style="width: 100%;border: 1px solid #ADD8E6;overflow: auto;" name="msgContent" 
						   			id="msgContent" readonly="readonly"></textarea>
						   		<br>
						   		<div style="width:100%; height: 20px;background-color: #ADD8E6; padding-top: 4px;">
						   			 <a style="text-align:center;float: right;position: relative;right: 20px;" 
								 		 class="l-btn" onclick="sendMsg()">发送消息</a>
						   		</div>
						   	</div>
						</div>
					</div>
				</div>
			</div>
			<div style="width: 100%;height:22%; margin-top:2px;">
				<table id="recordtable" class="easyui-datagrid" data-options="fit:true,striped: true" style="width: 100%;">
					<thead>
						<tr>
							<th data-options="field:'msgcontent',align:'center'">命令发送记录</th>
						</tr>
					</thead>
				</table> 
			</div>
		</div>		
	</div>
  </body>
</html>
