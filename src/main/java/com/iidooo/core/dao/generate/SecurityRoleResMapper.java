package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.SecurityRoleRes;

public interface SecurityRoleResMapper {
    int deleteByPrimaryKey(Integer roleResID);

    int insert(SecurityRoleRes record);

    int insertSelective(SecurityRoleRes record);

    SecurityRoleRes selectByPrimaryKey(Integer roleResID);

    int updateByPrimaryKeySelective(SecurityRoleRes record);

    int updateByPrimaryKey(SecurityRoleRes record);
}