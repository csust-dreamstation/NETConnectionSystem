package com.netconnection.entity;
@javax.persistence.Entity
@SuppressWarnings("serial")
public class SoftList implements java.io.Serializable {

	private int softid;
	private String softname;
	private String threadname;
	private String choose;
	private int statu;



	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}

	public String getThreadname() {
		return threadname;
	}

	public void setThreadname(String threadname) {
		this.threadname = threadname;
	}

	public int getSoftid() {
		return softid;
	}

	public void setSoftid(int softid) {
		this.softid = softid;
	}

	public String getSoftname() {
		return softname;
	}

	public void setSoftname(String softname) {
		this.softname = softname;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}











}