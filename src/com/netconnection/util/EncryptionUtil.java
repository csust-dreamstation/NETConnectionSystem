package com.netconnection.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 该类提供一些加密的方法
 * @author Administrator
 *
 */
public class EncryptionUtil {
	public static String eccryptByMD5(String info) throws NoSuchAlgorithmException{  
        //根据MD5算法生成MessageDigest对象  
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        byte[] srcBytes = info.getBytes();  
        //使用srcBytes更新摘要  
        md5.update(srcBytes);  
        //完成哈希计算，得到result  
        byte[] resultBytes = md5.digest();  
        return new String(resultBytes);  
    }
}
