package com.iidooo.core.service.security;

import com.iidooo.core.dto.extend.SecurityUserDto;

public interface UserinfoService {
    
    SecurityUserDto getUser(String loginID);
    
    boolean saveUser(SecurityUserDto user);
}
