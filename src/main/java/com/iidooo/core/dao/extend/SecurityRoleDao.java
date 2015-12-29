package com.iidooo.core.dao.extend;

import java.util.List;

import com.iidooo.core.dto.extend.SecurityRoleDto;

public interface SecurityRoleDao {

    /**
     * 通过UserID获得该用户角色一览
     * @param userID 用户的UserID
     * @return SecurityRoleDto 对象的列表
     */
    List<SecurityRoleDto> selectByUserID(Integer userID);
}