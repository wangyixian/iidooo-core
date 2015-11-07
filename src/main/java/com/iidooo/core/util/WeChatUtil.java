package com.iidooo.core.util;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    public Map<String, String> getSignature(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        try {

            String nonce_str = UUID.randomUUID().toString();
            String timestamp = Long.toString(System.currentTimeMillis() / 1000);
            String string1;
            String signature = "";

            // 注意这里参数名必须全部小写，且必须有序
            string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
            System.out.println(string1);

            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());

            ret.put(WeChatConstant.KEY_URL, url);
            ret.put(WeChatConstant.KEY_JSAPI_TICKET, jsapi_ticket);
            ret.put(WeChatConstant.KEY_NONCESTR, nonce_str);
            ret.put(WeChatConstant.KEY_TIMESTAMP, timestamp);
            ret.put(WeChatConstant.KEY_SIGNATURE, signature);

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
