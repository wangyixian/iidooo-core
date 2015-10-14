package com.iidooo.core.dao.extend;

import java.util.List;

import com.iidooo.core.dto.extend.SecurityResDto;
import com.iidooo.core.dto.extend.SecurityRoleDto;

public interface SecurityResDao {
    List<SecurityResDto> selectAll();
    
    List<SecurityResDto> selectResourceListByRoles(List<SecurityRoleDto> roles);
}
