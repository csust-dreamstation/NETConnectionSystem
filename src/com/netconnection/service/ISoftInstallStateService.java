package com.netconnection.service;

import java.util.List;

import com.netconnection.entity.Softinstallstate;

public interface ISoftInstallStateService {
	public boolean saveSoft(Softinstallstate soft);
	public boolean deleteSoft(Softinstallstate soft);
	public boolean updateSoft(Softinstallstate soft);
	public List<Softinstallstate> findSoftinstallstateByMac(String mac);
}
