package com.iidooo.core.constant;

public class WeChatConstant {

    /**
     * 微信加密签名，msg_signature结合了企业填写的token，请求中的timestamp, nonce参数，加密的消息体
     */
    public static final String MSG_SIGNATURE = "msg_signature";

    /**
     * 加密的随机字符串，以msg_encrypt格式提供。需要解密并返回echostr铭文，解密后的random, msg_len, msg,
     * $CorpID四个字段，其中msg即为echostr明文
     */
    public static final String ECHOSTR = "echostr";
    
    public static final String TOKEN = "6zanHbLP94rXAsq1";
    
    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String KEY_TICKET = "ticket";
    
    public static final String KEY_JSAPI_TICKET = "jsapi_ticket";
    
    public static final String KEY_SIGNATURE = "signature";
    
    public static final String KEY_NONCESTR = "noncestr";
    
    public static final String KEY_TIMESTAMP = "timestamp";
    
    public static final String KEY_URL = "url";
    
    public static final String ENCODING_AES_KEY = "WBjpwpGxL7OKlygLbErnnllgg52RVxiQXrfjj52jnvc";
    
    public static final String CORPID = "wx837891e149859457";
    
    public static final String SECRET = "9d3fJfPmBMfXM6lfOdWAIu09jBhvSGHXGpXJi6ez-ApYNrUA2zN12RpixZiU5_l9";
    
    public static final String API_GET_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";

    public static final String API_GET_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token={0}";

}
