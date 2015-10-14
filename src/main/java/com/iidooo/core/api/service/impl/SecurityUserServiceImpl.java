package com.iidooo.core.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.core.api.service.SecurityUserService;
import com.iidooo.core.dao.extend.SecurityUserDao;
import com.iidooo.core.dto.extend.SecurityUserDto;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    private static final Logger logger = Logger.getLogger(SecurityUserServiceImpl.class);
    
    @Autowired
    private SecurityUserDao securityUserDao;
    
    @Override
    public SecurityUserDto getSecurityUser(Integer userID) {
        try {
            return securityUserDao.selectByPrimaryKey(userID);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

}
