package com.netconnection.entity;

import java.sql.Timestamp;

public class CopyScreen {
	private int id;
	private String mac;
	private String path;
	private int state;
	private Timestamp recordtime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Timestamp getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}
	
}
