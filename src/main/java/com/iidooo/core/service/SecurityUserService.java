package com.iidooo.core.service;

import com.iidooo.core.model.vo.SecurityUserInfo;

public interface SecurityUserService {
    SecurityUserInfo getUserInfoByID(Integer userID);
    
    SecurityUserInfo createDefaultUser(String photoURL);
    
    SecurityUserInfo updateUserInfo(SecurityUserInfo userInfo);
}
