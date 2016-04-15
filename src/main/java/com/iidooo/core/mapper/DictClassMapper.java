package com.iidooo.core.mapper;

import com.iidooo.core.model.po.DictClass;

public interface DictClassMapper {
    int deleteByPrimaryKey(Integer dictClassID);

    int insert(DictClass record);

    int insertSelective(DictClass record);

    DictClass selectByPrimaryKey(Integer dictClassID);

    int updateByPrimaryKeySelective(DictClass record);

    int updateByPrimaryKey(DictClass record);
}