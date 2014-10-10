package com.netconnection.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.netconnection.dao.impl.OnlinetimeDAO;
import com.netconnection.dao.impl.PcinfoDAO;
import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.Pcinfo;
import com.netconnection.service.IOnlineTimeService;

public class OnlineTimeServiceImpl implements IOnlineTimeService{
	private OnlinetimeDAO onlineTimeDao;
	private PcinfoDAO pcinfoDao;
	@Override
	public boolean saveOnlineTime(Onlinetime time) {
		
		return onlineTimeDao.save(time);
	}

	@Override
	public boolean saveEndTime(String mac, String timestamp) {
		Onlinetime online = (Onlinetime)onlineTimeDao.findNewByMac(mac);
		if(online!=null){
		online.setEndtime(timestamp);
		return onlineTimeDao.update(online);
		}else{
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Onlinetime> findByMac(String mac) {
		return (List<Onlinetime>)onlineTimeDao.findByMac(mac);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Onlinetime> findAllOnlineTime() {
		return (List<Onlinetime>)onlineTimeDao.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pcinfo> findByTime(Timestamp starttime, Timestamp endtime) {
		List<String> macList = onlineTimeDao.findMacByTime(starttime, endtime);
		if(macList.size() == 0){
			System.out.println("dao");
			return null;
		}
		List<Pcinfo> pcinfoList = new ArrayList<Pcinfo>();
		for(String mac:macList){
			System.out.println(mac);
			List<Pcinfo> pcList = (List<Pcinfo>) pcinfoDao.findByMac(mac);
			pcinfoList.add(pcList.get(0));
		}
		return pcinfoList;
	}

	@Override
	public List<Pcinfo> findByTimeStatment(Timestamp starttime, long timelong) {
		List<String> macList = onlineTimeDao.findMacByTimeStatment(starttime, timelong);
		if(macList == null||macList.size() == 0){
			return null;
		}
		List<Pcinfo> pcinfoList = new ArrayList<Pcinfo>();
		for(String mac:macList){
			Pcinfo pc = (Pcinfo) pcinfoDao.findByMac(mac);
			pcinfoList.add(pc);
		}
		return pcinfoList;
	}
	
	public OnlinetimeDAO getOnlineTimeDao() {
		return onlineTimeDao;
	}

	public void setOnlineTimeDao(OnlinetimeDAO onlineTimeDao) {
		this.onlineTimeDao = onlineTimeDao;
	}

	public PcinfoDAO getPcinfoDao() {
		return pcinfoDao;
	}

	public void setPcinfoDao(PcinfoDAO pcinfoDao) {
		this.pcinfoDao = pcinfoDao;
	}

	@Override
	public List<Onlinetime> findOnlineTimeByCondition(String condition,int start, int number) {
		return onlineTimeDao.findOnlineTimeByCondition(condition,start,number);
	}

	@Override
	public List<Onlinetime> findOnlineTimeByPaging(int start, int number) {
		return onlineTimeDao.findOnlineTimeByPaging(start,number);
	}

	@Override
	public int getConditionCount() {
		return onlineTimeDao.getConditioncount();
	}

	@Override
	public void delonlinetime(Onlinetime onlinetime) {
		onlineTimeDao.delete(onlinetime);	
	}

	

	
	
}
