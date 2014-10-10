package com.netconnection.action;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import antlr.collections.List;
import cn.com.server.Services;
import cn.com.vo.Message;

import com.netconnection.entity.PatchList;
import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.SoftList;
import com.netconnection.entity.TacticsList;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.SoftService;
import com.netconnection.service.TacticsPcService;
import com.netconnection.service.TacticsService;
import com.netconnection.service.TacticsSoftService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * @author penicillus 
 */
@SuppressWarnings("serial")
public class TacticsPcSubmitAction extends ActionSupport{
	private int id;
	private int time;
	private int type;
	private String mac;
	private int statu;
	private int tacticsid;
	private TacticsPcService tacticsPcService;
	private TacticsService tacticsService;
	private IPCInfoService pcinfoService;
	private String together;
	private SoftService softService;
	private Services services;
	private TacticsSoftService tacticsSoftService;
	public String execute() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		//获得前台传过来的值，
		String strIds=request.getParameter("mac");
		String checkOs=request.getParameter("checkOs");
		this.tacticsid=(Integer) tacticsService.findIdByName(request.getParameter("tacticsname")).get(0);
		String[] tStrIds = strIds.split(",");
		String[] OSlist=checkOs.split(",");
		if(tStrIds[0].equals("")&&OSlist[0].equals("")){
			tacticsPcService.deleteBytacticsid(tacticsid);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("保存成功");	
			return null;
		}
		//检测策略的OS版本和主机的版本是否一致；
		//根据策略id找到对应下面的补丁的系统版本！
		if(tacticsSoftService.findListId(tacticsid, 2).size()!=0){
		String Os=softService.findPatchByid((Integer) tacticsSoftService.findListId(tacticsid, 2).get(0)).get(0).getOs();
		for(int i=0;i<OSlist.length;i++)
		if(!(Os.trim().equals(OSlist[i].trim()))&&!(tStrIds[0].equals(""))){
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("OS版本不一致");	
			return null;
		}}
		//删除以前数据库中的数据
		tacticsPcService.deleteBytacticsid(tacticsid);
		//检测主机已经有配置过策略；删除以前的配置的策略，添加入新的策略
		for(int i=0;i<tStrIds.length;i++)
		{
		tacticsPcService.deleteByMac(tStrIds[i]);
		}
		//将mac地址与对应的tacticsid，存入数据库
		for(int i=0;i<tStrIds.length;i++)
		{
			tacticsPcService.saveTacticsPc(tStrIds[i],tacticsid);
		}	
		
		
			//根据Tacticsid得到tacticslist的对象；
			//将黑名单发往前端
			TacticsList tacticsList=new TacticsList();
			SoftList softlist=new SoftList();
			tacticsList=tacticsService.findByTacticsid(tacticsid);
			this.together="";
			this.time=tacticsList.getBlacktime();
			this.type=3;
			this.statu=0;
			if(tacticsSoftService.findListId(tacticsid, statu).size()!=0){
			//保存黑名单并发送到客户端
			java.util.List<Integer> blacklist=tacticsSoftService.findListId(tacticsid, statu);
			for(int i=0;i<blacklist.size();i++)
			{
			System.out.print(blacklist);
			//在软件列表中找到黑名单
			softlist=softService.findSoft(blacklist.get(i));
			together=together+"|"+softlist.getThreadname()+"*"+softlist.getSoftname();
			}
			sendMessage(tacticsid,time+together,type);
			}
			
			//保存白名单并发送到客户端
			this.together="";
			this.time=tacticsList.getWhitetime();
			this.type=1;
			this.statu=1;
			if(tacticsSoftService.findListId(tacticsid, statu).size()!=0){
			java.util.List<Integer> whitelist=tacticsSoftService.findListId(tacticsid, statu);
			for(int i=0;i<whitelist.size();i++)
			{
			System.out.println(whitelist);
			softlist=softService.findSoft(whitelist.get(i));
			together=together+"|"+softlist.getThreadname()+"*"+softlist.getSoftname();
			}
			sendMessage(tacticsid,time+together,type);
			}
			//保存补丁表并发送到客户端
			
			this.together="";
			this.time=tacticsList.getPatchtime();
			this.type=5;
			this.statu=2;
			if(tacticsSoftService.findListId(tacticsid, statu).size()!=0){
			java.util.List<Integer> patchlist01=tacticsSoftService.findListId(tacticsid, statu);
			for(int i=0;i<patchlist01.size();i++)
			{
				PatchList patchlist=softService.findPatchList(patchlist01.get(i));
				together=together+"|"+patchlist.getPatchname();
			}
			sendMessage(tacticsid,time+together,type);
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("保存成功");	
			return null;
		
	}
	private void sendMessage(int tacticsid2, String string, int type2) {
		Message message = new Message();
		int i=0;
		//根据策略在表中找到Mac地址
		i=tacticsPcService.findMacByTactics(tacticsid2).size();
		System.out.println("找到配置该策略的主机台数"+i);
		for(int index=0;index<i;index++){
		String mac=(String) tacticsPcService.findMacByTactics(tacticsid2).get(index);
		if(mac!=null){
		message.setMac(mac);
//		message.setMac("20-6A-8A-4F-20-AE");
		message.setMessage(string);
		message.setType(type2);
		services.send(message);
}}

	}
	public IPCInfoService getPcinfoService() {
		return pcinfoService;
	}
	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public TacticsService getTacticsService() {
		return tacticsService;
	}
	public void setTacticsService(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
	}
	public String getTogether() {
		return together;
	}
	public void setTogether(String together) {
		this.together = together;
	}
	public SoftService getSoftService() {
		return softService;
	}
	public void setSoftService(SoftService softService) {
		this.softService = softService;
	}
	public Services getServices() {
		return services;
	}
	public void setServices(Services services) {
		this.services = services;
	}
	public TacticsSoftService getTacticsSoftService() {
		return tacticsSoftService;
	}
	public void setTacticsSoftService(TacticsSoftService tacticsSoftService) {
		this.tacticsSoftService = tacticsSoftService;
	}



}
