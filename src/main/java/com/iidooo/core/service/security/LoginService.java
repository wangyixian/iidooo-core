package com.iidooo.core.service.security;

import java.util.List;

import com.iidooo.core.dto.extend.SecurityResDto;
import com.iidooo.core.dto.extend.SecurityRoleDto;
import com.iidooo.core.dto.extend.SecurityUserDto;

public interface LoginService {
    SecurityUserDto login(String loginID, String password);
    
    List<SecurityRoleDto> getUserRoleList(int userID);
    
    List<SecurityResDto> getUserResourceList(List<SecurityRoleDto> roles);
}
