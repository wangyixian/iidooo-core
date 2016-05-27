package com.iidooo.core.service;

import java.util.Properties;

import com.iidooo.core.model.po.SecurityAccessToken;
import com.iidooo.core.model.po.SecurityUser;

public interface SecurityUserService {
    
    SecurityAccessToken getAccessTokenByLogin(String loginID, String password);
    
    /**
     * 通过邮件地址获取AccessToken
     * @param email 邮箱地址
     * @return 所获得AccessToken
     */
    SecurityAccessToken getAccessTokenByEmail(String email);
    
    SecurityUser getSecurityUserByID(Integer userID);
    
    SecurityUser getSecurityUserByEmail(String email);
    
    SecurityUser getSecurityUserByToken(String token);

    SecurityUser createDefaultUser(String photoURL, String email, Properties properties);

    SecurityUser updateUserInfo(SecurityUser securityUser);

    SecurityUser updateUserExp(Integer userID, Integer experience, Integer isLimited) throws Exception ;
}
