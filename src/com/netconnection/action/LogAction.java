package com.netconnection.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;

import com.netconnection.entity.Log;
import com.netconnection.entity.Message;
import com.netconnection.entity.TacticsList;
import com.netconnection.entity.User;
import com.netconnection.service.ILogService;
import com.opensymphony.xwork2.ActionSupport;

public class LogAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private String page;
	private String rows;
	private String condition;
	private String conditionValue;
	private String userName;
	private JSONObject resultObj ;
	
	private ILogService logService;


	public String getLogByPage() {
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
		List<Log> logList = logService.findByPaging(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", logService.findAllCount());
		obj.put("rows", logList);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	
	
	public String findLogByCondition() {
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
		
		System.out.println("condition***************:"+condition);
		List<Log> logList = logService.findLogByCondition(condition,start,number);
		Map<String,Object> obj = new HashMap<String,Object>();
		if(logList != null && logList.size()>0){
			obj.put("total", logService.findByConditionCount());			
		}else{
			obj.put("total", 0);	
		}
		obj.put("rows", logList);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	
	public void logArg(JoinPoint jp) {
		String declaringTypeName = jp.getSignature().getDeclaringTypeName();
		String signature = jp.getSignature().getName();
		Object[] args = jp.getArgs();
		
		System.out.println("declaringTypeName:"+declaringTypeName+"signature:"+signature);
		
		Log log = new Log();
		log.setOperationername(userName);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		log.setTime(dateFormat.format(new Date()));
		
		if(declaringTypeName.endsWith("ExportReportService")){
			if("getExportReportManage".equals(signature)){
				log.setOperation("导出报表");
				log.setContent("导出报表");
				logService.saveLog(log);				
			}
			
		}else if(declaringTypeName.endsWith("IMessageService")){
			if("sendMessage".equals(signature)){
				log.setOperation("发送消息");
				Message msg = (Message)args[0];
				log.setContent("向mac地址为："+msg.getMac()+"的用户发送内容为："+msg.getContent()+"的消息！");
				logService.saveLog(log);
			}
		}else if(declaringTypeName.endsWith("IUserService")){
			System.out.println("-------------------signature:"+signature);
			if("saveUser".equals(signature)){
				log.setOperation("添加管理员信息");
				User u = (User)args[0];
				log.setContent("添加一个姓名叫："+u.getUsername()+"管理员的信息！");
				logService.saveLog(log);
			}
			if("updateUser".equals(signature)){
				log.setOperation("修改管理员信息");
				User u = (User)args[0];
				log.setContent("修改一个姓名叫："+u.getUsername()+"管理员的信息！");
				logService.saveLog(log);
			}
			if("deleteUser".equals(signature)){
				log.setOperation("删除管理员信息");
				User u = (User)args[0];
				log.setContent("删除一个姓名叫："+u.getUsername()+"管理员的信息！");
				logService.saveLog(log);
				
			}
			if("login".equals(signature)){
				this.userName = (String)args[0];
				log.setOperationername(userName);
				log.setOperation("管理员登录信息");
				log.setContent(userName+"管理员登录！");
				logService.saveLog(log);
				
			}
		}else if(declaringTypeName.endsWith("TacticsService")){
			if("addtacticsList".equals(signature)){
				log.setOperation("添加策略");
				TacticsList tl = (TacticsList)args[0];
				log.setContent("添加了一个名为："+tl.getTacticsname()+"新的策略");
				logService.saveLog(log);
			}
			if("deletetacticsList".equals(signature)){
				log.setOperation("删除策略");
				int id = (Integer)args[0];
				log.setContent("删除了一个id为："+id+"新的策略");
				logService.saveLog(log);
			}
		}
		
	}
	
	
	public void exportLog() {
		String parame = ServletActionContext.getRequest().getParameter("parame");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/txt");
	    response.addHeader("Content-Disposition", "attachment;filename=log"+new Date()+".txt");
	    try
	    {
	        OutputStream    os  = response.getOutputStream();
	        os.write(parame.getBytes());
	        os.flush();
	        os.close();
	    }
	    catch ( Exception e )
	    {
	    	e.printStackTrace();
	    }
	}
	
	
	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getCondition() {
		return condition;
	}
	
	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
}
