package com.netconnection.service.impl;
import java.util.List;
import com.netconnection.dao.TacticsPcManage;
import com.netconnection.service.TacticsPcService;
public class TacticsPcServiceImpl implements TacticsPcService {
   private TacticsPcManage tacticsPcManage;

@Override
public void saveTacticsPc(String string, int tacticsid) {
	//System.out.println("Ϊʲô������þͲ�����"+string+tacticsid);
	tacticsPcManage.saveTacticsPc(string,tacticsid);
	
}
@Override
public List findMacByTactics(int tacticsid) {
	return tacticsPcManage.findMacByTactics(tacticsid);
}
public TacticsPcManage getTacticsPcManage() {
	return tacticsPcManage;
}

public void setTacticsPcManage(TacticsPcManage tacticsPcManage) {
	this.tacticsPcManage = tacticsPcManage;
}

@Override
public void setCheckedByMac(String string) {
	this.tacticsPcManage.setCheckedByMac(string);
}
@Override
public void intPcinfo() {
	this.tacticsPcManage.intPcinfo();
	
}
@Override
public void deleteBytacticsid(int tacticsid) {
	this.tacticsPcManage.deleteBytacticsid(tacticsid);
	
}
@Override
public void deleteByMac(String string) {
	this.tacticsPcManage.deleteByMac(string);
	
}
@Override
public List findTacticsid(String mac) {
	return tacticsPcManage.findTacticsid(mac);
}













}
