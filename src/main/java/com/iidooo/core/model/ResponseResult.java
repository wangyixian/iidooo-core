package com.iidooo.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.iidooo.core.constant.MessageConstant;
import com.iidooo.core.constant.RegularConstant;
import com.iidooo.core.enums.MessageLevel;
import com.iidooo.core.enums.MessageType;
import com.iidooo.core.enums.ResponseStatus;
import com.iidooo.core.util.StringUtil;
import com.iidooo.core.util.ValidateUtil;

public class ResponseResult {

    // 响应状态
    private Integer status;

    // 响应内容
    private Object data;

    // 附带的响应message，为了前台显示相关信息用
    private List<Message> messages;

    public ResponseResult() {
        messages = new ArrayList<Message>();
        data = new JSONArray();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public boolean checkFieldRequired(String field, Object value) {
        if(value == null){
            String message = StringUtil.replace(MessageConstant.FIELD_REQUIRED, field);
            this.addMessage(MessageType.FieldRequired, MessageLevel.WARN, field, message);
            return false;
        }
        
        if (value instanceof String && StringUtil.isBlank((String)value)) {
            String message = StringUtil.replace(MessageConstant.FIELD_REQUIRED, field);
            this.addMessage(MessageType.FieldRequired, MessageLevel.WARN, field, message);
            return false;
        }
        return true;
    }

    public boolean checkFieldInteger(String field, String value) {
        if (!ValidateUtil.isMatch(value, RegularConstant.REGEX_NUMBER)) {
            String message = StringUtil.replace(MessageConstant.FIELD_NUMBER_REQUIRED, field);
            this.addMessage(MessageType.FieldNumberRequired, MessageLevel.WARN, field, message);
            return false;
        }
        return true;
    }

    public boolean checkFieldEmail(String field, String value) {
        if (!ValidateUtil.isEmail(value)) {
            String message = StringUtil.replace(MessageConstant.FIELD_EMAIL_REQUIRED, field);
            this.addMessage(MessageType.FieldEmailRequired, MessageLevel.WARN, field, message);
            return false;
        }
        return true;
    }
    
    public boolean checkFieldUnique(String field, Object value) {
        if (value != null) {
            String message = StringUtil.replace(MessageConstant.FIELD_UNIQUE_CONSTRAINTS, field);
            this.addMessage(MessageType.UniqueConstraints, MessageLevel.WARN, field, message);
            return false;
        }
        return true;
    }

    public boolean checkVerifyCode(Map<String, String> verifyCodeMap, String key, String verifyCode) {
        String field = "verifyCode";
        if (!verifyCodeMap.containsKey(key) || !verifyCodeMap.get(key).equals(verifyCode)) {
            this.addMessage(MessageType.FieldVerifyCodeRequired, MessageLevel.WARN, field, MessageConstant.FIELD_VERIFY_CODE_WRONG);
            return false;
        }
        return true;
    }    

    public void checkQueryEmpty(String description) {
        this.addMessage(MessageType.Information, MessageLevel.INFO, "", description);
        this.setStatus(ResponseStatus.QueryEmpty.getCode());
    }

    public void checkException(Exception e) {
        this.addMessage(MessageType.Exception, MessageLevel.FATAL, "", e.toString());
        this.setStatus(ResponseStatus.Failed.getCode());
    }

    private void addMessage(MessageType messageType, MessageLevel level, String field, String description) {
        Message message = new Message(messageType.getCode(), level, field);
        message.setDescription(description);
        this.getMessages().add(message);
    }
}
