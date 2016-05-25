package com.iidooo.core.mapper;

import java.util.List;

import com.iidooo.core.model.po.SecurityRole;

public interface SecurityRoleMapper {
    int deleteByPrimaryKey(Integer roleID);

    int insert(SecurityRole record);

    int insertSelective(SecurityRole record);

    SecurityRole selectByPrimaryKey(Integer roleID);
    
    /**
     * 得到所有的角色
     * @return 角色一览
     */
    List<SecurityRole> selectAll();

    int updateByPrimaryKeySelective(SecurityRole record);

    int updateByPrimaryKey(SecurityRole record);
}