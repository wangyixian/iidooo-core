package com.iidooo.core.dto.extend;

import java.util.List;

import com.iidooo.core.dto.generate.SecurityUser;

public class SecurityUserDto extends SecurityUser{
    private List<SecurityRoleDto> roleList;
    private String roleString;
    public List<SecurityRoleDto> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SecurityRoleDto> roleList) {
        this.roleList = roleList;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }
    
}
