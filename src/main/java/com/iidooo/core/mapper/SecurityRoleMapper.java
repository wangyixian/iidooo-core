package com.iidooo.core.mapper;

import com.iidooo.core.model.po.SecurityRole;

public interface SecurityRoleMapper {
    int deleteByPrimaryKey(Integer roleID);

    int insert(SecurityRole record);

    int insertSelective(SecurityRole record);

    SecurityRole selectByPrimaryKey(Integer roleID);

    int updateByPrimaryKeySelective(SecurityRole record);

    int updateByPrimaryKey(SecurityRole record);
}