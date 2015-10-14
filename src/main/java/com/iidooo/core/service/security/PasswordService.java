package com.iidooo.core.service.security;

import com.iidooo.core.dto.extend.SecurityUserDto;


public interface PasswordService {
    boolean checkOldPassword(String loginID, String oldPassword);
    
    boolean saveNewPassword(SecurityUserDto user, String newPassword);
}
