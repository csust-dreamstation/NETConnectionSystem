package com.netconnection.service.impl;
import java.util.List;
import com.netconnection.dao.TacticsManage;
import com.netconnection.entity.TacticsList;
import com.netconnection.service.TacticsService;
public class TacticsServiceImpl implements TacticsService {

	private TacticsManage tacticsManage;


	public List<TacticsList> findByid(int id){
		return this.tacticsManage.findByid(id);
	}
	
	public TacticsManage getTacticsManage() {
		return tacticsManage;
	}

	public void setTacticsManage(TacticsManage tacticsManage) {
		this.tacticsManage = tacticsManage;
	}

	public List<TacticsList> findAll(){
		return this.tacticsManage.findAll();
	}

	@Override
	public void addtacticsList(TacticsList tacticsList) {
	 this.tacticsManage.addtacticsList(tacticsList);
		
	}



	@Override
	public void deletetacticsList(int tacticsid) {
		this.tacticsManage.deletetacticsList(tacticsid);
		
	}

	@Override
	public TacticsList findByTacticsid(int tacticsid) {
		return tacticsManage. findByTacticsid(tacticsid);
	}

	@Override
	public List findIdByName(String tacticsname) {
		return tacticsManage. findIdByName(tacticsname);
	}

	@Override
	public void updatetacticsList(TacticsList tacticsList) {
		this.tacticsManage.updatetacticsList(tacticsList);
		
	}




}
