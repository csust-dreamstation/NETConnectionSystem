package com.netconnection.action;
import java.util.ArrayList;
import java.util.List;

import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.TacticsList;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.TacticsService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author penicillus 显示tactics列表；
 */
@SuppressWarnings("serial")
public class TacticsListAction extends ActionSupport{
	private TacticsService tacticsService; 
	private Integer tacticsid;
	private String tacticsname;
	private List<TacticsList> list = new ArrayList<TacticsList>();
	private String test;
	public String execute(){
		list=tacticsService.findByid(0);    //获得list
		return "success";
	}
	public TacticsService getTacticsService() {
		return tacticsService;
	}
	public void setTacticsService(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
	}
	public Integer getTacticsid() {
		return tacticsid;
	}
	public void setTacticsid(Integer tacticsid) {
		this.tacticsid = tacticsid;
	}
	public String getTacticsname() {
		return tacticsname;
	}
	public void setTacticsname(String tacticsname) {
		this.tacticsname = tacticsname;
	}

	public List<TacticsList> getList() {
		return list;
	}
	public void setList(List<TacticsList> list) {
		this.list = list;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}



	
}
