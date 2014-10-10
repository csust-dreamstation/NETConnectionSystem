package com.netconnection.entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * Pcinfo entity. @author MyEclipse Persistence Tools
 */

public class Pcinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private String mac;
	private String clientname;
	private String os;
	private Long loadflow;
	private Long upflow;
	private Integer onlinestate;
	private Timestamp recordtime;
	private String statu;
	private Integer warnstate;
	private String softlist;
	private String esoftlist;
	private String cpu;
	private String memory;
	private String allmemory;
	private String availablity;
	private String itime;
	private String ftime;
	private int pctype;
	private int wifichk;
	// Constructors




	public String getCpu() {
		return cpu;
	}

	public int getPctype() {
		return pctype;
	}

	public void setPctype(int pctype) {
		this.pctype = pctype;
	}

	public int getWifichk() {
		return wifichk;
	}

	public void setWifichk(int wifichk) {
		this.wifichk = wifichk;
	}

	public String getItime() {
		return itime;
	}

	public void setItime(String itime) {
		this.itime = itime;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getAllmemory() {
		return allmemory;
	}

	public void setAllmemory(String allmemory) {
		this.allmemory = allmemory;
	}

	public String getAvailablity() {
		return availablity;
	}

	public void setAvailablity(String availablity) {
		this.availablity = availablity;
	}

	/** default constructor */
	public Pcinfo() {
	}

	/** full constructor */

	public Pcinfo(String ip, String mac, String clientname, String os,
			Long loadflow, Long upflow, Integer onlinestate,
			Timestamp recordtime, String statu, String cpu,
			String memory, String allmemory, String availablity) {
		super();
		this.ip = ip;
		this.mac = mac;
		this.clientname = clientname;
		this.os = os;
		this.loadflow = loadflow;
		this.upflow = upflow;
		this.onlinestate = onlinestate;
		this.recordtime = recordtime;
		this.statu = statu;
		this.cpu = cpu;
		this.memory = memory;
		this.allmemory = allmemory;
		this.availablity = availablity;
	}

	// Property accessors
	public String getEsoftlist() {
		return esoftlist;
	}

	public void setEsoftlist(String esoftlist) {
		this.esoftlist = esoftlist;
	}
	public String getSoftlist() {
		return softlist;
	}

	public void setSoftlist(String softlist) {
		this.softlist = softlist;
	}
	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}
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

	public String getClientname() {
		return this.clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Long getLoadflow() {
		return this.loadflow;
	}

	public void setLoadflow(Long loadflow) {
		this.loadflow = loadflow;
	}

	public Long getUpflow() {
		return this.upflow;
	}

	public void setUpflow(Long upflow) {
		this.upflow = upflow;
	}

	public Integer getOnlinestate() {
		return this.onlinestate;
	}

	public void setOnlinestate(Integer onlinestate) {
		this.onlinestate = onlinestate;
	}

	public Timestamp getRecordtime() {
		return this.recordtime;
	}

	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

	public Integer getWarnstate() {
		return warnstate;
	}

	public void setWarnstate(Integer warnstate) {
		this.warnstate = warnstate;
	}
	
}