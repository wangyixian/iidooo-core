package com.iidooo.core.model;

import com.iidooo.core.enums.MessageLevel;
import com.iidooo.core.enums.MessageType;

public class Message {
    private Integer code;

    private MessageLevel type;

    private String field;

    private String description;

    public Message(Integer code, MessageLevel type) {
        this.code = code;
        this.type = type;
        this.description = "";
    }

    public Message(Integer code, MessageLevel type, String field) {
        this.code = code;
        this.type = type;
        this.field = field;
        this.description = "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MessageLevel getType() {
        return type;
    }

    public void setType(MessageLevel type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
