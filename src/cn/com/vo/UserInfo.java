package cn.com.vo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
public class UserInfo {
	private String username;
	private String IP;
	private String mac;//mac�汾
	private byte lifetime;
	private String os;//os�汾
	private DataInputStream in;
	private DataOutputStream out;
	private boolean state;//�û��Ƿ�����,true��ʾ����,false��ʾ����
	//记录上一次的流量数据
//	private int preFlowin = 0;
//	private int preFlowout = 0;
	
	private int flowin;//
	private int flowout;//�������
	public int getUpcount() {
		return upcount;
	}

	public void setUpcount(int upcount) {
		this.upcount = upcount;
	}

	public int getLoadcount() {
		return loadcount;
	}

	public void setLoadcount(int loadcount) {
		this.loadcount = loadcount;
	}

	private int upcount;
	private int loadcount;
	public UserInfo() {
	}
	


	public UserInfo(String username, String iP, String mac, byte lifetime,
			String os, DataInputStream in, DataOutputStream out, boolean state,
			int flowin, int flowout, int upcount, int loadcount) {
		super();
		this.username = username;
		IP = iP;
		this.mac = mac;
		this.lifetime = lifetime;
		this.os = os;
		this.in = in;
		this.out = out;
		this.state = state;
		this.flowin = flowin;
		this.flowout = flowout;
		this.upcount = upcount;
		this.loadcount = loadcount;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public synchronized String getIP() {
		return IP;
	}
	public void setIP(String IP) {
		this.IP = IP;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public byte getLifetime() {
		return lifetime;
	}
	public void setLifetime(byte lifetime) {
		this.lifetime = lifetime;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public DataInputStream getIn() {
		return in;
	}
	public void setIn(DataInputStream in) {
		this.in = in;
	}
	public DataOutputStream getOut() {
		return out;
	}
	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public int getFlowin() {
		return flowin;
	}

	public void setFlowin(int flowin) {
//		this.preFlowin = this.flowin;
		this.flowin = flowin;
	}

	public int getFlowout() {
		return flowout;
	}

	public void setFlowout(int flowout) {
//		this.preFlowout = this.flowout; 
		this.flowout = flowout;
	}

//	public int getPreFlowin() {
//		return preFlowin;
//	}
//
//	public int getPreFlowout() {
//		return preFlowout;
//	}
	
}
