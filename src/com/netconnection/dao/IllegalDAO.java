package com.netconnection.dao;

import java.util.List;

import com.netconnection.entity.Illegal;

public interface IllegalDAO {
	public boolean saveIllegal(Illegal illegal);
	public boolean updateIllegal(Illegal illegal);
	public boolean deleteIllegal(Illegal illegal);
	public Illegal findByMac(String mac);
	public List<Illegal> findByPaging(int first,int max);
	public List<Illegal> findAll();
}
