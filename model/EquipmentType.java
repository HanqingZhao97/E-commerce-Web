package com.yunniu.lease.model;

import java.util.Date;

public class EquipmentType {
    
    private Integer id;//主键ID

    
    private String typeName;// 类型名称

    
    private Date createTime;//创建时间

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getTypeName() {
        return typeName;
    }

    
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString () {
        return "EquipmentType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}