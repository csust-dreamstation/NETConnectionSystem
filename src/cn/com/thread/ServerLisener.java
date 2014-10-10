package cn.com.thread;

import cn.com.server.Server;

public class ServerLisener extends Thread {
        private Thread t;
        public ServerLisener(Thread t){
        	   this.t=t;
       }
        public void run() {
        	while(true){
        		if(!t.isAlive()){
        			System.out.println("服务器已死，有事烧纸");
        			break;
        		}
        		else{
        			try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        			System.out.println("服务器线程仍然存活");
        		}
        	}
        }
}
