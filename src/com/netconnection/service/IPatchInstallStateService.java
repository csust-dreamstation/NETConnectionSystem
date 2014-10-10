package com.netconnection.service;

import java.util.List;

import com.netconnection.entity.Patchinstallstate;

public interface IPatchInstallStateService {
	public List<Patchinstallstate> findPatchInstallstateByMac(String mac);
}
