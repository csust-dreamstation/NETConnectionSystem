package com.netconnection.service;
import java.util.List;
import com.netconnection.entity.TacticsSoftList;
/**
 * @author penicillus
 *
 */
public interface TacticsSoftService {
		public List<TacticsSoftList> findByid(int id);
		public List<TacticsSoftList> findAll();
		public List findListId(int tacticsid,int statu);
		public void saveList(int tacticsid, int parseInt, int statu);
		public void deleteByTacticsStatu(int tacticsid, int statu);
		public void deleteBySoft(int i);
		public void deleteByPatchlist(int i);
}
