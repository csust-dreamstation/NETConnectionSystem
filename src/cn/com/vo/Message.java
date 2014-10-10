package cn.com.vo;

public class Message {
	public final static int LOGOUT=0;
	public final static int WLC=1;
	public final static int WLCC=2;
	public final static int BLC=3;
	public final static int BLCC=4;
	public final static int OPC=5;
	public final static int MSG2=6;
	public final static int SHUTDOWN=7;
	public final static int COPYSCREEN=8;
	public final static int ILLAC=9;
	public final static int ILLACC=10;
	public final static int TIMESYN=11;
	public final static int TYPE_WIFICHK=12;
	  /*
	   * 0:断网报文
	   * 1:白名单检测
	   * 2:取消百名单检测
	   * 3:黑名单检测
	   * 4:取消黑名单检测
	   * 5:OS补丁检测
	   * 6:给客户端发送信息
	   * 7:客户端关机报文
	   * 8:截屏命令
	   */
	
	  
      private String mac;//mac地址ַ
      private int type;  //消息类型
      private String message;//消息内容
	
      
    
    public Message() {

	}
	public Message(String mac, int type, String message) {
		this.mac = mac;
		this.type = type;
		this.message = message;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
      
}
