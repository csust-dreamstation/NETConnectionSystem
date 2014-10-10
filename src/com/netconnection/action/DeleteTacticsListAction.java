package com.netconnection.action;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.netconnection.service.TacticsService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @author penicillus 
 * 根据tacticsId删除tacitcisList中的记录；
 */
@SuppressWarnings("serial")
public class DeleteTacticsListAction extends ActionSupport{
	private int tacticsid;
	private TacticsService tacticsService;
	private String tacticsname;
	public String execute() throws IOException{
			HttpServletRequest request = ServletActionContext.getRequest();
			this.tacticsname=request.getParameter("tacticsname");
			this.tacticsid=(Integer)tacticsService.findIdByName(tacticsname).get(0);
			tacticsService.deletetacticsList(tacticsid);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("策略删除成功");	
			return null;
	}
	public int getTacticsid() {
		return tacticsid;
	}
	public void setTacticsid(int tacticsid) {
		this.tacticsid = tacticsid;
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





}
