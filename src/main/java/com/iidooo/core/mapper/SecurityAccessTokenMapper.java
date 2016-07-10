package com.iidooo.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.model.po.SecurityAccessToken;

public interface SecurityAccessTokenMapper {
    int deleteByPrimaryKey(Integer tokenID);

    /**
     * 插入一个SecurityAccessToken
     * @param securityAccessToken 该对象会被插入
     * @return 返回插入成功的结果
     */
    int insert(SecurityAccessToken securityAccessToken);

    /**
     * 根据用户获取AccessToken对象
     * @param userID 用户UserID
     * @return 返回SecutiryAccessToken
     */
    SecurityAccessToken selectByUserID(Integer userID);
    
    /**
     * 通过Token和用户ID来获取一个AccessToken对象
     * @param token 
     * @param userID
     * @return 返回SecutiryAccessToken
     */
    SecurityAccessToken selectByTokenUserID(@Param("token")String token, @Param("userID")Integer userID);

    /**
     * 通过TokenID更新SecurityAccessToken
     * @param securityAccessToken 要更新的数据内容
     * @return 返回所影响的行数
     */
    int update(SecurityAccessToken securityAccessToken);
}