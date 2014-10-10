package com.netconnection.service;
import java.util.List;

import com.netconnection.dao.IllegalDAO;
import com.netconnection.entity.Illegal;
public interface IllegalService {
	public boolean saveIllegal(Illegal illegal);
	public boolean updateIllegal(Illegal illegal);
	public boolean deleteIllegal(Illegal illegal);
	public Illegal findByMac(String mac);
	public List<Illegal> findByPaging(int first,int max);
	public List<Illegal> findAll();
}
