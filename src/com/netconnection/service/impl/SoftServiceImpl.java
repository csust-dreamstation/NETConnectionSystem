package com.netconnection.service.impl;
import java.util.List;

import com.netconnection.dao.SoftManage;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
import com.netconnection.service.SoftService;
public class SoftServiceImpl implements SoftService {

	private SoftManage  softManage;

	public SoftManage getSoftManage() {
		return softManage;
	}

	public void setSoftManage(SoftManage softManage) {
		this.softManage = softManage;
	}

	public List<SoftList> findAll(){
		return this.softManage.findAll();
	}

	public SoftList findSoft(int j) {
		
		return this.softManage.findSoft(j);
	}

	public void updateSoftList(SoftList test) {
		this.softManage.updateSoftList(test);
		
	}

	public void addSoftList(SoftList softList) {
		this.softManage.addSoftList(softList);
		
	}

	@Override
	public List<PatchList> findPatchByid(int id) {
		return this.softManage.findPatchByid(id);
	}

	@Override
	public void intSoftList() {
		this.softManage.intSoftList();
		
	}


	@Override
	public void setPatchByid(int integer) {
		this.softManage.setPatchByid(integer);
		
	}

	@Override
	public List<SoftList> findByid(int id) {
		return this.softManage.findByid(id);
	}

	@Override
	public void setCheckedByid(int integer,int inte) {
		this.softManage.setCheckedByid(integer,inte);
		
	}


	@Override
	public void setUnPatchByid(int patchid) {
		this.softManage.setUnPatchByid(patchid);
		
	}

	@Override
	public void addPatchList(PatchList patchList) {
		this.softManage.addPatchList(patchList);
		
	}

	@Override
	public List<SoftList> findByPaging(int start, int number) {
		return softManage.findByPaging(start, number);
	}

	@Override
	public PatchList findPatchList(int parseInt) {
		return softManage.findPatchList(parseInt);
	}

	@Override
	public void deleteSoftlist(SoftList softlist) {
		this.softManage.deleteSoftlist(softlist);
		
	}

	@Override
	public void deletePatchlist(PatchList patchlist) {
		this.softManage.deletePatchlist(patchlist);
		
	}

	@Override
	public List<PatchList> finPatchByPaging(int start, int number) {
		return softManage.finPatchByPaging(start,number);
	}


	





}
