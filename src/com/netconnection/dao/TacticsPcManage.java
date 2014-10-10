package com.netconnection.dao;
import java.util.List;


/**
 * @author penicillus 姒涙垵鎮曢崡鏇炲灙鐞涖劎顓搁敓锟� */
public interface TacticsPcManage{
	public void saveTacticsPc(String string, int tacticsid);

	public List findMacByTactics(int tacticsid);

	public void setCheckedByMac(String string);

	public List intPcinfo();

	public void deleteBytacticsid(int tacticsid);

	public void deleteByMac(String string);

	public List findTacticsid(String mac);

}
