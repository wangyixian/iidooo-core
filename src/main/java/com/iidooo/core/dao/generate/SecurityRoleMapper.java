package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.SecurityRole;

public interface SecurityRoleMapper {
    int deleteByPrimaryKey(String roleID);

    int insert(SecurityRole record);

    int insertSelective(SecurityRole record);

    SecurityRole selectByPrimaryKey(String roleID);

    int updateByPrimaryKeySelective(SecurityRole record);

    int updateByPrimaryKey(SecurityRole record);
}