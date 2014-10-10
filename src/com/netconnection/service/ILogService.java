package com.netconnection.service;

import java.util.List;

import org.aspectj.lang.JoinPoint;

import com.netconnection.entity.Log;

public interface ILogService {
	public boolean saveLog(Log log);
	public int findAllCount();
	public List<Log> findByPaging(int start, int number);
	public List<Log> findLogByCondition(String condition,int start, int number);
	public int findByConditionCount();
	public boolean deleteAllData();
	public List<Log> findAll();
	public void delete(Log log);
}
