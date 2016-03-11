package com.iidooo.core.enums;

public enum HttpStatus {

    // 成功返回状态，对应，GET,PUT,PATCH,DELETE.
    OK(200, "OK"),

    // 成功创建
    Created(201, "created"),

    // HTTP缓存有效
    NotModified(304, "not modified"),

    // 请求格式错误
    BadRequest(400, "bad request"),

    // 未授权
    Unauthorized(401, "unauthorized"),

    // 鉴权成功，但是该用户没有权限
    Forbidden(403, "forbidden"),

    // 请求的资源不存在
    NotFound(404, "not found"),

    // 该http方法不被允许
    MethodNotAllowed(405, "method not allowed"),

    // 这个url对应的资源现在不可用
    Gone(410, "gone"),

    // 请求类型错误
    UnsupportedMediaType(415, "unsupported media type"),

    // 校验错误时用
    UnprocessableEntity(422, "unprocessable entity"),

    // 请求过多
    TooManyRequest(429, "too many request");

    @SuppressWarnings("unused")
    private String desctiption;

    @SuppressWarnings("unused")
    private int code;

    private HttpStatus(int code, String desctiption) {
        this.code = code;
        this.desctiption = desctiption;
    }
}
