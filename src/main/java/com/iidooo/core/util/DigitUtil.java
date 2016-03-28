package com.iidooo.core.util;

import java.util.Random;

public class DigitUtil {
	
	/**
	 * 获取count个随机数
	 * 
	 * @param count 1~10
	 *        随机数个数
	 * @return count bit random digit
	 */

	public static String generateRandom(int count) {
		
		StringBuffer sb = new StringBuffer();
		String str = "0123456789";
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			int num = r.nextInt(str.length());
			sb.append(str.charAt(num));
			str = str.replace((str.charAt(num) + ""), "");
		}
		return sb.toString();
	}
	
	public static int generateRandom()
	{
		int max=99999999;
		int min=10000;
		
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}	
}
