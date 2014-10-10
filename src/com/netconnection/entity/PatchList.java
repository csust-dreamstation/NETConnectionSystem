package com.netconnection.entity;
@javax.persistence.Entity
@SuppressWarnings("serial")
public class PatchList implements java.io.Serializable {

	private int patchid;
	private String patchname;
	private String choose;
	private String degree;
	private String os;
	private String description;



	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public String getPatchname() {
		return patchname;
	}

	public void setPatchname(String patchname) {
		this.patchname = patchname;
	}

	public int getPatchid() {
		return patchid;
	}

	public void setPatchid(int patchid) {
		this.patchid = patchid;
	}






}