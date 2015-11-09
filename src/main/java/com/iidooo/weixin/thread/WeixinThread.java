package com.iidooo.weixin.thread;

import org.apache.log4j.Logger;

import com.iidooo.core.util.StringUtil;
import com.iidooo.weixin.entity.AccessToken;
import com.iidooo.weixin.entity.JSApiTicket;
import com.iidooo.weixin.util.WeixinUtil;

public class WeixinThread implements Runnable {
    private static final Logger logger = Logger.getLogger(WeixinThread.class);

    // 第三方用户唯一凭证
    private static String corpID;

    // 第三方用户唯一凭证密钥
    private static String secret;

    private static AccessToken accessToken;

    private static JSApiTicket jsAPITicket;

    public static String getCorpID() {
        return corpID;
    }

    public static String getSecret() {
        return secret;
    }

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static JSApiTicket getJsAPITicket() {
        return jsAPITicket;
    }

    public WeixinThread(String corpID, String secret) {
        WeixinThread.corpID = corpID;
        WeixinThread.secret = secret;
    }

    @Override
    public void run() {
        while (true) {
            try {

                accessToken = WeixinUtil.getAccessToken(corpID, secret);
                if (null != accessToken && StringUtil.isNotBlank(accessToken.getToken())) {
                    logger.info(StringUtil.replace("获取access_token成功，有效时长{0}秒 token:{1}", String.valueOf(accessToken.getExpires()), accessToken.getToken()));
                    

                    jsAPITicket = WeixinUtil.getJsAPITicket(accessToken.getToken());
                    if (null != jsAPITicket && StringUtil.isNotBlank(jsAPITicket.getTicket())){
                        logger.info(StringUtil.replace("获取JSAPI_TICKET成功，有效时长{0}秒 ticket:{1}", String.valueOf(jsAPITicket.getExpires()), jsAPITicket.getTicket()));
                    }
                    // 休眠7000秒
                    Thread.sleep((accessToken.getExpires() - 200) * 1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    logger.fatal(e1);
                }
                e.printStackTrace();
                logger.fatal(e);
            }
        }

    }
}
