package com.iidooo.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.po.HisUserExp;

public interface HisUserExpMapper {
    int deleteByPrimaryKey(Integer hisID);

    /**
     * 插入历史经验变更记录
     * @param hisUserExp 变更记录的对象
     * @return 影响行数
     */
    int insert(HisUserExp hisUserExp);

    /**
     * 根据日期查询指定用户的经验值变更
     * @param userID 指定的用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 经验值变更履历
     */
    List<HisUserExp> selectByUserID(@Param("userID")Integer userID, @Param("startTime")String startTime, @Param("endTime")String endTime);

    int updateByPrimaryKeySelective(HisUserExp record);

    int updateByPrimaryKey(HisUserExp record);
}