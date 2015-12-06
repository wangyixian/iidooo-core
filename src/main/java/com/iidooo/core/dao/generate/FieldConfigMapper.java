package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.FieldConfig;

public interface FieldConfigMapper {
    int deleteByPrimaryKey(Integer fieldID);

    int insert(FieldConfig record);

    int insertSelective(FieldConfig record);

    FieldConfig selectByPrimaryKey(Integer fieldID);

    int updateByPrimaryKeySelective(FieldConfig record);

    int updateByPrimaryKey(FieldConfig record);
}