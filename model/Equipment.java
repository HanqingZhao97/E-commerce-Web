package com.yunniu.lease.model;

import java.util.Date;

public class Equipment {
    
    private Integer id;//主键ID
    private String equipmentName;//设备名称
    private Date createTime;//设备购入时间
    private Integer equipmentTypeid;//类型ID
    private Integer  businessId;//商家ID
    private Integer istate;//状态  0故障  1正常
    private String voltage;//电压
    private String timeLength;//焊接时间
    private String cooling;//冷却时间
    private String temperatureIstate;//1打开 2关闭
    private String equipmentNum;//设备编号
    private String [] buinesImgArr;//拼接图片
    private String img;//设备图片
    
    //对应商家  设备类型 字段封装
    private String buinessName;//商家名称
    private String iphone;//商家手机
    private String saddress;//商家地址
    private String buinesimg;//商家图片
    private String typeName;//设备类型名称

    public String getBuinessName () {
        return buinessName;
    }

    public void setBuinessName (String buinessName) {
        this.buinessName = buinessName;
    }

    public String getIphone () {
        return iphone;
    }

    public void setIphone (String iphone) {
        this.iphone = iphone;
    }

    public String getSaddress () {
        return saddress;
    }

    public void setSaddress (String saddress) {
        this.saddress = saddress;
    }

    public String getBuinesimg () {
        return buinesimg;
    }

    public void setBuinesimg (String buinesimg) {
        this.buinesimg = buinesimg;
    }

    public String getTypeName () {
        return typeName;
    }

    public void setTypeName (String typeName) {
        this.typeName = typeName;
    }

    public String[] getBuinesImgArr () {
        return buinesImgArr;
    }

    public void setBuinesImgArr (String[] buinesImgArr) {
        this.buinesImgArr = buinesImgArr;
    }

    public String getEquipmentNum () {
        return equipmentNum;
    }

    public void setEquipmentNum (String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getImg () {
        return img;
    }

    public void setImg (String img) {
        this.img = img;
    }
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getEquipmentName() {
        return equipmentName;
    }

    
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName == null ? null : equipmentName.trim();
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public Integer getEquipmentTypeid() {
        return equipmentTypeid;
    }

    
    public void setEquipmentTypeid(Integer equipmentTypeid) {
        this.equipmentTypeid = equipmentTypeid;
    }

    
    public Integer getIstate() {
        return istate;
    }

    
    public void setIstate(Integer istate) {
        this.istate = istate;
    }

    
    public String getVoltage() {
        return voltage;
    }

    
    public void setVoltage(String voltage) {
        this.voltage = voltage == null ? null : voltage.trim();
    }

    
    public String getTimeLength() {
        return timeLength;
    }

    
    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength == null ? null : timeLength.trim();
    }

    
    public String getCooling() {
        return cooling;
    }

    
    public void setCooling(String cooling) {
        this.cooling = cooling == null ? null : cooling.trim();
    }

    
    public String getTemperatureIstate() {
        return temperatureIstate;
    }

    
    public void setTemperatureIstate(String temperatureIstate) {
        this.temperatureIstate = temperatureIstate == null ? null : temperatureIstate.trim();
    }

    public Integer getBusinessId () {
        return businessId;
    }

    public void setBusinessId (Integer businessId) {
        this.businessId = businessId;
    }
}