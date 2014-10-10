package com.netconnection.entity;
@javax.persistence.Entity
@SuppressWarnings("serial")
public class TacticsList implements java.io.Serializable {

	private int tacticsid;
	private String tacticsname;
	private int blacktime;
	private int whitetime;
	private int patchtime;
	public int getBlacktime() {
		return blacktime;
	}
	public void setBlacktime(int blacktime) {
		this.blacktime = blacktime;
	}
	public int getWhitetime() {
		return whitetime;
	}
	public void setWhitetime(int whitetime) {
		this.whitetime = whitetime;
	}
	public int getPatchtime() {
		return patchtime;
	}
	public void setPatchtime(int patchtime) {
		this.patchtime = patchtime;
	}
	public int getTacticsid() {
		return tacticsid;
	}
	public void setTacticsid(int tacticsid) {
		this.tacticsid = tacticsid;
	}
	public String getTacticsname() {
		return tacticsname;
	}
	public void setTacticsname(String tacticsname) {
		this.tacticsname = tacticsname;
	}

}