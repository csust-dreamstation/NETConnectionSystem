package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
public class test {
	Socket s;
	public test() {
		try {
			s=new Socket("127.0.0.1",22222);
			DataOutputStream out=new DataOutputStream(s.getOutputStream());
			new testthread(out).start();
			DataInputStream in=new DataInputStream(s.getInputStream());
			new Thread(new testrec(in)).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

         for(int i=0;i<1;i++){
        	 new test();
         }
	}
}
