package com.netconnection.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ValidateCode {
	public static final String RANDOMCODEKEY = "VALIDATECODEKEY";// �ŵ�session�е�key
	private Random random = new Random();
	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// ����������ַ���
	private int width = 80;// ͼƬ��
	private int height = 28;// ͼƬ��
	private int lineSize = 350;// ����������
	private int stringNum = 4;// ��������ַ�����
	private String validateCode;
	int x=0;
	/*
	 * �������
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 23);
	}
	
	

	/*
	 * �����ɫ
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * �������ͼƬ
	 */
	public ByteArrayInputStream getRandcode() {
		// BufferedImage���Ǿ��л�������Image��,Image������������ͼ����Ϣ����
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// ����Image�����Graphics����,�Ķ��������ͼ���Ͻ��и��ֻ��Ʋ���
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 23));
		g.setColor(getRandColor(110, 133));
		// ���Ƹ�����
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		validateCode="";
		// ��������ַ�
		for (int i = 1; i <= stringNum; i++) {
			validateCode = drowString(g, validateCode, i);
		}

		System.out.println("v1:"+validateCode);
		g.dispose();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	try {
		        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
		        ImageIO.write(image, "JPEG", imageOut);
		        imageOut.close();
		        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
		        output.close();
		        System.out.println("ͼƬ�����ֽ����ɹ�������");
		        return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return new ByteArrayInputStream(output.toByteArray());
		
		
	}

	/*
	 * �����ַ���
	 */
	private String drowString(Graphics g, String randomString, int i) {
		
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
				.nextInt(121)));
		
		g.setFont(getFont());
		
		String rand = String.valueOf(getRandomString(random.nextInt(randString
				.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return randomString;
	}

	/*
	 * ���Ƹ�����
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.setColor( new Color(0, 191, 255));
		g.drawLine(x, y, x + xl, y + yl);
//		g.draw3DRect(x, y, x + xl, y + yl, true);
	}

	/*
	 * ��ȡ������ַ�
	 */
	private String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}



	public String getValidateCode() {
		return validateCode;
	}



	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
	

}
