package com.iidooo.core.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.model.Page;

public interface SecurityUserDao {
    
    SecurityUserDto selectByPrimaryKey(Integer userID);
    
    
    /**
     * 根据用户名和密码来获得SecurityUserDto对象以验证是否成功登录
     * @param loginID 用户的LoginID
     * @param password 用户的密码
     * @return SecurityUserDto 用户信息对象
     */
    SecurityUserDto selectForLogin(@Param("loginID")String loginID, @Param("password")String password);
    
    SecurityUserDto selectByLoginID(String loginID);
    
    int selectAllCount();

    List<SecurityUserDto> selectAll(Page page);
    
    int updateByPrimaryKey(SecurityUserDto user);
    
    int updateLoginTime(SecurityUserDto user);
    
    int updatePassword(SecurityUserDto user);
    
    int insert(SecurityUserDto user);

    int update(SecurityUserDto user);

    int delete(SecurityUserDto user);
}
