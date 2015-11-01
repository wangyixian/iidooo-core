package com.iidooo.core.dao.generate;

import com.iidooo.core.dto.generate.DictClass;

public interface DictClassMapper {
    int deleteByPrimaryKey(String dictClassID);

    int insert(DictClass record);

    int insertSelective(DictClass record);

    DictClass selectByPrimaryKey(String dictClassID);

    int updateByPrimaryKeySelective(DictClass record);

    int updateByPrimaryKey(DictClass record);
}