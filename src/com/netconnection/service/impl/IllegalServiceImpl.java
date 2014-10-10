package com.netconnection.service.impl;
import java.util.List;
import com.netconnection.dao.IllegalDAO;
import com.netconnection.entity.Illegal;
import com.netconnection.service.IllegalService;

public class IllegalServiceImpl implements IllegalService {
	private IllegalDAO illegalDAO;
	@Override

	public boolean saveIllegal(Illegal illegal) {
		return illegalDAO.saveIllegal(illegal);
	}

	@Override
	public boolean updateIllegal(Illegal illegal) {
		return illegalDAO.updateIllegal(illegal);
	}

	@Override
	public boolean deleteIllegal(Illegal illegal) {
		return illegalDAO.deleteIllegal(illegal);
	}

	@Override
	public Illegal findByMac(String mac) {
		return illegalDAO.findByMac(mac);
	}

	@Override
	public List<Illegal> findByPaging(int first, int max) {
		return illegalDAO.findByPaging(first,max);
	}

	@Override
	public List<Illegal> findAll() {
		return illegalDAO.findAll();
	}
	public IllegalDAO getIllegalDAO() {
		return illegalDAO;
	}
	public void setIllegalDAO(IllegalDAO illegalDAO) {
		this.illegalDAO = illegalDAO;
	}

	
}
