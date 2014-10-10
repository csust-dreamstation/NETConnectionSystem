package com.netconnection.entity;
@javax.persistence.Entity
@SuppressWarnings("serial")
public class TacticsPc implements java.io.Serializable {
	private int id;
	private int tacticsid;
	private String mac;
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
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}








}