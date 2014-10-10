package com.netconnection.action;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import cn.com.server.Server;
import cn.com.server.Services;
import cn.com.thread.DelData;
import cn.com.userlist.UserInfolist;
import cn.com.util.ConfigTable;
import cn.com.util.ServerUtil;
import cn.com.vo.Message;
import cn.com.vo.UserInfo;

import com.opensymphony.xwork2.ActionSupport;
public class SystemSettingAction extends ActionSupport{
	private String serverPort;
	private String messageDealTime;
	private Services services;
	private String upWarning;
	private String loadWarning;
	private String warningContent;
	private String date;
	private String shi;
	private String dnstime;
	private String dnsadress;
	private String dnsport;
	private int type_wifichk;
    private int d_day;

	private Map serverData;
	
	public String saveServerSetting() throws UnsupportedEncodingException{
		System.out.println("serverPort:"+serverPort);
		System.out.println("messageDealTime:"+messageDealTime);
		//修改服务器的配置文件
		String path = Server.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/classes"));	
		services.changecofig("port", Integer.parseInt(serverPort), path+"/config.xml");
		services.changecofig("rechz", Integer.parseInt(messageDealTime)*1000, path+"/config.xml");
		services.changecofig("dely_time_del",d_day, path+"/config.xml");
		System.out.println(d_day);
		//重启服务器
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		int deltype=Integer.parseInt(this.date);
		ConfigTable.changeconfig("deltype",deltype);
		int date=Integer.parseInt(this.shi);
		if(deltype==2){
		String str[]={"一","二","三","四","五","六","日"};
		session.setAttribute("we", str[date-1]);
		}
		switch(deltype){
		case DelData.hour_of_day:
			services.changecofig("hour_of_day",date, path+"/config.xml");
			break;
		case DelData.day_of_week:
			services.changecofig("day_of_week", date, path+"/config.xml");
			break;
		case DelData.day_of_month:
			services.changecofig("day_of_month", date, path+"/config.xml");
			break;
		case DelData.month_of_year:
			services.changecofig("month_of_year", date, path+"/config.xml");
			break;
		}
		
		services.reloadserver();
		System.out.println("messageDealTime:"+messageDealTime);
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		session.setAttribute("deltype", deltype);
		session.setAttribute("deldate",date);
		session.setAttribute("d_day", d_day);
		return INPUT;
	}
	public String saveDnsSetting(){
		System.out.println(this.dnsadress);
        System.out.println(this.dnsport);
        System.out.println(this.dnstime);
		String path = Server.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/classes"));
        services.changecofig("dnsadress", this.dnsadress, path+"/config.xml");
        services.changecofig("dnsport", this.dnsport, path+"/config.xml");
        services.changecofig("dnstime", this.dnstime, path+"/config.xml");
        HttpSession session = ServletActionContext.getRequest().getSession();
    	session.setAttribute("dnsport",dnsport);
		session.setAttribute("dnsadress",dnsadress);
		session.setAttribute("dnstime",dnstime);
        ArrayList<UserInfo> list=UserInfolist.getuserlist();
        for(int i=0;i<list.size();i++){
            Message mes=new Message();
			mes.setMac(list.get(i).getMac());
			mes.setType(Message.ILLACC);
			ServerUtil.send(mes);		
			mes.setType(Message.ILLAC);
			mes.setMessage(ConfigTable.dnstime+"|"+ConfigTable.dnsadress+"|"+ConfigTable.dnsport);
			ServerUtil.send(mes);
        }
		return INPUT;
	}
	public String SaveCheckWifiType(){
		System.out.println("这里打印的是上网检测策略"+type_wifichk);
		String path = Server.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/classes"));
		services.changecofig("type_wifichk", type_wifichk, path+"/config.xml");
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("type", ConfigTable.type_wifichk);
		for(UserInfo user:UserInfolist.getuserlist()){
			String mac=user.getMac();
			Message message=new Message();
			message.setMac(mac);
			message.setMessage("0");
			message.setType(Message.TYPE_WIFICHK);
			ServerUtil.send(message);
			message.setMessage(type_wifichk+"");
			ServerUtil.send(message);
		}
		return INPUT;
	}
	public String ServerData(){
		/*serverData = new HashMap<String, Integer>();
		serverData.put("serverPort", ConfigTable.port);
		serverData.put("messageDealTime", ConfigTable.timeout);*/
//		this.setServerPort(ConfigTable.port+"");
//		this.setMessageDealTime(ConfigTable.rechz/1000+"");
//		this.setUpWarning(ConfigTable.upWarning+"");
//		this.setLoadWarning(ConfigTable.loadWarning+"");
//		this.setWarningContent(ConfigTable.warningContent);
		/*System.out.println("up:"+ConfigTable.upWarning+" load:"+ConfigTable.loadWarning+"content:"+ConfigTable.warningContent);*/
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("type", ConfigTable.type_wifichk);
		session.setAttribute("serverPort", ConfigTable.port+"");
		session.setAttribute("messageDealTime", ConfigTable.rechz/1000+"");
		session.setAttribute("upWarning", ConfigTable.upWarning);
		session.setAttribute("loadWarning", ConfigTable.loadWarning);
		session.setAttribute("warningContent", ConfigTable.warningContent);
		session.setAttribute("dnsport",ConfigTable.dnsport);
		session.setAttribute("dnsadress",ConfigTable.dnsadress);
		session.setAttribute("dnstime",ConfigTable.dnstime);
		session.setAttribute("d_day",ConfigTable.dely_time_del);
		String str[]={"一","二","三","四","五","六","日"};
		if(session.getAttribute("deltype")==null){
		session.setAttribute("deltype",ConfigTable.deltype);
		if(ConfigTable.deltype==1){
			session.setAttribute("deldate",ConfigTable.hour_of_day);
		}
		if(ConfigTable.deltype==2){
			session.setAttribute("deldate",ConfigTable.day_of_week);
			session.setAttribute("we", str[ConfigTable.day_of_week-1]);
		}
		if(ConfigTable.deltype==3){
			session.setAttribute("deldate",ConfigTable.day_of_month);
		}
		if(ConfigTable.deltype==4){
			session.setAttribute("deldate",ConfigTable.month_of_year+1);
		}
		}
		return INPUT;
	}
	
