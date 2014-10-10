package com.netconnection.service.impl;
import java.util.List;

import com.netconnection.dao.TacticsSoftManage;
import com.netconnection.entity.TacticsSoftList;
import com.netconnection.service.TacticsSoftService;
public class TacticsSoftServiceImpl implements TacticsSoftService {
   private TacticsSoftManage tacticsSoftManage;

	public List<TacticsSoftList> findByid(int id) {
		return this.tacticsSoftManage.findByid(id);
	}


	public List<TacticsSoftList> findAll() {
		return this.tacticsSoftManage.findAll();
	}


	public List findListId(int tacticsid, int statu) {
		System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
		System.out.println(tacticsSoftManage.findListId(tacticsid, statu));
		return tacticsSoftManage.findListId(tacticsid, statu);
	}
	public TacticsSoftManage getTacticsSoftManage() {
		return tacticsSoftManage;
	}


	public void setTacticsSoftManage(TacticsSoftManage tacticsSoftManage) {
		this.tacticsSoftManage = tacticsSoftManage;
	}


	@Override
	public void saveList(int tacticsid, int parseInt, int statu) {
		this.tacticsSoftManage. saveList(tacticsid, parseInt, statu);
		
	}


	@Override
	public void deleteByTacticsStatu(int tacticsid, int statu) {
		this.tacticsSoftManage. deleteByTacticsStatu(tacticsid, statu);
		
	}


	@Override
	public void deleteBySoft(int i) {
		this.tacticsSoftManage. deleteBySoft(i);
		
	}


	@Override
	public void deleteByPatchlist(int i) {
		this.tacticsSoftManage.deleteByPatchlist(i); 
		
	}










}
