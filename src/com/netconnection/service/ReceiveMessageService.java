package com.netconnection.service;
import java.util.List;

import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
/**
 * @author penicillus
 *
 */
public interface ReceiveMessageService {


	void saveSoftInstallState(String mac, String str, String string, String strr);

	void savePatchInstallState(String mac, String str, String string);

		
}
