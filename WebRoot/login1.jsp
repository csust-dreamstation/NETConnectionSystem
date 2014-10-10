<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function validate(){
			var userName = document.getElementById("userName");
			if( userName.value == ""){
				document.getElementById("err_msg").innerText="用户名不能为空！";
				userName.focus();
				err_style(userName);
				return false;
			}
			var password = document.getElementById("password");
			if( password.value == ""){
				document.getElementById("err_msg").innerText="密码不能为空！";
				password.focus();
				return false;
			}
			var validateCode = document.getElementById("validateCode");
				if( validateCode.value == ""){
				document.getElementById("err_msg").innerText="验证码不能为空！";
				validateCode.focus();
				return false;
			}
			return true;
		}
		function refresh(obj) {
    		var rom = new Date(); 
			obj.src = "validateAction.action?timestamp="+rom;
    	}
    	
    	function err_style(obj){
    		obj.style.border = "2px solid red";
    	}
    	
    	function blur(obj){
    		alert("jahkgho");
    		obj.style.border = "solid 1px #d1d1d1";
    	}
	</script>
	<style type="text/css">
		.floortd{position: relative;top: -16px;}
	</style>
</head>

<body on>
<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;border-spacing:0;">
	      <tr>
	        <td height="561" style="background:url(images/lbg.gif)"><table width="940" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td height="238" style="background:url(images/login01.jpg)">
	            	<div style="color: red;font-size: 15px;margin-left: 280px;margin-top: 205px;" id="err_msg">
               				${message }
               		</div>
	            </td>
	          </tr>
	          <tr>
	            <td height="190">
	             <form action="loginAction.action" method="post" onSubmit="return validate()">
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td width="208" height="190" style="background:url(images/login02.jpg)"></td>
	                <td width="518" style="background:url(images/login03.jpg)">
	                <table width="320" border="0" align="center" cellpadding="0" cellspacing="0">
	                  <tr>
	                    <td width="40" height="50"><img src="images/user.gif" width="30" height="30"></td>
	                    <td height="50"><div style=" width:60px; text-align:center">用户</div></td>
	                    <td width="242" height="50">
	                    	<input type="text" name="userName" id="userName" 
	                    		style="width:164px; height:32px; background:url(images/inputbg.gif) repeat-x;
	                    			 border:solid 1px #d1d1d1; font-size:9pt;" onblur="javascript:this.style.border = 'solid 1px #d1d1d1';"/>
	                    </td>
						<td rowspan="3">
							<input type="submit" style="background:url(image/login_button.jpg) no-repeat; border:0px; width: 85px; height:100px; margin-left:55px;" value=""/>
						</td>
	                  </tr>	
						</td>
	                  </tr>
	                  <tr>
	                    <td height="50"><img src="images/password.gif" width="28" height="32"></td>
	                    <td height="50"><div style=" width:60px; text-align:center">密码</div></td>
	                    <td height="50">
	                    	<input type="password" name="password" id="password" 
	                    		style="width:164px; height:32px;background:url(images/inputbg.gif) repeat-x; border:solid 1px #d1d1d1;" 
	                    		onblur="javascript:this.style.border = 'solid 1px #d1d1d1';">
	                   	</td>
	                  </tr>
	     			   <tr>
					   	<td height="50"><img src="images/Validate.png" width="28" height="32"></td>
	                    <td height="50"><div style="width:60px; text-align:center">验证码</div></td>
	                    <td height="50">
	                    	<input type="text" name="validateCode" id="validateCode" 
	                    		style="width:75px; height:32px;background:url(images/inputbg.gif) repeat-x; 
	                    		border:solid 1px #d1d1d1;" onblur="javascript:this.style.border = 'solid 1px #d1d1d1';">
	                    	<img id="validateImg" style="margin-left: 5px;margin-bottom: -8px;width: 75px;" 
	                   	 		src="validateAction.action?timestamp=<%=System.currentTimeMillis()%>" 
	                   	 		onclick="refresh(this)" alt="点击图片，换一张">
	                   	</td>
	                  </tr>
	                </table>
	                </form>
	                
	                </td>
	                <td width="214" style="background:url(images/login04.jpg)" ></td>
	              </tr>
		       	</table>
            </td>
          </tr>
          <tr>
            <td height="133">
            	<img  src="images/login05.jpg" id="floorlogo">
            	<script type="text/javascript">
            		//让火狐浏览器能够保持一致
				   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
				   		document.getElementById("floorlogo").className = "floortd";
				   }
            	</script>
            </td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
