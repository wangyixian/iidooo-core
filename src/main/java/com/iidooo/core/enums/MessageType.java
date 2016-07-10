package com.iidooo.core.enums;

public enum MessageType {

    Information(100),
    
    Exception(401),

    FieldRequired(501),
    
    FieldNumberRequired(502),
    
    IsSlient(503),
    
    FieldDuplicate(504),
    
    FieldEmailRequired(505),
    
    // 验证码不正确的错误信息
    FieldVerifyCodeRequired(506),
    
    // 唯一性约束
    UniqueConstraints(507);

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
