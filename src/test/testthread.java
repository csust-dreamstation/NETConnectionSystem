package test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;


public class testthread extends Thread{
	DataOutputStream out;
	private Random r;
	private int ip;
	private String username;
	private String firstname[]={"赵","钱","孙","李","周", "陈"," 肖" ,"张", "戴", "欧阳", "孔", "孟", "王", "田", "玉", "黄"};
	private String lastname[]={"翼","一","二","三","四","五","六","七","八","九","十","立波","笑笑","弯弯","琪琪","狒狒","菲菲","慧慧","惠惠","灰灰","辉辉","王波"};
	private String[] osr={"windows7","windowsxp","windows2000","windows8","linux"};
	private String os;
	private int[] macr;
	private static String[] st1={"#START#WLCR|RavMonD.exe*RESULT|vrvsafec.exe*RESULT#END#","#START#BLCR|hypmain.exe*RESULT|QQ.exe*RESULT#END#"
		,"#START#OPCR|KB2510531-IE8*RESULT|KB2079403*RESULT|KB2360937*RESULT#END#","#START#MSG1|我来了#END#"};
	public testthread(DataOutputStream out){
		macr=new int[4];
		r=new Random();
		macr[0]=r.nextInt(10);
		macr[1]=r.nextInt(10);
		macr[2]=r.nextInt(10);
		macr[3]=r.nextInt(10);
	    username=firstname[r.nextInt(firstname.length)]+lastname[r.nextInt(lastname.length)];
		ip=r.nextInt(256);
		os=osr[r.nextInt(osr.length)];
		this.out=out;
	}
    public void run() {
    	
    	String str="#START#HB|192.168.0.0."+ip+"|"+macr[0]+r.nextInt(10)+"-"+macr[1]+r.nextInt(10)+"-"+macr[2]+r.nextInt(10)+"-"+macr[3]+r.nextInt(10)+"|"+username+"|"+r.nextInt(25600)+"|"+r.nextInt(25600)+"|"+"0.65"+"|"+"768/1024=66%"+"|"+os+"#END#";
    	while(true){
    	try {
			out.write(str.getBytes("GBK"));
			this.sleep(50);
			out.write(st1[r.nextInt(st1.length)].getBytes("GBK"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(str);
    	try {
			this.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	}
    }
}
