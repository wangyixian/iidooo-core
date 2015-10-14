package com.iidooo.core.service.security.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.dao.extend.SecurityUserDao;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.security.PasswordService;
import com.iidooo.core.util.DateUtil;
import com.iidooo.core.util.SecurityUtil;

@Service
public class PasswordServiceImpl implements PasswordService {
    private static final Logger logger = Logger.getLogger(PasswordServiceImpl.class);

    @Autowired
    private SecurityUserDao securityUserDao;
    
    @Override
    public boolean checkOldPassword(String loginID, String oldPassword) {
        try {
            oldPassword = SecurityUtil.getMd5(oldPassword);
            SecurityUserDto user = securityUserDao.selectForLogin(loginID, oldPassword);
            if (user == null) {
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
    public boolean saveNewPassword(SecurityUserDto user, String newPassword) {
        try {
            user.setUpdateUser(user.getUserID());
            user.setPassword(SecurityUtil.getMd5(newPassword));
            user.setUpdateTime(DateUtil.getNow(DateUtil.FORMAT_DATETIME));
            securityUserDao.updatePassword(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }
}
