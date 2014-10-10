						$(function (){
							getBlackList01();
							getWhiteList01();	
							getPatchList01();
							$('#combox01').combobox({
							    onChange:function checkTactics(){
							    	getBlackList01();
									getWhiteList01();	
									getPatchList01();
							    }
							});
							 
						});
						function getBlackList01(){
							var tacticsname=$('#combox01').combobox('getValue');
							 var urlaction="";
							 urlaction="getBlackListAction.action?tacticsname="+encodeURIComponent(tacticsname);
							$("#blacktable").datagrid({
								url:urlaction,
								columns:[[
									{ field:'ck',checkbox:true },
									{field:'softid',width:100,title:'软件编号',align:'center'},
									{field:'softname',width:100,title:'软件名称',align:'center'},
									{field:'threadname',width:100,title:'线程名称',align:'center'},
								]],
								   singleSelect: false,
								   pagination:true,
								   fit: true,
								   fitColumns:true,
								   rownumbers:true//行号       
							});  
							$('#blacktable').datagrid({
							    onLoadSuccess:function(data){
							        if(data){
							        	$.each(data.rows, function(index, item){
							        		if(item.choose=="checked"&&item.statu=="0"){
							        		$('#blacktable').datagrid('checkRow', index);
							        		}
							        		});
							      }
							}
							});
							
							var pg = $("#blacktable").datagrid("getPager");    
							if(pg)    
							{    
							   $(pg).pagination({    
							   pageSize:10,
							   pageList:[10,20,30],
							   beforePageText: '第',//页数文本框前显示的汉字    
			                   afterPageText: '页    共 {pages} 页',    
			                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
							   });    
							}   
						}
							
						function getWhiteList01(){
								var tacticsname=$('#combox01').combobox('getValue');
								 var urlaction="";
								 urlaction="getWhiteListAction.action?tacticsname="+encodeURIComponent(tacticsname);
								$("#whitetable").datagrid({
									url:urlaction,
										columns:[[
											{ field:'ck',checkbox:true },
											{field:'softid',width:100,title:'软件编号',align:'center'},
											{field:'softname',width:100,title:'软件名称',align:'center'},
											{field:'threadname',width:100,title:'线程名称',align:'center'},
										]],
										singleSelect: false,
										pagination:true,
										fit: true,
										fitColumns:true,
										rownumbers:true//行号      
									});
								$('#whitetable').datagrid({
								    onLoadSuccess:function(data){
								        if(data){
								        	$.each(data.rows, function(index, item){
								        		if(item.choose=="checked"&&item.statu=="1"){
								        		$('#whitetable').datagrid('checkRow', index);
								        		}
								        		});
								      }
								}
								});
								var pg = $("#whitetable").datagrid("getPager");    
								if(pg)    
								{    
								   $(pg).pagination({    
								   pageSize:10,
								   pageList:[10,20,30],
								   beforePageText: '第',//页数文本框前显示的汉字    
				                   afterPageText: '页    共 {pages} 页',    
				                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
								   });    
								}  
							}
							function getPatchList01(){
								var tacticsname=$('#combox01').combobox('getValue');
								 var urlaction="";
								 urlaction="getPatchListAction.action?tacticsname="+encodeURIComponent(tacticsname);
								$("#patchtable").datagrid({
									url:urlaction,
									columns:[[
										{ field:'ck',checkbox:true },
										{field:'patchid',width:100,title:'补丁编号',align:'center'},
										{field:'patchname',width:100,title:'补丁名称',align:'center'},
										{field:'degree',width:140,title:'严重程度',align:'center'},
										{field:'os',width:140,title:'OS版本',align:'center'},
										{field:'description',width:140,title:'基本描述',align:'center'},
									]],
								    singleSelect: false,
								    pagination:true,
								    fit: true,
								    fitColumns:true,
								    rownumbers:true//行号      
								});
								$('#patchtable').datagrid({
								    onLoadSuccess:function(data){
								        if(data){
								        	$.each(data.rows, function(index, item){
								        		if(item.choose=="checked"){
								        		$('#patchtable').datagrid('checkRow', index);
								        		}
								        		});
								      }
								}
								});
								var pg = $("#patchtable").datagrid("getPager");    
								if(pg)    
								{    
								   $(pg).pagination({    
								   pageSize:10,
								   pageList:[10,20,30],
								   beforePageText: '第',//页数文本框前显示的汉字    
				                   afterPageText: '页    共 {pages} 页',    
				                   displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
								   });    
								}  
							}
					
						
							
							function confirm1(){
								 var tacticsname=$('#combox01').combobox('getValue');
								 if(tacticsname=="默认策略")
									 {
									 alert("默认策略不能删除！");
									 history.go(0);
									 return null;
									 }
									$.messager.confirm('删除策略', '确定要删除该策略吗?', function(r){
										if (r){
											$.post('deleteTacticsListAction.action',
									    			{
													tacticsname:tacticsname,
									    			},
									    			function(result){
									    				if(result=="策略删除成功")
									    				{
									    					alert("策略删除成功,请尽快为配置了该策略的主机配置新的策略！");
									    					history.go(0);
									    				}
									    			}
									    		);
										}
									});
								}
								function prompt1(){
									$.messager.prompt('添加策略', '请输入策略名称', function(r){
										if (r=="默认策略"){
											alert("策略名称不能为默认策略");
										}else{
											$.post('addTacticsListAction.action',
									    			{
													tacticsname:r,
									    			},
									    			function(result){
									    				if(result=="策略添加成功")
									    				{
									    					alert("策略添加成功！添加策略后请在复选框中勾选后进行配置！");
									    					history.go(0);
									    				}
									    			
									    			}
									    		);
										}
									});
								}
								function settime(){
									var tacticsname=$('#combox01').combobox('getValue');
									document.getElementById("p1").innerHTML=tacticsname;
									$('#time').window('open');
									
								}
								function addtime(){
													var tacticsname=$('#combox01').combobox('getValue');
													var blacktime=$('#blacktime').combobox('getValue');
													var whitetime=$('#whitetime').combobox('getValue');
													var patchtime=$('#patchtime').combobox('getValue');
													$.post('addTimeAction.action',
											    			{
															tacticsname:tacticsname,
															blacktime:blacktime,
															whitetime:whitetime,
															patchtime:patchtime,
											    			},
											    			function(result){
											    	
												    			
											    				if(result=="时间设置成功")
											    				{
											    					alert("时间设置成功");
											    					history.go(0);
											    				}
											    			}
											    		);
												}
								function checkTactics(){
								    		getBlackList01();
											getWhiteList01();	
											getPatchList01();
								}
								

								function addSoftList01(){
								    var rows = $('#blacktable').datagrid('getSelections');
								    var whiterows=$('#whitetable').datagrid('getSelections');
								    var patchrows=$('#patchtable').datagrid('getSelections');
								    var tacticsname=$('#combox01').combobox('getValue');
								    if (rows){
								    		var paramsId="";
								    		var whiteId="";
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
								    		for(var j=0;j<whiterows.length;j++)
								    		{
								    			if(whiterows[j].softid!=null)
								    			{
								    				whiteId=whiteId+whiterows[j].softid;
									    			if(j<whiterows.length-1){
									    				whiteId=whiteId+",";
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
											$.messager.confirm('Confirm','确定保存配置吗?',function(r){
										    if (r){
										    		$.post('softSubmitAction.action',
										    			{recordIds:paramsId,
										    			whiteId:whiteId,
										    			tacticsname:tacticsname,
										    			patchid:patchId},
										    			function(result){
										    				if(result=="配置信息保存成功")
										    				{
										    					getBlackList01();
																getWhiteList01();	
																getPatchList01();
										    				}
										    				else if(result=="黑白名单不可以同时选择")
										    				{
										    					alert("黑白名单选择不能重复,,请重新选择！");
										    				}
										    				else if(result=="在策略中请选择相同版本的补丁")
										    				{
										    					alert("在策略中请选择相同版本的补丁！");
										    				}
										    				
										    			}
										    		);
										    		
												  }
											});
								    }

								}