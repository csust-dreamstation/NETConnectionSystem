						$(function (){
							getDataList();
							$('#combobox').combobox({
							    onChange:function checklist(){
							        getDataList();
							       
							    }
							});
							
							
							 
						});
						function setchecked(){
						    var rows = $('#clientlist').datagrid('getRows');
					  		var paramsId="";
				    		for(var i=0;i<rows.length;i++)
				    		{
				    			if(rows[i].statu!="unchecked")
				    			{
				    				$('#clientlist').datagrid('checkRow', getRowIndex(rows[i]));
					    			paramsId=paramsId+rows[i].mac;
					    			if(i<rows.length-1){
					    				paramsId=paramsId+",";
					    			}
				    			}
				    			//alert(rows[i].mac);
				    		}
				    		alert(paramsId);
							 var arr1 = [ "aaa", "bbb", "ccc" ];      
							  $.each(arr1, function(i,val){      
							      alert(i);   
							      alert(val);
							  });  
						}
						function getDataList(){
							 var tacticsname=$('#combobox').combobox('getValue');
							 var urlaction="";
							 
							 urlaction="tacticsPcListAction.action?tacticsname="+encodeURIComponent(tacticsname);
							$("#clientlist").datagrid({
								url:urlaction,
								columns:[[
									{ field:'ck',checkbox:true },
									{field:'clientname',width:80,title:'用户名',align:'center'},
									{field:'ip',width:100,title:'ip地址',align:'center'},
									{field:'mac',width:120,title:'物理地址',align:'center'},
									{field:'os',width:140,title:'操作系统版本',align:'center'},
								]],
								singleSelect: false,
							    pagination:true,
							    fit: true,
							    fitColumns:true,
							    pageSize:30,
							    rownumbers:true//行号     
							});
							$('#clientlist').datagrid({
							    onLoadSuccess:function(data){
							        if(data){
							        	$.each(data.rows, function(index, item){
							        		if(item.statu=="checked"){
							        		$('#clientlist').datagrid('checkRow', index);
							        		}
							        		});
							      }
							}
							});
							var pg = $("#clientlist").datagrid("getPager");    
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

					

//					            
						}

							function checklist(){
								 var tacticsname=$('#combobox').combobox('getValue');
								 alert("hello");
								 getDataList();
								
							}
						    function addTacticsPc(){
							    var rows = $('#clientlist').datagrid('getSelections');
							    var Os="";
							    var tacticsname=$('#combobox').combobox('getValue');
							    if (rows){
								    	//alert(rows[i].mac);	
							    		var paramsId="";
							    		for(var i=0;i<rows.length;i++)
							    		{
							    			
							    			if(rows[i].mac!=null)
							    			{
							    				Os=rows[i].os;
								    			paramsId=paramsId+rows[i].mac;
								    			if(i<rows.length-1){
								    				paramsId=paramsId+",";
								    			}
							    			}
							    			//alert(rows[i].mac);
							    		}
							    		//alert(paramsId);
										$.messager.confirm('Confirm','是否保存策略与这些主机的对应关系?',function(r){
									    if (r){
									    		$.post('tacticsPcSubmitAction.action',
									    			{mac:paramsId,
									    			tacticsname:tacticsname,
									    			checkOs:Os},
									    			function(result){
									    				if(result=="保存成功")
									    				{
									    					alert("应用成功");
									    					 getDataList();
									    				}
									    				else 	if(result=="OS版本不一致")
									    				{
									    					alert("策略补丁OS版本与主机OS不一致，请重新选择！");
									    					 getDataList();
									    				}
									    			}
									    		);
									    		
											  }
										});
							    }
							    
						    }