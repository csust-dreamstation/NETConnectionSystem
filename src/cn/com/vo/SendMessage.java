package cn.com.vo;
public class SendMessage {
     private Message message;
     private int time;
     private int timeout;
     private int outtime;
     private int count;//计数器
	public SendMessage(Message message, int time, int timeout, int outtime,int count) {
		this.message = message;
		this.time = time;
		this.timeout = timeout;
		this.outtime = outtime;
		this.count=count;
	}
	public SendMessage() {

	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getOuttime() {
		return outtime;
	}
	public void setOuttime(int outtime) {
		this.outtime = outtime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}    
}
