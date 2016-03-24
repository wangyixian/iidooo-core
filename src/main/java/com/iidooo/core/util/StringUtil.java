package com.iidooo.core.util;

import java.util.Random;

import org.apache.log4j.Logger;

public class StringUtil {

    private static final Logger logger = Logger.getLogger(StringUtil.class);

    /**
     * 字符串为空判断.
     *
     * @param str check对象字符串
     * @return boolean true:null 空字符串、false:非空字符串
     */
    public static boolean isNotBlank(final String str) {
        try {
            boolean result = false;
            if (str != null && str.length() > 0) {
                result = true;
            }
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            return false;
        }
    }
    
    public static boolean isBlank(final String str){
        try {
            boolean result = false;
            if (str == null || str.length() <= 0) {
                result = true;
            }
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            return true;
        }
    }

    /**
     * Replace the old string by the array of new strings. The old string's {1}, {2}... are the replace object.
     * 
     * @param old This old string should be replaced.
     * @param news These new strings will replace the old string's {1}, {2}
     * @return The result of the replace string.
     */
    public static String replace(String old, String... news) {
        try {
            String result = old;
            for (int i = 0; i < news.length; i++) {
                if (news[i] == null) {
                    continue;
                }
                result = result.replace("{" + i + "}", news[i]);
            }
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            return old;
        }
    }

    public static String substring(String str, int start, int end) {
        try {
            if (str == null || str.isEmpty()) {
                return "";
            }

            String result = str;
            if (str.length() < (start + end)) {
                logger.warn("The input string is not long enough!");
                return str;
            }

            str.substring(start, end);
            return result;
        } catch (Exception e) {
            logger.fatal(e);
            return str;
        }
    }
    
    public static String getRandomStr(int length){
        try {
            int A = 'A';
            int Z = 'Z';
            
            Random rand = new Random();
            StringBuilder sb = new StringBuilder();
            while (sb.length() < length) {
                int number = rand.nextInt(Z + 1);
                if (number >= A) {
                    sb.append((char)number);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            logger.fatal(e);
            return "";
        }
    }
}
