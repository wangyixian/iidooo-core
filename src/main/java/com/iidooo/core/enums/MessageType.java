package com.iidooo.core.enums;

public enum MessageType {

    Exception(40001),

    FieldRequired(50001);

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
