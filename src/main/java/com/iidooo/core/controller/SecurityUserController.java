package com.iidooo.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iidooo.core.constant.MessageConstant;
import com.iidooo.core.constant.ServletConstant;
import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.model.ResponseResult;
import com.iidooo.core.model.po.SecurityAccessToken;
import com.iidooo.core.model.po.SecurityResource;
import com.iidooo.core.model.po.SecurityRole;
import com.iidooo.core.model.po.SecurityUser;
import com.iidooo.core.service.SecurityUserService;
import com.iidooo.core.util.MailUtil;
import com.iidooo.core.util.StringUtil;

@Controller
public class SecurityUserController {
    private static final Logger logger = Logger.getLogger(SecurityUserController.class);

    // key: email
    // value: verfy code
    Map<String, String> verifyCodeMap = new HashMap<>();

    private long FIVE_MINUTE = 5 * 60 * 1000;

    @Autowired
    private SecurityUserService securityUserService;

    @ResponseBody
    @RequestMapping(value = "/core/getAccessToken", method = RequestMethod.POST)
    public ResponseResult getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String loginID = request.getParameter("loginID");
            String password = request.getParameter("password");

            result.checkFieldRequired("loginID", loginID);
            result.checkFieldRequired("password", password);
            if (result.getMessages().size() > 0) {
                result.setStatus(ResponseStatus.ValidateFailed.getCode());
                return result;
            }

            SecurityAccessToken accessToken = this.securityUserService.getAccessTokenByLogin(loginID, password);
            if (accessToken == null) {
                result.checkQueryEmpty(MessageConstant.QUERY_EMPTY_WRONG_LOGIN);
            } else {
                result.setStatus(ResponseStatus.OK.getCode());
                result.setData(accessToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/core/getAccessTokenByMail", method = RequestMethod.POST)
    public ResponseResult getAccessTokenByMail(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String email = request.getParameter("email");
            String verifyCode = request.getParameter("verifyCode");

            if (result.checkFieldRequired("email", email) && result.checkFieldRequired("verifyCode", verifyCode)) {
                result.checkVerifyCode(verifyCodeMap, email, verifyCode);
            }
            if (result.getMessages().size() > 0) {
                result.setStatus(ResponseStatus.ValidateFailed.getCode());
                return result;
            }

            verifyCodeMap.remove(email);

            SecurityAccessToken accessToken = this.securityUserService.getAccessTokenByEmail(email);
            if (accessToken == null) {
                result.checkQueryEmpty(MessageConstant.QUERY_EMPTY_WRONG_LOGIN);
            } else {
                result.setStatus(ResponseStatus.OK.getCode());
                result.setData(accessToken);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/core/getUserByToken", method = RequestMethod.POST)
    public ResponseResult getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String accessToken = request.getParameter("accessToken");

            result.checkFieldRequired("accessToken", accessToken);
            if (result.getMessages().size() > 0) {
                result.setStatus(ResponseStatus.ValidateFailed.getCode());
                return result;
            }

            SecurityUser securityUser = this.securityUserService.getSecurityUserByToken(accessToken);
            if (securityUser == null) {
                result.checkQueryEmpty(MessageConstant.QUERY_EMPTY_WRONG_TOKEN);
            } else {
                ServletContext sc = request.getServletContext();
                // 这个用户的可访问资源获取
                Object roleResourceMapObj = sc.getAttribute(ServletConstant.ROLE_RESOURCE_MAP);
                if (roleResourceMapObj != null) {
                    @SuppressWarnings("unchecked")
                    Map<Integer, List<SecurityResource>> roleResourceMap = (Map<Integer, List<SecurityResource>>) roleResourceMapObj;
                    for (SecurityRole item : securityUser.getRoleList()) {
                        List<SecurityResource> resourceList = roleResourceMap.get(item.getRoleID());
                        for (SecurityResource securityResource : resourceList) {
                            securityUser.getResUrlList().add(securityResource.getResURL());
                        }
                    }
                }

                result.setStatus(ResponseStatus.OK.getCode());
                result.setData(securityUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/core/sendVerifyCode", method = RequestMethod.POST)
    public ResponseResult sendVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        try {
            String email = request.getParameter("email");

            result.checkFieldRequired("email", email);
            result.checkFieldEmail("email", email);
            if (result.getMessages().size() > 0) {
                result.setStatus(ResponseStatus.ValidateFailed.getCode());
                return result;
            }

            String verifyCode = StringUtil.getRandomNumber(4, 9);
            this.verifyCodeMap.put(email, verifyCode);

            ServletContext sc = request.getServletContext();
            Properties mailProperties = (Properties) sc.getAttribute("mail.properties");

            String content = "亲爱的毒电波用户，" + verifyCode + " 是您的验证码，5分钟内有效。";
            if (!MailUtil.sendMail(mailProperties, content, email)) {
                result.setStatus(ResponseStatus.Failed.getCode());
            } else {
                result.setStatus(ResponseStatus.OK.getCode());
                verifyCodeTimeTask(email);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            result.checkException(e);
        }
        return result;
    }

    private void verifyCodeTimeTask(final String account) {

        new Thread() {
            public void run() {

                TimerTask task = new TimerTask() {
                    public void run() {
                        verifyCodeMap.remove(account);
                    }
                };

                Timer timer = new Timer(false);
                timer.schedule(task, FIVE_MINUTE);
                /*
                 * try { Thread.sleep(FIVE_MINUTE + 1000); } catch (InterruptedException e) { logger.info("验证码计时异常",e); }
                 */
            }
        }.start();
    }
}
