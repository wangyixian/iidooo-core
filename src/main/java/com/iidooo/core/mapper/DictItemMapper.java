package com.iidooo.core.mapper;

import java.util.List;

import com.iidooo.core.model.po.DictItem;

public interface DictItemMapper {
    int deleteByPrimaryKey(Integer dictItemID);

    int insert(DictItem record);

    int insertSelective(DictItem record);

    /**
     * 根据ClassCode获取字典项列表
     * @param dictClassCode 字典 class code
     * @return 字典项列表
     */
    List<DictItem> selectByClassCode(String dictClassCode);

    int updateByPrimaryKeySelective(DictItem record);

    int updateByPrimaryKey(DictItem record);
}