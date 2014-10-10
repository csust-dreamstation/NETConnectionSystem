package com.netconnection.service.impl;
import java.util.List;

import com.netconnection.dao.ReceiveMessageManage;
import com.netconnection.dao.SoftManage;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
import com.netconnection.service.ReceiveMessageService;
import com.netconnection.service.SoftService;
/**
 * @author penicillus
 *
 */
class ReceiveMessageServiceImpl implements ReceiveMessageService{
	private ReceiveMessageManage  receiveMessageManage ;

	public ReceiveMessageManage getReceiveMessageManage() {
		return receiveMessageManage;
	}

	public void setReceiveMessageManage(ReceiveMessageManage receiveMessageManage) {
		this.receiveMessageManage = receiveMessageManage;
	}

	@Override
	public void saveSoftInstallState(String state,String mac, String str, String string) {
		receiveMessageManage.saveSoftInstallState(state,mac,str,string);
		
	}

	@Override
	public void savePatchInstallState(String mac, String str, String string) {
		receiveMessageManage.savePatchInstallState(mac,str,string);
		
	}

		
		
}
