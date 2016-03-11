package com.iidooo.core.model;

import java.util.ArrayList;
import java.util.List;

import com.iidooo.core.enums.ResponseStatus;

public class ResponseResult {

    // 响应状态
    private ResponseStatus status;

    // 响应内容
    private Object data;

    // 附带的响应message，为了前台显示相关信息用
    private List<Message> messages;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Message> getMessages() {
        if (messages == null) {
            messages = new ArrayList<Message>();
        }
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
