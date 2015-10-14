package com.iidooo.core.service.security.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dao.extend.SecurityUserDao;
import com.iidooo.core.dto.PageDto;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.security.UserListService;
import com.iidooo.core.util.DateUtil;
import com.opensymphony.xwork2.ActionContext;
@Service
public class UserListServiceImpl implements UserListService {

    private static final Logger logger = Logger.getLogger(UserListServiceImpl.class);

    @Autowired
    private SecurityUserDao userDao;

    @Override
    public SecurityUserDto getUser(int userID) {
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
    public int getUserListCount() {
        int result = 0;
        try {
            result = userDao.selectAllCount();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    @Override
    public List<SecurityUserDto> getUserList(PageDto page) {
        List<SecurityUserDto> result = new ArrayList<SecurityUserDto>();
        try {
            result = userDao.selectAll(page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    @Override
    public boolean deleteUser(SecurityUserDto user) {
        try {

            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            SecurityUserDto loginUser = (SecurityUserDto) sessionMap.get(SessionConstant.LOGIN_USER);
            user.setUpdateUser(loginUser.getUserID());
            user.setUpdateTime(DateUtil.getNow(DateUtil.FORMAT_DATETIME));
            int count = userDao.delete(user);
            if (count <= 0) {
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
