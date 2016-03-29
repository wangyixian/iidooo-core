package com.iidooo.core.constant;

public class RegularConstant {
    
    /**
     * 数字组合的字符串正则表达式.
     */
    public static final String REGEX_NUMBER = "^[0-9]*$";
    
    // The regular expression of email
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    // The regular expression of half English
    public static final String REGEX_ENGLISH = "^[a-zA-Z]*";
    // The regular expression of half English and number
    public static final String REGEX_ENGLISH_NUMBER = "^[A-Za-z0-9]+$";
}
