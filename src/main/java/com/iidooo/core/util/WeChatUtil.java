package com.iidooo.core.util;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;


import com.iidooo.core.constant.WeChatConstant;

public class WeChatUtil {
    private static final Logger logger = Logger.getLogger(WeChatUtil.class);

    private static WeChatUtil instance;

    private String accessToken;

    private String jsAPITicket;

    public String getAccessToken() {
        return accessToken;
    }

    public String getJsAPITicket() {
        return jsAPITicket;
    }

    public static WeChatUtil getInstance() {
        if (instance == null) {
            instance = new WeChatUtil();
        }
        return instance;
    }

    private WeChatUtil() {
        this.initialize();
    }

    public String getSignature(String noncestr, String jsapi_ticket, String timestamp, String url) {
        try {
            String signature = "";
            
            StringBuilder sb = new StringBuilder();
            sb.append("jsapi_ticket=");
            sb.append(jsapi_ticket);
            
            //signature = SHA1(sb.toString());
            
            return signature;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return "";
        }
    }

    private void initialize() {
        try {
            setAccessToken();
            setJsAPITicket();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void setAccessToken() {
        try {
            // 获取access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）
            String url = StringUtil.replace(WeChatConstant.API_GET_ACCESS_TOKEN, WeChatConstant.CORPID, WeChatConstant.SECRET);
            String response = HttpUtil.doGet(url);

            JSONObject jsonObject = JSONObject.fromObject(response);
            this.accessToken = jsonObject.getString(WeChatConstant.KEY_ACCESS_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    private void setJsAPITicket() {
        try {
            // 用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存
            String url = StringUtil.replace(WeChatConstant.API_GET_JSAPI_TICKET, this.accessToken);
            String response = HttpUtil.doGet(url);

            JSONObject jsonObject = JSONObject.fromObject(response);
            this.jsAPITicket = jsonObject.getString(WeChatConstant.KEY_TICKET);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
