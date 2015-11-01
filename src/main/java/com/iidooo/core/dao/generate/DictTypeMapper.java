package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.DictType;

public interface DictTypeMapper {
    int deleteByPrimaryKey(String dictTypeID);

    int insert(DictType record);

    int insertSelective(DictType record);

    DictType selectByPrimaryKey(String dictTypeID);

    int updateByPrimaryKeySelective(DictType record);

    int updateByPrimaryKey(DictType record);
}