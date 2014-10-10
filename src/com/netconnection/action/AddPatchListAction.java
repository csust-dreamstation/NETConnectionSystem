package com.netconnection.action;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
import com.netconnection.service.SoftService;
import com.netconnection.service.TacticsSoftService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @author Penicillus ；
 * 添加软件名单  包括黑名单  白名单  补丁信息
 * 并在添加名单的同时可以选择相应的策略包含这些名单（软件）
 */
@SuppressWarnings("serial")
public class AddPatchListAction extends ActionSupport{
	private int patchid;
	private SoftService softService;        
	private String patchname;              
	private String os;        
	private String degree;   
	private String description;     
	private String checked;
	public String execute(){
		   PatchList patchlist=new PatchList();
			//添加软件名单和补丁名单的新id号；
            patchlist.setDegree(degree);
            patchlist.setDescription(description);
            patchlist.setOs(os);
            patchlist.setPatchname(patchname);
            patchlist.setChoose("unchecked");
			softService.addPatchList(patchlist);
			return "success";
	}

	public int getPatchid() {
		return patchid;
	}

	public void setPatchid(int patchid) {
		this.patchid = patchid;
	}

	public String getPatchname() {
		return patchname;
	}

	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}



	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public SoftService getSoftService() {
		return softService;
	}
	public void setSoftService(SoftService softService) {
		this.softService = softService;
	}


}
