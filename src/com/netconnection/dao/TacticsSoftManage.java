package com.netconnection.dao;
import java.util.List;
import com.netconnection.entity.TacticsSoftList;

/**
 * @author penicillus 榛戝悕鍗曞垪琛ㄧ锟�
 */
public interface TacticsSoftManage{
	public List<TacticsSoftList> findByid(int id);  //鏍规嵁id鏌ユ壘锟�锟斤拷鐨勪俊鎭紱
	public List<TacticsSoftList> findAll();//鏌ユ壘锟�锟斤拷鐨勪俊鎭紱
	public List findListId(int tacticsid, int statu);
	public void saveList(int tacticsid, int parseInt, int statu);
	public void deleteByTacticsStatu(int tacticsid, int statu);
	public void deleteBySoft(int i);
	public void deleteByPatchlist(int i);
}
