package com.netconnection.service.impl;

import java.util.List;

import com.netconnection.dao.SoftManage;
import com.netconnection.dao.impl.SoftinstallstateDAO;
import com.netconnection.entity.Softinstallstate;
import com.netconnection.service.ISoftInstallStateService;

public class SoftInstallStateServiceImpl implements ISoftInstallStateService{
	private SoftinstallstateDAO softDAO;
	private SoftManage softManage;
	
	@Override
	public boolean saveSoft(Softinstallstate soft) {
		return softDAO.save(soft);
	}

	@Override
	public boolean deleteSoft(Softinstallstate soft) {
		// TODO Auto-generated method stub
		return softDAO.delete(soft);
	}

	@Override
	public boolean updateSoft(Softinstallstate soft) {
		// TODO Auto-generated method stub
		return softDAO.update(soft);
	}
	
	
	@Override
	public List<Softinstallstate> findSoftinstallstateByMac(String mac) {
		List<Softinstallstate> installlist = softDAO.findByMac(mac);
		for(Softinstallstate si: installlist){
			si.setSoftname(softManage.findSoftNameByThreadName(si.getSoftname()));
		}
		return installlist;
	}	
	
	public SoftinstallstateDAO getSoftDAO() {
		return softDAO;
	}

	public void setSoftDAO(SoftinstallstateDAO softDAO) {
		this.softDAO = softDAO;
	}

	public SoftManage getSoftManage() {
		return softManage;
	}

	public void setSoftManage(SoftManage softManage) {
		this.softManage = softManage;
	}
	
	
	
}
