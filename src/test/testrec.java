package test;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class testrec implements Runnable {
    private DataInputStream in;
	public testrec(DataInputStream in) {
		this.in = in;
	}

	public void run() {
		while(true){
			byte[] b=new byte[1024];
			try {
				int len=in.read(b);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println(new String(b,"GBK").trim());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
