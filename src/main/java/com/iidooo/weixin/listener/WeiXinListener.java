package com.iidooo.weixin.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.iidooo.core.util.StringUtil;
import com.iidooo.weixin.constant.WeixinConstant;
import com.iidooo.weixin.thread.WeixinThread;

public class WeiXinListener extends HttpServlet implements ServletContextListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(WeiXinListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ServletContext sc = arg0.getServletContext();
            String appID = sc.getInitParameter(WeixinConstant.KEY_APP_ID);
            String appSecrect = sc.getInitParameter(WeixinConstant.KEY_APP_SECRET);
            String isCorp = sc.getInitParameter(WeixinConstant.KEY_IS_CORP);
            
            // 未配置corpID、secret时给出提示
            if (StringUtil.isBlank(appID) || StringUtil.isBlank(appSecrect)) {
                logger.error("corpID and secret configuration error, please check carefully.");
            } else {
                // 启动定时获取access_token的线程
                Runnable thread = new WeixinThread(appID, appSecrect, isCorp);
                new Thread(thread).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
