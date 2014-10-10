package com.netconnection.service;

import java.sql.Timestamp;
import java.util.List;

import com.netconnection.entity.CopyScreen;

public interface ICopyScreenService {
	public boolean saveCopyScreen(CopyScreen copyScreen);
	public CopyScreen findNearNoReadByMac(String mac,Timestamp sendTime);
	public void updateReadState(CopyScreen copyScreen);
	public List<CopyScreen> findRest();
	public void deleteCopyScreenInfo(CopyScreen copyScreen);
}
