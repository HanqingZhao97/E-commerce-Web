package com.yunniu.lease.model;

/**
 * 故障分析表
 */
public class Fault {
    
    private Integer id;//主键ID

    
    private String faultInfo;//故障现象

    
    private String reason;//原因

    
    private String method;//解决方法

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getFaultInfo() {
        return faultInfo;
    }

    
    public void setFaultInfo(String faultInfo) {
        this.faultInfo = faultInfo == null ? null : faultInfo.trim();
    }

    
    public String getReason() {
        return reason;
    }

    
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    
    public String getMethod() {
        return method;
    }

    
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }
}