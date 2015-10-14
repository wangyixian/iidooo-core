package com.iidooo.core.service.security.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dao.extend.SecurityUserDao;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.security.UserDetailService;
import com.iidooo.core.util.DateUtil;
import com.opensymphony.xwork2.ActionContext;
@Service
public class UserDetailServiceImpl implements UserDetailService {

    private static final Logger logger = Logger.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private SecurityUserDao userDao;

    @Override
    public SecurityUserDto getUserByID(int userID) {
        try {
            SecurityUserDto result = userDao.selectByPrimaryKey(userID);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    @Override
    public boolean isLoginIDDuplicate( String loginID) {
        try {

            SecurityUserDto user = userDao.selectByLoginID(loginID);
            if (user == null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }

    @Override
    public boolean createUser(SecurityUserDto user) {
        try {

            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            SecurityUserDto loginUser = (SecurityUserDto) sessionMap.get(SessionConstant.LOGIN_USER);
            user.setCreateUser(loginUser.getUserID());
            user.setCreateTime(DateUtil.getNow(DateUtil.FORMAT_DATETIME));
            user.setUpdateUser(loginUser.getUserID());
            user.setUpdateTime(DateUtil.getNow(DateUtil.FORMAT_DATETIME));

            int result = userDao.insert(user);
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

    @Override
    public boolean updateUser(SecurityUserDto user) {
        try {
            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            SecurityUserDto loginUser = (SecurityUserDto) sessionMap.get(SessionConstant.LOGIN_USER);
            user.setUpdateUser(loginUser.getUserID());
            user.setUpdateTime(DateUtil.getNow(DateUtil.FORMAT_DATETIME));

            int result = userDao.update(user);
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
