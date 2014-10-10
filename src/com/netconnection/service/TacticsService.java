package com.netconnection.service;
import java.util.List;
import com.netconnection.entity.TacticsList;
/**
 * @author penicillus
 *
 */
public interface TacticsService {
		public List <TacticsList> findByid(int id);
		public List<TacticsList> findAll();
		public void addtacticsList(TacticsList tacticsList);
		public void deletetacticsList(int tacticsid);
		public TacticsList findByTacticsid(int tacticsid);
		public List findIdByName(String tacticsname);
		public void updatetacticsList(TacticsList tacticsList);
}
