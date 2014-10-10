package com.netconnection.service;

import java.util.List;

import com.netconnection.entity.Pcinfo;

public interface IPCInfoService {
	public final static  int ONLINESTATE = 1;
	public final static  int NO_ONLINESTATE = 0;
	public final static  int DEL_ONLINESTATE = 2;
	public final static  int NO_WARNSTATE = 0;
	public final static  int UP_WARNSTATE = 1;
	public final static  int LOAD_WARNSTATE = 2;
	public final static  int BOTH_WARNSTATE = 3;

	public List<Pcinfo> findOnline();
	public boolean savePCInfo(Pcinfo pcinfo);
	public boolean updatePCInfo(Pcinfo pcinfo);
	public boolean deletePCInfo(Pcinfo pcinfo);
	public Pcinfo findByMac(String mac);
	public List<Pcinfo> findByPaging(int first,int max);
	public List<Pcinfo> findOnlinePcByPaging(int first,int max);
	public List<Pcinfo> findByConditionAndPage(String condition, String value,int first, int max);	
	public int findOnlineNum();
	public int findAllCount();
	public List<Pcinfo> findAll();
}
