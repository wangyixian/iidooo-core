package com.iidooo.core.dao.extend;

import java.util.List;

import com.iidooo.core.dto.extend.SecurityRoleDto;
import com.iidooo.core.dto.extend.SecurityUserDto;

public interface SecurityRoleDao {

    List<SecurityRoleDto> selectSecurityRoleList(SecurityUserDto securityUser);

}