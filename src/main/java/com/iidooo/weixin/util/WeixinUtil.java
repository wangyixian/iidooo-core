package com.iidooo.weixin.util;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.iidooo.core.util.HttpUtil;
import com.iidooo.core.util.StringUtil;
import com.iidooo.weixin.constant.WeixinConstant;
import com.iidooo.weixin.entity.AccessToken;
import com.iidooo.weixin.entity.JSApiTicket;
import com.iidooo.weixin.entity.Signature;

public class WeixinUtil {
    private static final Logger logger = Logger.getLogger(WeixinUtil.class);

    public static AccessToken getAccessToken(String corpID, String secret) {
        try {
            AccessToken result = new AccessToken();
            // 获取access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）
            String url = StringUtil.replace(WeixinConstant.API_GET_ACCESS_TOKEN, corpID, secret);
            String response = HttpUtil.doGet(url);

            JSONObject jsonObject = JSONObject.fromObject(response);
            result.setToken(jsonObject.getString(WeixinConstant.KEY_ACCESS_TOKEN));
            result.setExpires(jsonObject.getInt(WeixinConstant.KEY_EXPIRES_IN));

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public static JSApiTicket getJsAPITicket(String accessToken) {
        try {
            JSApiTicket result = new JSApiTicket();
            // 用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存
            String url = StringUtil.replace(WeixinConstant.API_GET_JSAPI_TICKET, accessToken);
            String response = HttpUtil.doGet(url);

            JSONObject jsonObject = JSONObject.fromObject(response);
            result.setTicket(jsonObject.getString(WeixinConstant.KEY_TICKET));
            result.setErrCode(jsonObject.getInt(WeixinConstant.KEY_ERR_CODE));
            result.setErrMsg(jsonObject.getString(WeixinConstant.KEY_ERR_MSG));
            result.setExpires(jsonObject.getInt(WeixinConstant.KEY_EXPIRES_IN));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public static Signature getSignature(String jsapi_ticket, String url) {
        try {

            Signature result = new Signature();

            String nonce_str = UUID.randomUUID().toString();
            long timestamp = System.currentTimeMillis() / 1000;
            String string1;
            String signature = "";

            // 注意这里参数名必须全部小写，且必须有序
            string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + String.valueOf(timestamp) + "&url=" + url;
            // System.out.println(string1);

            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());

            result.setUrl(url);
            result.setJsAPITicket(jsapi_ticket);
            result.setNoncestr(nonce_str);
            result.setTimestamp(timestamp);
            result.setSignature(signature);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public static String getUserInfo(String accessToken, String code) {

        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={0}&code={1}&agentid=2";
        url = url.replace("{0}", accessToken);
        url = url.replace("{1}", code);

        try {
            String response = HttpUtil.doGet(url);

            JSONObject jsonObject = JSONObject.fromObject(response);
            String userInfo = (String) jsonObject.get("OpenId");
            if (StringUtil.isBlank(userInfo)) {
                userInfo = (String) jsonObject.get("UserId");
            } 

            return userInfo;
        } catch (Exception e) {
            return "";
        }
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
