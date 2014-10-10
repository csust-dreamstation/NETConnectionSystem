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
public class AddSoftListAction extends ActionSupport{
	private int id;
	private SoftService softService;        //软件表操作方法；
	private String softname;              //接收前台传来的软件名称
	private String threadname;        //接受前台传来的软件对应的线程名称
	private int statu;                 //区别是来自哪个表，黑名单 ，白名单，补丁名单
	public String execute(){
			SoftList  softList=new SoftList();
			softList.setSoftname(softname);
			softList.setThreadname(threadname);
			softList.setChoose("unchecked");
			softList.setStatu(statu);
			softService.addSoftList(softList);
			return "success";
	}

	public SoftService getSoftService() {
		return softService;
	}
	public void setSoftService(SoftService softService) {
		this.softService = softService;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSoftname() {
		return softname;
	}
	public void setSoftname(String softname) {
		this.softname = softname;
	}
	public String getThreadname() {
		return threadname;
	}
	public void setThreadname(String threadname) {
		this.threadname = threadname;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}


}
