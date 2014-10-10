package com.netconnection.action;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.netconnection.entity.TacticsList;
import com.netconnection.service.TacticsService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @author penicillus 
 */
@SuppressWarnings("serial")
public class AddTacticsListAction extends ActionSupport{
	private int tacticsid;
	private TacticsService tacticsService;
	private String tacticsname;
	public String execute() throws IOException{
		// 添加策略表单，并存入数据库
			TacticsList tacticsList=new TacticsList();
			HttpServletRequest request = ServletActionContext.getRequest();
			this.tacticsname=request.getParameter("tacticsname");
			//tactics设定为自增，可能会有bug
			tacticsList.setTacticsid(tacticsid);
			tacticsList.setTacticsname(tacticsname);	
			tacticsList.setBlacktime(20);
			tacticsList.setPatchtime(120);
			tacticsList.setWhitetime(20);
			tacticsService.addtacticsList(tacticsList);
			System.out.println(tacticsname);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("策略添加成功");	
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
