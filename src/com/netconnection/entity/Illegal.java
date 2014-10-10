package com.netconnection.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Pcinfo entity. @author MyEclipse Persistence Tools
 */

public class Illegal implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private String mac;
	private String clientname;
	private String os;
	private Timestamp recordtime;
	private int illegal;
	private String itime;
	private int flow;
	private String ftime;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Timestamp getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}
	public int getIllegal() {
		return illegal;
	}
	public void setIllegal(int illegal) {
		this.illegal = illegal;
	}
	public String getItime() {
		return itime;
	}
	public void setItime(String itime) {
		this.itime = itime;
	}
	public int getFlow() {
		return flow;
	}
	public void setFlow(int flow) {
		this.flow = flow;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	// Constructors



	
}