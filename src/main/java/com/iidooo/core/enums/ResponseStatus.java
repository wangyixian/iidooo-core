package com.iidooo.core.enums;

public enum ResponseStatus {

    // 处理成功，返回OK
    OK(200),
    
    // 处理成功，但返回值为空
    QueryEmpty(201),

    // 处理失败
    Failed(400);

    @SuppressWarnings("unused")
    private int code;

    private ResponseStatus(int code) {
        this.code = code;
    }
}
