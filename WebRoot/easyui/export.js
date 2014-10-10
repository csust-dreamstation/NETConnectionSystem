	$(function (){
							getDataList();
							getUnclosedReport();
							getInstallReport();
							getIllegalAceessReport();
							$("#reportlist").combobox({
							    onChange:function select(){
							    	var index=$("#reportlist").combobox('getValue');
							    	if(index==0)
							    		$('#tt').tabs("select","客户端基本信息报表");
							    	else if(index==1)
							    		$('#tt').tabs("select","客户端未关机终端报表");
							    	else if(index==2)
							    		$('#tt').tabs("select","客户端软件安装统计报表");
							    	else
							    		$('#tt').tabs("select","客户端违规统计报表");
							    }
							});
							
							   $("#tt").tabs({    
								    border:false,    
								    onSelect:function(title){    
								        var tab = $("#tt").tabs("getSelected");
										var index = $("#tt").tabs('getTabIndex',tab);
										if(index==0)
											$("#reportlist").combobox("select","0");
										else if(index==1)
											$("#reportlist").combobox("select","1");
										else if(index==2)
											$("#reportlist").combobox("select","2");
										else
											$("#reportlist").combobox("select","3");
								    }    	
								}); 
						});
						function getDataList(){
							$("#softlist").datagrid({
								url:"showSoftReportAction.action",
								columns:[[
									{field:'clientname',width:80,title:'用户名',align:'center'},
									{field:'ip',width:100,title:'ip地址',align:'center'},
									{field:'mac',width:140,title:'网卡地址',align:'center'},
									{field:'os',width:150,title:'操作系统版本',align:'center'},
								]],
								singleSelect: false,
							    pagination:true,
							    fitColumns:true,
							    fit: true,
							    pageSize:30,
							    rownumbers:true//行号  
							});
							var pg = $("#softlist").datagrid("getPager");    
							if(pg)    
							{    
							   $(pg).pagination({    
							   pageSize:30,
							   pageList:[30,60,120],
							   beforePageText: '第',//页数文本框前显示的汉字    
			                   afterPageText: '页    共 {pages} 页',    
			                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
							   });    
							}   

						}		
						
						function getUnclosedReport(){
							$("#unclosedReport").datagrid({
								url:"showUnclosedReportAction.action",
								columns:[[
									{field:'clientname',width:80,title:'用户名',align:'center'},
									{field:'ip',width:100,title:'ip地址',align:'center'},
									{field:'mac',width:140,title:'网卡地址',align:'center'},
									{field:'softlist',width:360,title:'软件列表',align:'center'},
								]],
								singleSelect: false,
							    pagination:true,
							    fitColumns:true,
							    fit: true,
							    pageSize:30,
							    rownumbers:true//行号  
							});
							var pg = $("#unclosedReport").datagrid("getPager");    
							if(pg)    
							{    
							   $(pg).pagination({    
							   pageSize:30,
							   pageList:[30,60,120],
							   beforePageText: '第',//页数文本框前显示的汉字    
			                   afterPageText: '页    共 {pages} 页',    
			                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
							   });    
							}   
						}	
						
					
						function getInstallReport(){
							$("#installReport").datagrid({
								url:"showInstallReportAction.action",
								columns:[[
									{field:'clientname',width:80,title:'用户名',align:'center'},
									{field:'ip',width:100,title:'ip地址',align:'center'},
									{field:'mac',width:140,title:'网卡地址',align:'center'},
									{field:'softlist',width:200,title:'运行的黑名单',align:'center'},
									{field:'esoftlist',width:200,title:'没有运行的白名单',align:'center'},
								]],
								singleSelect: false,
							    pagination:true,
							    fit: true,
							    pageSize:30,
							    fitColumns:true,
							    rownumbers:true//行号  
							});
							var pg = $("#installReport").datagrid("getPager");    
							if(pg)    
							{    
							   $(pg).pagination({    
							   pageSize:30,
							   pageList:[30,60,120],
							   beforePageText: '第',//页数文本框前显示的汉字    
			                   afterPageText: '页    共 {pages} 页',    
			                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
							   });    
							}  

						}	
						function getIllegalAceessReport(){
							$("#illegalaceessReport").datagrid({
								url:"showIllegalAceessReportAction.action",
								columns:[[
								{field:'clientname',width:80,title:'用户名',align:'center'},
                                {field:'ip',width:100,title:'ip地址',align:'center'},
                                {field:'mac',width:140,title:'网卡地址',align:'center'},
                                {field:'os',width:150,title:'操作系统版本',align:'center'},
                                {field:'itime',width:150,title:'违规外连时间',align:'center'},
                                {field:'ftime',width:150,title:'流量异常时间',align:'center'},
								]],
								singleSelect: false,
							    pagination:true,
							    fit: true,
							    pageSize:30,
							    fitColumns:true,
							    rownumbers:true//行号  
							});
							var pg = $("#illegalaceessReport").datagrid("getPager");    
							if(pg)    
							{    
							   $(pg).pagination({    
							   pageSize:30,
							   pageList:[30,60,120],
							   beforePageText: '第',//页数文本框前显示的汉字    
			                   afterPageText: '页    共 {pages} 页',    
			                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
							   });    
							}  

						}	
						   function exportReport() {
							   		var reportlist=$('#reportlist').combobox('getValue');
							   		if(reportlist==0){
							   			var rows = $('#softlist').datagrid('getRows');
							    		var unclosed= $('#unclosedReport').datagrid('getRows');
							    		var softRows = new Object();	    		
							    		softRows=JSON.stringify(rows);  
							   		}
							   		else if(reportlist==1){
							    		var unclosed= $('#unclosedReport').datagrid('getRows');
							    		var softRows = new Object();	    		
							    		softRows=JSON.stringify(unclosed);  
							   		}
							   		else if(reportlist==2){
							   			var installReport= $('#installReport').datagrid('getRows');
							   			var softRows = new Object();	    		
							    		softRows=JSON.stringify(installReport);  
							   		}
							   		else{
							   			var illegalaceessReport= $('#illegalaceessReport').datagrid('getRows');
							   			var softRows = new Object();	    		
							    		softRows=JSON.stringify(illegalaceessReport);  
							   		}

						    		var outcome;
						    		$.post('exportSoftReportAction.action',
						    				{daoChuJsonStr:softRows,
						    				reportlist:reportlist},
							    			function(result){
							    				if(result!=null)
							    				{
							    					outcome=result;
							    					window.location.href=outcome;
							    					//alert("导出成功！");
							    				}
							    			}
							    		); 		
						    		
						    	}	