package com.iidooo.core.service;

import com.iidooo.core.model.po.SecurityUser;

public interface SecurityUserService {
    SecurityUser getSecurityUserByID(Integer userID);
    
    SecurityUser getSecurityUserByEmail(String email);

    SecurityUser createDefaultUser(String photoURL, String email);

    SecurityUser updateUserInfo(SecurityUser securityUser);

    SecurityUser updateUserExp(Integer userID, Integer experience, Integer isLimited) throws Exception ;
}
