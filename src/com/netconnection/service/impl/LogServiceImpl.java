package com.netconnection.service.impl;

import java.util.List;

import com.netconnection.dao.impl.LogDAO;
import com.netconnection.entity.Log;
import com.netconnection.service.ILogService;

public class LogServiceImpl implements ILogService {
	private LogDAO logDAO;
	public LogDAO getLogDAO() {
		return logDAO;
	}
	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}
	@Override
	public boolean saveLog(Log log) {
		return logDAO.save(log);
	}
	@Override
	public int findAllCount() {
		return logDAO.findAllCount().intValue();
	}
	@Override
	public List<Log> findByPaging(int start, int number) {
		String hql = "from Log log order by log.time desc";
		return logDAO.findLogListeByPaging(hql, start, number);
	}
	@Override
	public List<Log> findLogByCondition(String condition,int start, int number) {
		return logDAO.findLogByCondition(condition,start,number);
	}
	
	
	@Override
	public boolean deleteAllData(){
		return logDAO.deleteAllData();
	}
	
	@Override
	public int findByConditionCount() {
		return logDAO.getConditionCount();
	}
	@Override
	public List<Log> findAll() {	
		return logDAO.findAll();
	}
	@Override
	public void delete(Log log) {
		logDAO.delete(log);
		
	}
}
