package com.iidooo.core.enums;

public enum UserType {
       
    Admin("1"),
    
    Member("2");
    
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private UserType(String code) {
        this.code = code;
    }
}
