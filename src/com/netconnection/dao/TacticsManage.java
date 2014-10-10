package com.netconnection.dao;

import java.util.List;

import com.netconnection.entity.TacticsList;

public interface TacticsManage {
	public List <TacticsList> findByid(int id);  //根据id查找所有的信息；
	public List<TacticsList> findAll();//查找所有的信息；
	public void addtacticsList(TacticsList tacticsList);
	public void deletetacticsList(int tacticsid);
	public TacticsList findByTacticsid(int tacticsid);
	public List findIdByName(String tacticsname);
	public void updatetacticsList(TacticsList tacticsList);
}
