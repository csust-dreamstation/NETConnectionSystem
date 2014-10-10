package com.netconnection.service.impl;

import java.util.List;

import com.netconnection.dao.impl.PatchinstallstateDAO;
import com.netconnection.entity.Patchinstallstate;
import com.netconnection.service.IPatchInstallStateService;

public class PatchInstallStateServiceImpl implements IPatchInstallStateService {
	private PatchinstallstateDAO patchDao;

	@Override
	public List<Patchinstallstate> findPatchInstallstateByMac(String mac) {
		return patchDao.findByMac(mac);
	}

	public PatchinstallstateDAO getPatchDao() {
		return patchDao;
	}

	public void setPatchDao(PatchinstallstateDAO patchDao) {
		this.patchDao = patchDao;
	}
	

}
