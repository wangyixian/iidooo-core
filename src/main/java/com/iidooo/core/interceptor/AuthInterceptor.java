package com.iidooo.core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.mapper.SecurityAccessTokenMapper;
import com.iidooo.core.model.ResponseResult;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

    @Autowired
    private SecurityAccessTokenMapper accessTokenMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            ResponseResult result = new ResponseResult();

            String userID = request.getParameter("userID");
            String accessToken = request.getParameter("accessToken");

            result.checkFieldRequired("userID", userID);
            result.checkFieldInteger("userID", userID);
            result.checkFieldRequired("accessToken", accessToken);

            if (result.getMessages().size() > 0) {
                result.setStatus(ResponseStatus.AuthFailed.getCode());
                response.reset();
                out = response.getWriter();
                JSONObject jsonObject = JSONObject.fromObject(result);
                out.append(jsonObject.toString());
                return false;
            }

            if (accessTokenMapper.selectByTokenUserID(accessToken, Integer.parseInt(userID)) == null) {
                result.setStatus(ResponseStatus.AuthFailed.getCode());
                response.reset();
                out = response.getWriter();
                JSONObject jsonObject = JSONObject.fromObject(result);
                out.append(jsonObject.toString());
                return false;
            }

        } catch (Exception e) {
            logger.fatal(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return super.preHandle(request, response, handler);
    }
}
