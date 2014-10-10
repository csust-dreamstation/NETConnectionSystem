package com.netconnection.service;
import java.util.List;

import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
/**
 * @author penicillus
 *
 */
public interface SoftService {
		public List<SoftList> findAll();
		public SoftList findSoft(int j);
		public void updateSoftList(SoftList test);
		public void addSoftList(SoftList softList);
		public List<PatchList> findPatchByid(int id);
		public void intSoftList();
		public void setPatchByid(int integer);
		public List<SoftList> findByid(int id);
		public void setCheckedByid(int integer, int statu);
		public void setUnPatchByid(int patchid);
		public void addPatchList(PatchList patchList);
		public List<SoftList> findByPaging(int start, int number);
		public PatchList findPatchList(int parseInt);
		public void deleteSoftlist(SoftList softlist);
		public void deletePatchlist(PatchList patchlist);
		public List<PatchList> finPatchByPaging(int start, int number);
		
}
