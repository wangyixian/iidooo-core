package com.iidooo.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * The security utility class
 *
 * @author wangyixian
 *
 */
public class SecurityUtil {

    private static final Logger logger = Logger.getLogger(SecurityUtil.class);
    
    /**
     * Make the plain text by MD5 security.
     *
     * @param plainText 
     * @return MD5 Text
     */
    public static String getMd5(final String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append(0);
                }
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }
}
