package com.netconnection.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.Pcinfo;
import com.netconnection.service.IOnlineTimeService;
import com.netconnection.service.IPCInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class OnlineTimeLogAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private IOnlineTimeService onlineService;
	private IPCInfoService pcinfoService;
	private JSONObject resultObj;
	private String condition;
	private String page;
	private String rows;


	public String findAllOnLineRecord(){
		 /*rows = (String) ServletActionContext.getRequest()
	                .getParameter("rows");
	     page = (String) ServletActionContext.getRequest()
	                .getParameter("page");
	     System.out.println(rows);
	     System.out.println(page);*/
	     
	    int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	    int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows); 
	    int start = (intPage-1)*number;
		List<Onlinetime> onlineList = onlineService.findOnlineTimeByPaging(start, number);
		
		List<Map> Maprows = null;
		Map<String,Object> obj = new HashMap<String,Object>();
		if(onlineList != null && onlineList.size()>0){
			Maprows = new ArrayList<Map>();
			 for(Onlinetime onlinTime:onlineList){			
				 
				 Map<String,Object> row = new HashMap<String,Object>();
				 row.put("clientname",onlinTime.getClientname());
				 row.put("ip",onlinTime.getIp());
				 row.put("mac",onlinTime.getMac());
				 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
				 try {
					 System.out.println("beigin:"+df.parse(onlinTime.getBegintime()).getTime());
					 row.put("begintime",df.parse(onlinTime.getBegintime()).getTime());
					 if(onlinTime.getEndtime() == null){
						 row.put("endtime",0);
					 }else{
						 row.put("endtime",df.parse(onlinTime.getEndtime()).getTime());						 
					 }
				 } catch (ParseException e) {
					 e.printStackTrace();
				 }
				 Maprows.add(row);
			 }	
			 obj.put("total",onlineService.findAllOnlineTime().size());
			 obj.put("rows", Maprows);
		}else{
			obj.put("total", 0);
			obj.put("rows", Maprows);
		}
		this.setResultObj(JSONObject.fromObject(obj)); 
		
		return SUCCESS;
	}
	
	public String findOnlineTimeByCondition(){
		System.out.println("****************condition:"+condition);
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
	    int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows); 
	    int start = (intPage-1)*number;
//		List<Onlinetime> onlineList = onlineService.findOnlineTimeByPaging(start, number);
		List<Onlinetime> onlineList = onlineService.findOnlineTimeByCondition(condition,start, number);
		
		List<Map> rows = null;
		Map<String,Object> obj = new HashMap<String,Object>();
		if(onlineList != null && onlineList.size()>0){
			 rows = new ArrayList<Map>();
			 for(Onlinetime onlinTime:onlineList){			
				 
				 Map<String,Object> row = new HashMap<String,Object>();
				 row.put("clientname",onlinTime.getClientname());
				 row.put("ip",onlinTime.getIp());
				 row.put("mac",onlinTime.getMac());
				 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
				 try {
					 row.put("begintime",df.parse(onlinTime.getBegintime()).getTime());
					 if(onlinTime.getEndtime() == null){
//						 row.put("endtime",new Date().getTime());
						 row.put("endtime",0);
					 }else{
						 row.put("endtime",df.parse(onlinTime.getEndtime()).getTime());						 
					 }
				 } catch (ParseException e) {
					 e.printStackTrace();
				 }
				 rows.add(row);
			 }	
			 obj.put("total", onlineService.getConditionCount());
			 obj.put("rows", rows);
		}else{
			obj.put("total", 0);
			obj.put("rows", rows);
		}
		this.setResultObj(JSONObject.fromObject(obj)); 
		
		return SUCCESS;
	}

	public IOnlineTimeService getOnlineService() {
		return onlineService;
	}

	public void setOnlineService(IOnlineTimeService onlineService) {
		this.onlineService = onlineService;
	}

	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
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

	public IPCInfoService getPcinfoService() {
		return pcinfoService;
	}

	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	
}
