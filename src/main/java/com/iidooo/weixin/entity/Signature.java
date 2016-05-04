package com.iidooo.weixin.entity;

public class Signature {
    private String nonceStr;

    private String jsAPITicket;

    private long timestamp;

    private String url;
    
    private String signature;

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getJsAPITicket() {
        return jsAPITicket;
    }

    public void setJsAPITicket(String jsAPITicket) {
        this.jsAPITicket = jsAPITicket;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
