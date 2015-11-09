package com.iidooo.weixin.entity;

public class Signature {
    private String noncestr;

    private String jsAPITicket;

    private long timestamp;

    private String url;
    
    private String signature;

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
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
