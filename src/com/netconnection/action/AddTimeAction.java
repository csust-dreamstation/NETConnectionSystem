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
public class AddTimeAction extends ActionSupport{
	private int tacticsid;
	private int blacktime;
	private int whitetime;
	private int patchtime;
	private String tacticsname;
	private TacticsService tacticsService;
	public String execute ()throws IOException{
		// 添加策略表单，并存入数据库
		HttpServletRequest request = ServletActionContext.getRequest();
		this.tacticsname=request.getParameter("tacticsname");
			this.tacticsid=(Integer)tacticsService.findIdByName(tacticsname).get(0);
			TacticsList tacticsList=tacticsService.findByTacticsid(tacticsid);
			tacticsList.setTacticsid(tacticsid);
			tacticsList.setTacticsname(tacticsname);
			tacticsList.setBlacktime(blacktime);
			tacticsList.setWhitetime(whitetime);
			tacticsList.setPatchtime(patchtime);
			tacticsService.updatetacticsList(tacticsList);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("时间设置成功");
			return null;
	}
	public int getBlacktime() {
		return blacktime;
	}
	public void setBlacktime(int blacktime) {
		this.blacktime = blacktime;
	}
	public int getWhitetime() {
		return whitetime;
	}
	public void setWhitetime(int whitetime) {
		this.whitetime = whitetime;
	}
	public int getPatchtime() {
		return patchtime;
	}
	public void setPatchtime(int patchtime) {
		this.patchtime = patchtime;
	}
	public String getTacticsname() {
		return tacticsname;
	}
	public void setTacticsname(String tacticsname) {
		this.tacticsname = tacticsname;
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


}
