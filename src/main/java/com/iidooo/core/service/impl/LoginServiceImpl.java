package com.iidooo.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.dao.extend.SecurityRoleDao;
import com.iidooo.core.dao.extend.SecurityUserDao;
import com.iidooo.core.dto.extend.SecurityRoleDto;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.service.LoginService;
import com.iidooo.core.util.DateUtil;
import com.iidooo.core.util.SecurityUtil;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Autowired
    private SecurityUserDao userDao;

    @Autowired
    private SecurityRoleDao roleDao;

    @Override
    public SecurityUserDto login(String loginID, String password) {
        try {
            password = SecurityUtil.getMd5(password);
            SecurityUserDto result = userDao.selectForLogin(loginID, password);

            if (result != null) {
                result.setLoginTime(DateUtil.getNow(DateUtil.FORMAT_DATETIME));
                userDao.updateLoginTime(result);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    @Override
    public List<SecurityRoleDto> getUserRoleList(int userID) {
        List<SecurityRoleDto> result = new ArrayList<SecurityRoleDto>();
        try {
            SecurityUserDto securityUser = new SecurityUserDto();
            securityUser.setUserID(userID);
            result = roleDao.selectSecurityRoleList(securityUser);
        } catch (Exception e) {
            logger.fatal(e);
            e.printStackTrace();
        }

        return result;
    }

}
