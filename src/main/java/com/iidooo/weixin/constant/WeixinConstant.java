package com.iidooo.weixin.constant;

public class WeixinConstant {

    /**
     * 微信加密签名，msg_signature结合了企业填写的token，请求中的timestamp, nonce参数，加密的消息体
     */
    public static final String KEY_MSG_SIGNATURE = "msg_signature";

    public static final String KEY_TIMESTAMP = "timestamp";

    public static final String KEY_NONCE = "nonce";

    /**
     * 加密的随机字符串，以msg_encrypt格式提供。需要解密并返回echostr铭文，解密后的random, msg_len, msg, $CorpID四个字段，其中msg即为echostr明文
     */
    public static final String KEY_ECHOSTR = "echostr";

    public static final String KEY_ERR_CODE = "errcode";

    public static final String KEY_ERR_MSG = "errmsg";

    public static final String KEY_ACCESS_TOKEN = "access_token";

    public static final String KEY_EXPIRES_IN = "expires_in";

    public static final String KEY_TICKET = "ticket";

    public static final String KEY_JSAPI_TICKET = "jsapi_ticket";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_AES_KEY = "aesKey";
    //
    // public static final String KEY_SIGNATURE = "signature";
    //
    // public static final String KEY_NONCESTR = "noncestr";
    //
    // public static final String KEY_TIMESTAMP = "timestamp";
    //
    // public static final String KEY_URL = "url";

    // APP ID(应用ID), 如果是企业号，就是CORPID
    public static final String KEY_APP_ID = "AppID";
    // 应用密钥
    public static final String KEY_APP_SECRET = "AppSecret";
    // 是否时企业号的标识，企业号的API是不一样的
    public static final String KEY_IS_CORP = "IsCorp";
    
    // 企业号API
    public static final String CORP_API_GET_ACCESS_TOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={0}&corpsecret={1}";
    
    // 公众号API
    public static final String API_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    public static final String CORP_API_GET_JSAPI_TICKET = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token={0}";
    
    public static final String API_GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";

}
