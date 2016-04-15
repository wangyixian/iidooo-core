package com.iidooo.core.mapper;

import com.iidooo.core.model.po.SecurityRoleResource;

public interface SecurityRoleResourceMapper {
    int deleteByPrimaryKey(Integer roleResID);

    int insert(SecurityRoleResource record);

    int insertSelective(SecurityRoleResource record);

    SecurityRoleResource selectByPrimaryKey(Integer roleResID);

    int updateByPrimaryKeySelective(SecurityRoleResource record);

    int updateByPrimaryKey(SecurityRoleResource record);
}