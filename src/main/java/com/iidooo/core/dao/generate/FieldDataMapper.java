package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.FieldData;

public interface FieldDataMapper {
    int deleteByPrimaryKey(Integer dataID);

    int insert(FieldData record);

    int insertSelective(FieldData record);

    FieldData selectByPrimaryKey(Integer dataID);

    int updateByPrimaryKeySelective(FieldData record);

    int updateByPrimaryKey(FieldData record);
}