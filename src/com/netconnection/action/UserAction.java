package com.netconnection.action;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import cn.com.util.ConfigTable;

import com.netconnection.entity.User;
import com.netconnection.exception.NoFindUserByNameException;
import com.netconnection.service.IUserService;
import com.netconnection.util.EncodeUtil;
import com.netconnection.util.EncryptionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{	
	private static final long serialVersionUID = 1L;
	private int userId;
	private String userName;
	private String password;
	private String message;
	private String validateCode;
	private IUserService userService;
	private JSONObject resultObj ;

	public String login(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		String sValidateCode = (String)(ActionContext.getContext().getSession().get("sValidateCode"));
		System.out.println(sValidateCode);
		System.out.println(validateCode);
		if(sValidateCode==null) return INPUT;
		if(! sValidateCode.equalsIgnoreCase(validateCode)){
			message = "验证码输入错误!";
			return INPUT;
		}
		
		User user = null;
		
		try {
			user = this.userService.login(userName, password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			message = "密码检验加密错误!";
			return INPUT;
		} catch (NoFindUserByNameException e) {
			message = "对不起，没有该用户名!"; 
			return INPUT;
		}
		
		if(user != null){
			System.out.println("tttttttttttttttttttttttt:"+user.getOnlinestate() );
//			if(user.getOnlinestate() == IUserService.ONLINE){
//				System.out.println("已经在线");
//				message = "该用户已经登录，请先注销！";
//				return INPUT;
//			}else{
//				
//				//将用户信息加载到session中s
//				session.setAttribute("user", user);		
//				user.setOnlinestate(IUserService.ONLINE);
//				userService.updateUser(user);
//				return SUCCESS;
//			}
			//将用户信息加载到session中s
			session.setAttribute("user", user);		
//			user.setOnlinestate(IUserService.ONLINE);
//			userService.updateUser(user);
			return SUCCESS;
			
		}else{
			message = "用户名或密码错误! ";
		}
		return INPUT;
		
	}
	
	public String exit(){
		System.out.println("111111111111111111111111");
		Map session = ActionContext.getContext().getSession();
		User user = (User)session.remove("user");
		//user.setOnlinestate(IUserService.NO_ONLINE);
		//userService.updateUser(user);
		return "success";
	}
	
	public String addUser(){
		System.out.println("addUserName:"+userName);
		try {
	
			userName = EncodeUtil.transferISO8859ToUTF8(userName);
		} catch (UnsupportedEncodingException e) {
			System.out.println("加密异常");
			e.printStackTrace();
		}
		
		User user = new User(userName,password);
		if(userService.saveUser(user) == true){
			message = "用户添加成功";
		}else{
			message = "用户添加失败";			
		}
        return INPUT;
		
	}
	
	public String findAllUser(){
		
		List<User> userList = userService.findAllUserList();;
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", userList.size());
		obj.put("rows", userList);
		this.setResultObj(JSONObject.fromObject(obj));
		return SUCCESS;
	}
	
	public String updateUser(){
		User user = userService.findByUserId(userId);
		if(user != null){
			user.setUsername(userName);
			
			
			if(!password.equals(user.getPassword())){
				try {
					password = EncryptionUtil.eccryptByMD5(password);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				user.setPassword(password);
			}
			if(userService.updateUser(user) == true){
				message = "用户更新操作成功";
			}else{
				message = "用户更新操作失败";
			}
			
		}else{
			message = "用户更新操作失败";
		}
		
		return INPUT;
	}
	
	public String deleteUser(){
		System.out.println("delete");
		System.out.println("userId:"+userId);
		User user = userService.findByUserId(userId);
		System.out.println("username:"+user.getUsername());
		System.out.println("userId:"+user.getId());
		userService.deleteUser(user);
		System.out.println("delete success");
//		if(userService.deleteUser(user) == true){
//			message = "用户删除成功";
//		}else{
//			message = "用户删除失败";
//		}	
		return INPUT;
	}
	
	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getValidateCode() {
		return validateCode;
	}


	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}


	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}
}
