package com.iidooo.core.model;

import com.iidooo.core.enums.MessageCode;
import com.iidooo.core.enums.MessageType;

public class Message {
    private MessageCode code;

    private MessageType type;

    private String field;

    private String description;

    public Message(MessageCode code, MessageType type) {
        this.code = code;
        this.type = type;
    }

    public Message(MessageCode code, MessageType type, String field) {
        this.code = code;
        this.type = type;
        this.field = field;
    }

    public MessageCode getCode() {
        return code;
    }

    public void setCode(MessageCode code) {
        this.code = code;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
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
