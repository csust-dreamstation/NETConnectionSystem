package com.netconnection.service;
import java.util.List;
import com.netconnection.entity.TacticsSoftList;
/**
 * @author penicillus
 *
 */
public interface TacticsPcService {
		public void saveTacticsPc(String string, int tacticsid);

		public List findMacByTactics(int tacticsid);

		public void setCheckedByMac(String string);

		public void intPcinfo();

		public void deleteBytacticsid(int tacticsid);

		public void deleteByMac(String string);

		public List findTacticsid(String mac);

}
