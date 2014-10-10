package com.netconnection.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import cn.com.server.Services;
import cn.com.vo.Message;

import com.netconnection.entity.CopyScreen;
import com.netconnection.entity.Patchinstallstate;
import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.Softinstallstate;
import com.netconnection.service.ICopyScreenService;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.IPatchInstallStateService;
import com.netconnection.service.ISoftInstallStateService;
import com.opensymphony.xwork2.ActionSupport;

public class MonitorAction extends ActionSupport{
	private String mac;
	private String macList;
	private String optionList;
	private String sendMessage;
	private String optionType;
	private String retMsg;
	private JSONObject resultObj ;
	private String page;
	private String rows;
	private String condition;
	private String conditionValue;
	private String time;
	private Map<String,CopyScreen> copyScreenMap = new HashMap<String, CopyScreen>();
	private String upWarning;
	private String loadWarning;
	private String warningContent;
	private String statisticsMsg;//统计上线和下线人数的消息
	
	private Pcinfo pc;//客户端的详细信息
	private List<Softinstallstate> softlist;//软件安装信息
	private List<Patchinstallstate> patchlist;//补丁安装信息
	
	
	private IPCInfoService pcinfoService;
	private ICopyScreenService copyScreenService;
	private ISoftInstallStateService  softService;
	private IPatchInstallStateService patchService;
	private Services services;
	
	//分页查询数据库中所有的pc信息
	public String getAllPCByPage(){
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);
        int start = (intPage-1)*number;
		System.out.println("number:"+number);
		List<Pcinfo> onlineList = pcinfoService.findByPaging(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", pcinfoService.findAllCount());
		obj.put("rows", onlineList);
		this.setResultObj(JSONObject.fromObject(obj));
		
		return SUCCESS; 
	}
	
	public String getOnlinePcPage(){
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);
        int start = (intPage-1)*number;
		System.out.println("number:"+number);
		List<Pcinfo> onlineList = pcinfoService.findOnlinePcByPaging(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", pcinfoService.findOnlineNum());
		obj.put("rows", onlineList);
		this.setResultObj(JSONObject.fromObject(obj));
		return SUCCESS;
	}
	
	public String findPcinfoByConditionAndPage(){
		System.out.println("condition:"+condition+"conditionValue:"+conditionValue);
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);
        int start = (intPage-1)*number;
		List<Pcinfo> onlineList = pcinfoService.findByConditionAndPage(condition, conditionValue,start,number);
		Map<String,Object> obj = new HashMap<String,Object>();
		System.out.println("pcinfoService.findOnlineNum():"+pcinfoService.findOnlineNum());
		obj.put("total", pcinfoService.findOnlineNum());
		obj.put("rows", onlineList);
		this.setResultObj(JSONObject.fromObject(obj));
		return SUCCESS; 
	}
	//上线人数和下线人数的统计
	public String getStatistics(){
		int onlineCount = pcinfoService.findOnlineNum();
		int allCount = pcinfoService.findAllCount();
		StringBuffer msg = new StringBuffer("在线：");
		msg.append(onlineCount);
		msg.append("人,不在线：");
		msg.append(allCount-onlineCount);
		msg.append("人");
		statisticsMsg = msg.toString();		
		return SUCCESS;
	}
	
	public String sendCommod(){
		System.out.println("mac:"+mac);
		Message message = new Message();
		message.setMac(mac);
		message.setType(Integer.parseInt(optionType));
		boolean bool = services.send(message);
		if(bool == true){
			retMsg = "success";
		}else{
			retMsg = "fail";
		}
		return "success";
	}
	
	//发送某些命令到某些客户端
	public String sendSomeCommodToSome(){
		//将mac地址解析出来
		
		String[] macl = parseList(macList);
		String[] opl = parseList(optionList);
		
		System.out.println(optionList);
		for(int i = 0;i<opl.length;i++){
			for(int j = 0;j<macl.length;j++){
				services.send(new Message(macl[j], Integer.parseInt(opl[i]), sendMessage));
			}
		}
		retMsg = "success";
		return "success";
	}
	
	//解析前台发回的mac数组和操作数组
	private String[] parseList(String s){
		String str = s.substring(1, s.length()-1);
		str = str.replace("\"", "");
		String[] strl = str.split(",");
		return strl;
	}
	
	public String getCopyScreenByOne(){
		System.out.println("getCopyScreenByOne:"+mac);
		System.out.println("time:"+time);
		
		Timestamp ts = new Timestamp(Long.parseLong(time));
		CopyScreen cs = copyScreenService.findNearNoReadByMac(mac,ts);
		
		if(cs == null){
			retMsg = "fail";
		}else{
			copyScreenService.updateReadState(cs);
			copyScreenMap.put(mac, cs);
			retMsg = cs.getPath();
		}
		
		return SUCCESS;
	}
	
	//删除已经显示的图片信息
	public String deleteCopyScreen(){
		System.out.println("deletecopyscreen");
		CopyScreen cs = copyScreenMap.get(mac);
		copyScreenService.deleteCopyScreenInfo(cs);
		return INPUT;
		
	}
	
	//显示详细的客户端信息
	public String pcinformation(){
		System.out.println("pc_mac:"+mac);
		pc = pcinfoService.findByMac(mac);
		softlist = softService.findSoftinstallstateByMac(mac);
		patchlist = patchService.findPatchInstallstateByMac(mac);
		return INPUT;
	}
	
	public JSONObject getResultObj() {
		return resultObj;
	}
	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getMacList() {
		return macList;
	}

	public void setMacList(String macList) {
		this.macList = macList;
	}

	public String getOptionList() {
		return optionList;
	}

	public void setOptionList(String optionList) {
		this.optionList = optionList;
	}
	
	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
	}
	
	public IPCInfoService getPcinfoService() {
		return pcinfoService;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public ICopyScreenService getCopyScreenService() {
		return copyScreenService;
	}

	public void setCopyScreenService(ICopyScreenService copyScreenService) {
		this.copyScreenService = copyScreenService;
	}

	public ISoftInstallStateService getSoftService() {
		return softService;
	}

	public IPatchInstallStateService getPatchService() {
		return patchService;
	}

	public void setPatchService(IPatchInstallStateService patchService) {
		this.patchService = patchService;
	}

	public void setSoftService(ISoftInstallStateService softService) {
		this.softService = softService;
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

	public Pcinfo getPc() {
		return pc;
	}

	public void setPc(Pcinfo pc) {
		this.pc = pc;
	}

	public List<Softinstallstate> getSoftlist() {
		return softlist;
	}

	public void setSoftlist(List<Softinstallstate> softlist) {
		this.softlist = softlist;
	}

	public List<Patchinstallstate> getPatchlist() {
		return patchlist;
	}

	public void setPatchlist(List<Patchinstallstate> patchlist) {
		this.patchlist = patchlist;
	}

	public String getStatisticsMsg() {
		return statisticsMsg;
	}

	public void setStatisticsMsg(String statisticsMsg) {
		statisticsMsg = statisticsMsg;
	}
	
	
	
	
}
