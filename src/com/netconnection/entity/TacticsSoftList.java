package com.netconnection.entity;
@javax.persistence.Entity
@SuppressWarnings("serial")
public class TacticsSoftList implements java.io.Serializable {
	private int id;
	private int tacticsid;
	private int listid;
	private int statu;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTacticsid() {
		return tacticsid;
	}
	public void setTacticsid(int tacticsid) {
		this.tacticsid = tacticsid;
	}
	public int getListid() {
		return listid;
	}
	public void setListid(int listid) {
		this.listid = listid;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}




}