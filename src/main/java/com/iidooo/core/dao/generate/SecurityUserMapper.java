package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.SecurityUser;

public interface SecurityUserMapper {
    int deleteByPrimaryKey(String userID);

    int insert(SecurityUser record);

    int insertSelective(SecurityUser record);

    SecurityUser selectByPrimaryKey(String userID);

    int updateByPrimaryKeySelective(SecurityUser record);

    int updateByPrimaryKey(SecurityUser record);
}