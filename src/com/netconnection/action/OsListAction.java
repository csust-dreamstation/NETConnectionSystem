package com.netconnection.action;
import java.util.ArrayList;
import java.util.Iterator;
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
public class OsListAction extends ActionSupport{
	private IPCInfoService pcinfoService;
	private List<Pcinfo> OsList = new ArrayList<Pcinfo>();
	public String execute(){
		OsList=pcinfoService.findAll();
		for(int i = 0; i < OsList.size(); i++){
			for (int j = 0; j < i; j++) {
				
				if(OsList.get(i).getOs().equals(OsList.get(j).getOs())){
					OsList.remove(i);
				i--;
				}
			}	
		}
		System.out.println(OsList);
        System.out.println(OsList.size());
		return "success";
	}
	public IPCInfoService getPcinfoService() {
		return pcinfoService;
	}
	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
	}
	public List<Pcinfo> getOsList() {
		return OsList;
	}
	public void setOsList(List<Pcinfo> osList) {
		OsList = osList;
	}


	
}
