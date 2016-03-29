package com.iidooo.core.enums;

public enum ResponseStatus {

    // 处理成功，返回OK
    OK(200),

    // 处理成功，但返回值为空
    QueryEmpty(201),

    // 处理失败
    Failed(400),
    
    // 创建失败
    InsertFailed(401),
    
    UpdateFailed(402),
    
    // 应为一些原因而受限制后处理失败
    ConfinedFailed(403);

    private Integer code;

    private ResponseStatus(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
