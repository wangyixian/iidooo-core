package com.iidooo.core.api.service;

import com.iidooo.core.dto.extend.SecurityUserDto;

public interface SecurityUserService {
    SecurityUserDto getSecurityUser(Integer userID);
}
