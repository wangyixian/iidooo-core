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
    
    /**
     * 通过邮件地址登陆获取AccessToken
     * @param email 邮箱地址
     * @param password 密码
     * @return 所获的的AccessToken
     */
    SecurityAccessToken getAccessTokenByEmail(String email, String password);
    
    SecurityUser getSecurityUserByID(Integer userID);
    
    SecurityUser getSecurityUserByEmail(String email);
    
    SecurityUser getSecurityUserByToken(String token);

    SecurityUser createDefaultUser(String photoURL, String email, Properties properties);

    SecurityUser updateUserInfo(SecurityUser securityUser);

    SecurityUser updateUserExp(Integer userID, Integer experience, Integer isLimited) throws Exception ;
}
