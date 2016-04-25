package com.iidooo.core.service;

import java.util.Properties;

import com.iidooo.core.model.po.SecurityAccessToken;
import com.iidooo.core.model.po.SecurityUser;

public interface SecurityUserService {
    
    SecurityAccessToken getAccessTokenByLogin(String loginID, String password, String userType) throws Exception;
    
    SecurityUser getSecurityUserByID(Integer userID);
    
    SecurityUser getSecurityUserByEmail(String email);
    
    SecurityUser getSecurityUserByToken(String token);

    SecurityUser createDefaultUser(String photoURL, String email, Properties properties);

    SecurityUser updateUserInfo(SecurityUser securityUser);

    SecurityUser updateUserExp(Integer userID, Integer experience, Integer isLimited) throws Exception ;
}
