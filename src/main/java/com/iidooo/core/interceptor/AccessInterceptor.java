package com.iidooo.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iidooo.core.mapper.SecurityClientMapper;
import com.iidooo.core.util.StringUtil;

public class AccessInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(AccessInterceptor.class);

    @Autowired
    private SecurityClientMapper securityClientMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String appID = request.getHeader("appID");
            String secret = request.getHeader("secret");
            String referer = request.getHeader("referer");
            logger.warn("referer is " + referer);
            if (StringUtil.isBlank(appID) || StringUtil.isBlank(secret)) {
                appID = request.getParameter("appID");
                secret = request.getParameter("secret");
            }

            if (StringUtil.isBlank(appID) || StringUtil.isBlank(secret)) {
                logger.warn("Access Denied!");
                return false;
            }

            if (securityClientMapper.selectByAppIDSecret(appID, secret) == null) {
                logger.warn("Access Denied!");
                return false;
            }

            // 临时代码，拦截爬虫和安卓机
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.toLowerCase().contains("scrapy")) {
                logger.warn("Access Denied because of " + userAgent);
                return false;
            }
            logger.info("Access OK!");

        } catch (Exception e) {
            logger.fatal(e);
        }
        return super.preHandle(request, response, handler);
    }

}
