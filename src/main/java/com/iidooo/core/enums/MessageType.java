package com.iidooo.core.enums;

public enum MessageType {

    Exception(401),

    FieldRequired(501),
    
    FieldNumberRequired(502),
    
    IsSlient(503);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private MessageType(Integer code) {
        this.code = code;
    }
}
