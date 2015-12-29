package com.iidooo.core.dao.extend;

import java.util.List;

import com.iidooo.core.dto.extend.SecurityResDto;
import com.iidooo.core.dto.extend.SecurityRoleDto;

public interface SecurityResDao {
    List<SecurityResDto> selectAll();
    
    /**
     * 获得该角色列表相关的所有可访问资源列表
     * @param roles 角色一览
     * @return 可访问资源列表
     */
    List<SecurityResDto> selectResourceListByRoles(List<SecurityRoleDto> roles);
}
