package com.iidooo.core.mapper;

import com.iidooo.core.model.po.SecurityUser;

public interface SecurityUserMapper {
    int deleteByPrimaryKey(Integer userID);

    int insert(SecurityUser record);

    /**
     * 插入一个SecurityUser
     * @param user 该记录的信息会被插入
     * @return 返回影响行数以判断是否插入成功
     */
    int insertSelective(SecurityUser user);

    /**
     * 通过用户主键ID获得UserInfo对象
     * @param userID 用户主键ID
     * @return securityUser对象
     */
    SecurityUser selectByUserID(Integer userID);
    
    /**
     * 通过用户的Email获得用户对象
     * @param email 用户的Email
     * @return 所获的的用户对象
     */
    SecurityUser selectByEmail(String email);

    /**
     * 根据用户ID更新用户信息
     * @param user 该用户信息会被更新进数据库
     * @return 更行影响的行数
     */
    int updateByUserID(SecurityUser user);
}