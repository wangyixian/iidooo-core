package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.SecurityRoleRes;

public interface SecurityRoleResMapper {
    int deleteByPrimaryKey(String roleResID);

    int insert(SecurityRoleRes record);

    int insertSelective(SecurityRoleRes record);

    SecurityRoleRes selectByPrimaryKey(String roleResID);

    int updateByPrimaryKeySelective(SecurityRoleRes record);

    int updateByPrimaryKey(SecurityRoleRes record);
}