package com.iidooo.core.service.security.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.iidooo.core.constant.DateTimeFormat;
import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dao.extend.SecurityUserDao;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.security.UserinfoService;
import com.iidooo.core.util.DateUtil;
import com.opensymphony.xwork2.ActionContext;

public class UserinfoServiceImpl implements UserinfoService {

    private static final Logger logger = Logger.getLogger(UserinfoServiceImpl.class);

    private SecurityUserDao securityUserDao;

    public SecurityUserDto getUser(String loginID) {
        try {
            SecurityUserDto result = securityUserDao.selectByLoginID(loginID);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    public boolean saveUser(SecurityUserDto user) {
        try {
            // Set SecurityUser key of loginID and userID from the session SecurityUser.
            // Because this way is safety.
            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            SecurityUserDto sessionUser = (SecurityUserDto) sessionMap.get(SessionConstant.LOGIN_USER);
            user.setLoginID(sessionUser.getLoginID());
            user.setUserID(sessionUser.getUserID());

            user.setUpdateUser(user.getUserID());
            user.setUpdateTime(DateUtil.getNow(DateTimeFormat.DATE_TIME_HYPHEN));

            int result = securityUserDao.updateByPrimaryKey(user);
            if (result <= 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }
}
