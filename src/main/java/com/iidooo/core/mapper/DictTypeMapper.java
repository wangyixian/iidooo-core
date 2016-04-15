package com.iidooo.core.mapper;

import com.iidooo.core.model.po.DictType;

public interface DictTypeMapper {
    int deleteByPrimaryKey(Integer dictTypeID);

    int insert(DictType record);

    int insertSelective(DictType record);

    DictType selectByPrimaryKey(Integer dictTypeID);

    int updateByPrimaryKeySelective(DictType record);

    int updateByPrimaryKey(DictType record);
}