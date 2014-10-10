package com.netconnection.service;

import java.sql.Timestamp;
import java.util.List;

import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.Pcinfo;

public interface IOnlineTimeService {
	/**
	 * 保存上线的记录
	 * @param time
	 * @return
	 */
	public boolean saveOnlineTime(Onlinetime time);
	/**
	 * 保存下线的时间
	 * @param timestamp
	 * @return
	 */
	public boolean saveEndTime(String mac,String timestamp);
	/**
	 * 根据mac找到该客户端上网时间的所有记录
	 * @param mac
	 * @return
	 */
	public List<Onlinetime> findByMac(String mac);
	/**
	 *根据时间段来查找该时间内在上网的客户端
	 * @param starttime
	 * @param endtime
	 * @return 返回在该时间段内的pcinfo
	 */
	public List<Pcinfo> findByTime(Timestamp starttime,Timestamp endtime);
	
	/**
	 * 通过时长来查询上网的客户端（如:从今天早上起上网超过多少小时的客户端）
	 * @param starttime
	 * @param timelong
	 * @return
	 */
	public List<Pcinfo> findByTimeStatment(Timestamp starttime,long timelong);
	
	public List<Onlinetime> findAllOnlineTime();
	
	public List<Onlinetime> findOnlineTimeByCondition(String condition,int start,int number);
	public int getConditionCount();
	public List<Onlinetime> findOnlineTimeByPaging(int start, int number);
	public void delonlinetime(Onlinetime onlinetime);
	
}
