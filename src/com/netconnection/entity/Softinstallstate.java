package com.netconnection.entity;

import java.sql.Timestamp;

/**
 * Softinstallstate entity. @author MyEclipse Persistence Tools
 */

public class Softinstallstate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mac;
	private String softname;
	private Integer installstate;
	private String version;
	private Timestamp recordtime;
	private String state;

	// Constructors


	/** default constructor */
	public Softinstallstate() {
	}

	/** full constructor */
	public Softinstallstate(String mac, String softname, Integer installstate,
			String version, Timestamp recordtime) {
		this.mac = mac;
		this.softname = softname;
		this.installstate = installstate;
		this.version = version;
		this.recordtime = recordtime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSoftname() {
		return this.softname;
	}

	public void setSoftname(String softname) {
		this.softname = softname;
	}

	public Integer getInstallstate() {
		return this.installstate;
	}

	public void setInstallstate(Integer installstate) {
		this.installstate = installstate;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Timestamp getRecordtime() {
		return this.recordtime;
	}

	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

}