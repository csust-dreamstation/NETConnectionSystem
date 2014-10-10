package com.netconnection.entity;

import java.sql.Timestamp;

/**
 * Onlinetime entity. @author MyEclipse Persistence Tools
 */

public class Onlinetime implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private String mac;
	private String begintime;
	private String endtime;
	private String clientname;
	

	// Constructors

	/** default constructor */
	public Onlinetime() {
	}

	/** minimal constructor */
	public Onlinetime(String ip, String mac, String begintime) {
		this.ip = ip;
		this.mac = mac;
		this.begintime = begintime;
	}

	/** full constructor */
	public Onlinetime(String ip, String mac, String begintime,
			String endtime) {
		this.ip = ip;
		this.mac = mac;
		this.begintime = begintime;
		this.endtime = endtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getBegintime() {
		return this.begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	
	
}