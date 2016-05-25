package com.iidooo.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.po.DictItem;

public interface DictItemMapper {
    int deleteByPrimaryKey(Integer dictItemID);

    int insert(DictItem record);

    int insertSelective(DictItem record);

    /**
     * 根据ClassCode获取字典项列表
     * 
     * @param dictClassCode 字典 class code
     * @return 字典项列表
     */
    List<DictItem> selectByClassCode(String dictClassCode);
        
    /**
     * 根据ClassCode和ItemCode获取字典项
     * @param dictClassCode 字典的ClassCode
     * @param dictItemCode 字典项的ItemCode
     * @return 所获得的指定字典项对象
     */
    DictItem selectByClassItemCode(@Param("dictClassCode")String dictClassCode, @Param("dictItemCode")String dictItemCode);

    int updateByPrimaryKeySelective(DictItem record);

    int updateByPrimaryKey(DictItem record);
}