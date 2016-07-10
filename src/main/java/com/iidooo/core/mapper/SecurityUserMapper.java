package com.iidooo.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
     * 通过LoginID和Password获取用户信息
     * @param loginID 登录名
     * @param password 密码
     * @return 返回的用户对象
     */
    SecurityUser selectByLogin(@Param("loginID")String loginID, @Param("password")String password);
    
    /**
     * 通过Email和Password获取用户信息
     * @param email 邮箱地址
     * @param password 密码
     * @return 返回的用户对象
     */
    SecurityUser selectByEmailLogin(@Param("email")String email, @Param("password")String password);
    
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
     * 通过用户的登录Token获得用户对象
     * @param token 登录令牌
     * @return 所获的的用户对象
     */
    SecurityUser selectByToken(String token);
    
    /**
     * 查询用户一览
     * @param securityUser 封装的查询条件
     * @return 所获得的用户一览
     */
    List<SecurityUser> selectForSearch(SecurityUser securityUser);

    /**
     * 根据用户ID更新用户信息
     * @param user 该用户信息会被更新进数据库
     * @return 更行影响的行数
     */
    int updateByUserID(SecurityUser user);
}