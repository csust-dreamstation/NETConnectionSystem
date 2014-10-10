package com.netconnection.action;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
import com.netconnection.service.SoftService;
import com.netconnection.service.TacticsService;
import com.netconnection.service.TacticsSoftService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author penicillus 
*根据关系表中的记录，在相应的表中设置相应的checked；
*取得list后，将list返回前台页面
 */
@SuppressWarnings("serial")
public class SoftListAction extends ActionSupport{
	private int id;
	private int statu;
	private int tacticsid;
	private String tacticsname;
	private String status;
	private String test;
	private String softname;
    private String threadname;
	private String page;
	private String rows;
	private JSONObject resultObj ;
	List<SoftList> blackList = new ArrayList<SoftList>();
	List<SoftList> whiteList = new ArrayList<SoftList>();
	List<PatchList> patchList = new ArrayList<PatchList>();
    private TacticsService tacticsService;
	private SoftService softService; 
	private TacticsSoftService tacticsSoftService;
	public String execute(){
		return "success";
	}
	public String getBlackList1() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String tacticsname = new String(request.getParameter("tacticsname").getBytes("iso8859-1"),"utf-8");
		this.tacticsid=(Integer) tacticsService.findIdByName(tacticsname).get(0);
		softService.intSoftList();
		this.statu=0;
		List idList=tacticsSoftService.findListId(tacticsid, statu);//寻找策略与黑名单中的软件名单
    	for (int i = 0; i <idList.size(); i++)  
    	softService.setCheckedByid((Integer) idList.get(i),statu);
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        int start = (intPage-1)*number;
		blackList=softService.findByPaging(start, number); 
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", softService.findAll().size());
		obj.put("rows", blackList);
		System.out.println("这里是黑名单的"+JSONObject.fromObject(obj));
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	public String getWhiteList1() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String tacticsname = new String(request.getParameter("tacticsname").getBytes("iso8859-1"),"utf-8");
        this.tacticsid=(Integer) tacticsService.findIdByName(tacticsname).get(0);
		softService.intSoftList();
		this.statu=1;
		List idList=tacticsSoftService.findListId(tacticsid, statu);//寻找策略与黑名单中的软件名单
    	for (int i = 0; i <idList.size(); i++)  
    	softService.setCheckedByid((Integer) idList.get(i),statu);   
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        int start = (intPage-1)*number;
		whiteList=softService.findByPaging(start, number); 
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", softService.findAll().size());
		obj.put("rows", whiteList);
		System.out.println("这里是白名单的"+JSONObject.fromObject(obj));
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	public String getPatchList1() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String tacticsname = new String(request.getParameter("tacticsname").getBytes("iso8859-1"),"utf-8");
        this.tacticsid=(Integer) tacticsService.findIdByName(tacticsname).get(0);
    	patchList=softService.findPatchByid(tacticsid); 
    	for (int i = 0; i <patchList.size(); i++)  
    	    softService.setUnPatchByid(patchList.get(i).getPatchid());
		this.statu=2;
		List<Integer> idList=tacticsSoftService.findListId(tacticsid, statu);//寻找策略与黑名单中的补丁名单
		for (int i = 0; i <idList.size(); i++)  
    	    softService.setPatchByid((Integer)idList.get(i));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "10":rows);  
        int start = (intPage-1)*number;
        patchList=softService.finPatchByPaging(start, number); //获得list
        System.out.println("获得的补丁长度"+patchList.size());
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", softService.findPatchByid(1).size());
		obj.put("rows", patchList);
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

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTacticsid() {
		return tacticsid;
	}
	public void setTacticsid(int tacticsid) {
		this.tacticsid = tacticsid;
	}
	public List<SoftList> getBlackList() {
		return blackList;
	}
	public void setBlackList(List<SoftList> blackList) {
		this.blackList = blackList;
	}
	public List<SoftList> getWhiteList() {
		return whiteList;
	}
	public void setWhiteList(List<SoftList> whiteList) {
		this.whiteList = whiteList;
	}
	public List<PatchList> getPatchList() {
		return patchList;
	}
	public void setPatchList(List<PatchList> patchList) {
		this.patchList = patchList;
	}
	public TacticsSoftService getTacticsSoftService() {
		return tacticsSoftService;
	}
	public void setTacticsSoftService(TacticsSoftService tacticsSoftService) {
		this.tacticsSoftService = tacticsSoftService;
	}
	public String getThreadname() {
		return threadname;
	}
	public void setThreadname(String threadname) {
		this.threadname = threadname;
	}
	public String getSoftname() {
		return softname;
	}
	public void setSoftname(String softname) {
		this.softname = softname;
	}

	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public SoftService getSoftService() {
		return softService;
	}
	public void setSoftService(SoftService softService) {
		this.softService = softService;
	}


	
}
