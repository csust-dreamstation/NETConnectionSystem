package com.netconnection.entity;

import java.sql.Timestamp;

/**
 * patchinstallstate entity. @author MyEclipse Persistence Tools
 */

public class Patchinstallstate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mac;
	private String patchname;
	private Integer installstate;
	private String version;
	private Timestamp recordtime;

	// Constructors

	/** default constructor */
	public Patchinstallstate() {
	}

	/** full constructor */
	public Patchinstallstate(String mac, String patchname, Integer installstate,
			String version, Timestamp recordtime) {
		this.mac = mac;
		this.patchname = patchname;
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

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getpatchname() {
		return this.patchname;
	}

	public void setpatchname(String patchname) {
		this.patchname = patchname;
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