	public String getFlowWarning(){
		this.setUpWarning(ConfigTable.upWarning+"");
		this.setLoadWarning(ConfigTable.loadWarning+"");
		this.setWarningContent(ConfigTable.warningContent);
		return INPUT;
	}
	
	public String saveFlowWarningSetting(){
		//修改服务器的配置文件
		String path = Server.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/classes"));	
		services.changecofig("upWarning", Integer.parseInt(upWarning), path+"/config.xml");
		services.changecofig("loadWarning", Integer.parseInt(loadWarning), path+"/config.xml");
		services.changecofig("warningContent", warningContent, path+"/config.xml");
		//重启服务器
		services.reloadserver();
		
		return INPUT;
	}

	public int getType_wifichk() {
		return type_wifichk;
	}
	public void setType_wifichk(int type_wifichk) {
		this.type_wifichk = type_wifichk;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getMessageDealTime() {
		return messageDealTime;
	}
	public void setMessageDealTime(String messageDealTime) {
		this.messageDealTime = messageDealTime;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public Map getServerData() {
		return serverData;
	}

	public void setServerData(Map serverData) {
		this.serverData = serverData;
	}


	public String getUpWarning() {
		return upWarning;
	}

	public void setUpWarning(String upWarning) {
		this.upWarning = upWarning;
	}

	public String getLoadWarning() {
		return loadWarning;
	}

	public void setLoadWarning(String loadWarning) {
		this.loadWarning = loadWarning;
	}

	public String getWarningContent() {
		return warningContent;
	}

	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}
	public String getDnstime() {
		return dnstime;
	}
	public void setDnstime(String dnstime) {
		this.dnstime = dnstime;
	}
	public String getDnsadress() {
		return dnsadress;
	}
	public void setDnsadress(String dnsadress) {
		this.dnsadress = dnsadress;
	}
	public String getDnsport() {
		return dnsport;
	}
	public void setDnsport(String dnsport) {
		this.dnsport = dnsport;
	}
	public int getD_day() {
		return d_day;
	}
	public void setD_day(int d_day) {
		this.d_day = d_day;
	}
	
	
	
}
