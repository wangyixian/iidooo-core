/**
 * Copyright 2014-2015 IIDOOO All rights reserved.
 * Author(e-mail)    wangyixian@iidooo.com
 * Creation date     2015-03-27
 */
package com.iidooo.core.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.iidooo.core.action.common.BaseAPIAction;
import com.iidooo.core.action.common.BaseAction;
import com.iidooo.core.action.security.IdentifyCodeAction;
import com.iidooo.core.action.security.LoginAction;
import com.iidooo.core.constant.SessionConstant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * The interceptor of doing the authorization
 * @author Ethan
 *
 */
public class AuthInterceptor extends AbstractInterceptor {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

    @Override
    public final String intercept(final ActionInvocation invocation) throws Exception {
        try {
            Object actionclass = invocation.getAction();

            // The api action did not need this auth logic.
            if (actionclass instanceof BaseAPIAction) {
                return invocation.invoke();
            }

            // If the action class is LoginAction, need not check the session, just return.
            if (actionclass instanceof LoginAction) {
                return invocation.invoke();
            }

            if (actionclass instanceof IdentifyCodeAction) {
                return invocation.invoke();
            }
            // The session did not contain the LOGIN_USER, forward to the the login page.
            Map<String, Object> session = ActionContext.getContext().getSession();
            if (session == null || !session.containsKey(SessionConstant.LOGIN_USER)) {
                return ActionSupport.LOGIN;
            }

            @SuppressWarnings("unchecked")
            List<String> resourceURLList = (List<String>) session.get(SessionConstant.LOGIN_RESOURCE_URL_LIST);
            if (resourceURLList == null || resourceURLList.size() <= 0) {
                return BaseAction.RESULT_SECURITY;
            }

            // Get the servlet path like index.action
            HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
            String path = request.getServletPath();

            if (path.startsWith("/")) {
                path = path.substring(1);
            }

            if (!resourceURLList.contains(path)) {
                return BaseAction.RESULT_SECURITY;
            }

            return invocation.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ActionSupport.ERROR;
        }

    }
}
