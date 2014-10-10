package com.netconnection.dao;
import java.util.List;

import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;


public interface ReceiveMessageManage{
	void saveSoftInstallState(String mac, String str, String string, String string2);

	void savePatchInstallState(String mac, String str, String string);


}
