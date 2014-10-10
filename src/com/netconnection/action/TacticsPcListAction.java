package com.netconnection.action;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.SoftList;
import com.netconnection.entity.TacticsList;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.TacticsPcService;
import com.netconnection.service.TacticsService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author penicillus 显示tactics列表；
 */
@SuppressWarnings("serial")
public class TacticsPcListAction extends ActionSupport{
	private TacticsPcService tacticsPcService; 
	private IPCInfoService pcinfoService;
	private TacticsService tacticsService;
	private int tacticsid;
	private String page;
	private String rows;
	private String tacticsname;
	private JSONObject resultObj ;
	public String execute(){
		return "success";
	}
	public String getClientList() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String temp=request.getParameter("tacticsname");
		this.tacticsname = new String(temp.getBytes("iso8859-1"),"utf-8");
		this.tacticsid=(Integer) tacticsService.findIdByName(tacticsname).get(0);
		List<Pcinfo> pcinfo=new ArrayList<Pcinfo>();
		@SuppressWarnings("unchecked")
		List<String> idList=tacticsPcService.findMacByTactics(tacticsid);//寻找策略与pc的对应表mac地址表
		tacticsPcService.intPcinfo();
    	for (int i = 0; i <idList.size(); i++)  
    	{  
    		if(idList.get(i).equals("")){
    		break;}
    	    tacticsPcService.setCheckedByMac(idList.get(i));   
    	}     
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
		List<Pcinfo> onlineList = pcinfoService.findByPaging(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", pcinfoService.findAllCount());
		obj.put("rows", onlineList);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success";
	}
	public TacticsService getTacticsService() {
		return tacticsService;
	}
	public void setTacticsService(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
	}
	public String getTacticsname() {
		return tacticsname;
	}

	public void setTacticsname(String tacticsname) {
		this.tacticsname = tacticsname;
	}
	public TacticsPcService getTacticsPcService() {
		return tacticsPcService;
	}
	public void setTacticsPcService(TacticsPcService tacticsPcService) {
		this.tacticsPcService = tacticsPcService;
	}
	public int getTacticsid() {
		return tacticsid;
	}
	public void setTacticsid(int tacticsid) {
		this.tacticsid = tacticsid;
	}
    public IPCInfoService getPcinfoService() {
		return pcinfoService;
	}

	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
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

	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}
	
}
