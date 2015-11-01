package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.SecurityRes;

public interface SecurityResMapper {
    int deleteByPrimaryKey(String resID);

    int insert(SecurityRes record);

    int insertSelective(SecurityRes record);

    SecurityRes selectByPrimaryKey(String resID);

    int updateByPrimaryKeySelective(SecurityRes record);

    int updateByPrimaryKey(SecurityRes record);
}