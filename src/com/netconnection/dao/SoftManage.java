package com.netconnection.dao;
import java.util.List;

import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;

/**
 * @author penicillus 榛戝悕鍗曞垪琛ㄧ鐞�
 */
public interface SoftManage{
	public List<SoftList> findAll();//鏌ユ壘鎵�湁鐨勪俊鎭紱
	public SoftList findSoft(int j);//鏍规嵁id鎵惧埌鐩稿簲鐨勫璞★紱
	public void updateSoftList(SoftList test);//璺熸柊瀵硅薄
	public void addSoftList(SoftList softList);//娣诲姞瀵硅薄
	public List<PatchList> findPatchByid(int id);
	public void intSoftList();
	public void setPatchByid(int integer);
	public List<SoftList> findByid(int id);
	public void setCheckedByid(int integer, int inte);
	public void setUnPatchByid(int patchid);
	public void addPatchList(PatchList patchList);
	public List<SoftList> findByPaging(int start, int number);
	public PatchList findPatchList(int parseInt);
	public void deleteSoftlist(SoftList softlist);
	 void deletePatchlist(PatchList patchlist);
	 public String findSoftNameByThreadName(String threadname);
	public List<PatchList> finPatchByPaging(int start, int number);
}
