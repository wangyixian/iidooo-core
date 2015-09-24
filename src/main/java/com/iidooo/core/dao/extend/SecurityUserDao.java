package com.iidooo.core.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.core.dto.PageDto;
import com.iidooo.core.dto.extend.SecurityUserDto;

public interface SecurityUserDao {
    
    SecurityUserDto selectByPrimaryKey(Integer userID);
    
    SecurityUserDto selectForLogin(@Param("loginID")String loginID, @Param("password")String password);
    
    SecurityUserDto selectByLoginID(String loginID);
    
    int selectAllCount();

    List<SecurityUserDto> selectAll(PageDto page);
    
    int updateByPrimaryKey(SecurityUserDto user);
    
    int updateLoginTime(SecurityUserDto user);
    
    int updatePassword(SecurityUserDto user);
    
    int insert(SecurityUserDto user);

    int update(SecurityUserDto user);

    int delete(SecurityUserDto user);
}
