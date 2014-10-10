<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>demo</title>
    
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
	<script type="text/javascript">
	$(function (){
		getBlackList01();
		getPatchList01();
	});
	function getPatchList01(){
		var tacticsname="默认策略";
		 var urlaction="";
		 urlaction="getPatchListAction.action?tacticsname="+encodeURIComponent(tacticsname);
		$("#patchtable").datagrid({
			url:urlaction,
			columns:[[
				{ field:'ck',checkbox:true },
				{field:'patchname',width:100,title:'补丁名称',align:'center'},
				{field:'degree',width:140,title:'严重程度',align:'center'},
				{field:'os',width:140,title:'OS版本',align:'center'},
			]],
		    singleSelect: false,
		    pagination:true,
		    fit: true,
		    rownumbers:true//行号      
		});
	}
	function getBlackList01(){
		var tacticsname="默认策略";
		 var urlaction="";
		 urlaction="getBlackListAction.action?tacticsname="+encodeURIComponent(tacticsname);
		$("#blacktable").datagrid({
			url:urlaction,
			columns:[[
				{ field:'ck',checkbox:true },
				{field:'softname',width:100,title:'软件名称',align:'center'},
				{field:'threadname',width:100,title:'线程名称',align:'center'},
				
			]],
			singleSelect: false,
		    pagination:true,
		    fit: true,
		    rownumbers:true//行号      
		});  

	}

	function deleteSoftList01(){
	    var rows = $('#blacktable').datagrid('getSelections');
	    var patchrows=$('#patchtable').datagrid('getSelections');
	    if (rows){
	    		var paramsId="";
	    		var patchId="";
	    		for(var i=0;i<rows.length;i++)
	    		{
	    			if(rows[i].softid!=null)
	    			{
		    			paramsId=paramsId+rows[i].softid;
		    			if(i<rows.length-1){
		    				paramsId=paramsId+",";
		    			}
	    			}
	    			//alert(rows[i].recordId);
	    		}
	
	     		for(var j=0;j<patchrows.length;j++)
	    		{
	    			if(patchrows[j].patchid!=null)
	    			{
	    				patchId=patchId+patchrows[j].patchid;
		    			if(j<patchrows.length-1){
		    				patchId=patchId+",";
		    			}
	    			}
	    			//alert(rows[i].recordId);
	    		}
				$.messager.confirm('Confirm','将会删除勾选的软件与补丁，确定删除吗?',function(r){
			    if (r){
			    		$.post('deleteSoftAction.action',
			    			{recordIds:paramsId,
			    			patchid:patchId},
			    			function(result){
			    				if(result=="信息删除成功")
			    				{
			    					getBlackList01();
									getPatchList01();
			    				}
			    				if(result=="请选择要删除的软件或补丁")
			    				{
			    					alert("请选择要删除的软件或补丁！");
			    					getBlackList01();
									getPatchList01();
			    				}
			    				
			    			}
			    		);
			    		
					  }
				});
	    }

	}
	</script>
  </head>

  <body style="font-family:microsoft yahei;font-size:25px;">
  			<s:action name="OsListAction" id="OsList">
			</s:action>
			<div id="ta" class="easyui-tabs"  style=" width:380px; height:500px;float:left;"  >
					<div title="添加基本信息" style="padding:20px;overflow:hidden;" id="home">
									<form action="src/com.netconnection.action/addSoftListAction.action" method="post" style="padding-left:60px;"  >
									<div style="font-weight:bold">软件名称</div>
									<input type="text" style="width:200px;"  name="softname" required="true" />
									<br>
									<div style="font-weight:bold">软件进程名称</div>
									<input type="text" name="threadname" style="width:200px;" required="true"/>
									<br>
									<div style="font-weight:bold">归属名单</div>
									<select class="easyui-combobox" name="statu" style="width:200px;">
										<option value="0">黑名单</option>
										<option value="1">白名单</option>
									</select>
									<br>
									<button class="submit" type="submit" style="padding-left:0px;" >添加</button>
						</form>
						
						<form action="src/com.netconnection.action/addPatchListAction.action" method="post" style="padding-left:60px;"  >
						<div style="font-weight:bold">补丁名称</div>
						<input type="text" style="width:200px;"  name="patchname" required="true"/>
						<br>
						<div style="font-weight:bold">严重程度</div>
						<select class="easyui-combobox" name="degree" style="width:200px;"  >
							<option value="非常重要">非常重要</option>
							<option value="重要">重要</option>
							<option value="一般">一般</option>
						</select>
						</br>
						<div class="com" style="font-weight:bold">OS版本</div>
							<select class="easyui-combobox" name="os" style="width:200px;"  >
								<s:iterator id="a" value="#OsList.OsList">
								<option value=<s:property value="#a.os"/>><s:property value="#a.os"/></option>
								</s:iterator>
							</select>
							
					
				
							</br>
						<div style="font-weight:bold">基本信息</div>
						<textarea name="description" style="width:200px;height:70px;" ></textarea>
						</br>
							<button style="padding-left:0px;" class="submit" type="submit">添加</button>
							
					</form>
					
					</div>
			</div>
			
					<div id="ta" class="easyui-tabs"  style=" width:380px; height:500px;float:right;"  >
					
					<div title="软件列表" style="padding:20px;overflow:hidden;" id="home">
					删除软件
							<button onclick="deleteSoftList01()">删除</button>
							<table id="blacktable" ></table>
					</div>
					
					<div title="补丁列表" style="padding:20px;overflow:hidden;" id="home">
					删除补丁
							<button onclick="deleteSoftList01()">删除</button>
							<table id="patchtable" ></table>
			
					</div>
					
			</div>
	
	
  </body>
</html>
