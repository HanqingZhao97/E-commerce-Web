package com.yunniu.lease.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class FatherMsg implements Serializable {
    
    private Integer id;//主键ID
    
    private Integer businessid;//商家ID

    private Integer msgid;//

    private Integer userid;//用户ID

    private Integer equipmentid;//设备ID

    private String method;//冷却方式

    private Date endtime;//完成时间

    private String equipmentnum;//设备编号
    
    private List<Msg> msgs;

    private Integer uid;//用户id
    
    private String createtime;//开始时间
    
    private String settemperature;//温度补偿
    
    private String equipmentname;//设备名称
    
   
    
    
    private String saddress;//商家地址
    private String iphone;//商家手机
    private String sname;//设备名称
    private String setVoltage;//设定电压
    private String setTime;//设定时间
    private String voltageTime;//熔接时间
    
    
    private Date pringTime;//打印时间

    public Date getPringTime () {
        return pringTime;
    }

    public void setPringTime (Date pringTime) {
        this.pringTime = pringTime;
    }

    public String getSaddress () {
        return saddress;
    }

    public void setSaddress (String saddress) {
        this.saddress = saddress;
    }

    public String getIphone () {
        return iphone;
    }

    public void setIphone (String iphone) {
        this.iphone = iphone;
    }

    public String getSname () {
        return sname;
    }

    public void setSname (String sname) {
        this.sname = sname;
    }

    public String getSetVoltage () {
        return setVoltage;
    }

    public void setSetVoltage (String setVoltage) {
        this.setVoltage = setVoltage;
    }

    public String getSetTime () {
        return setTime;
    }

    public void setSetTime (String setTime) {
        this.setTime = setTime;
    }

    public String getVoltageTime () {
        return voltageTime;
    }

    public void setVoltageTime (String voltageTime) {
        this.voltageTime = voltageTime;
    }

    public String getEquipmentname () {
        return equipmentname;
    }

    public void setEquipmentname (String equipmentname) {
        this.equipmentname = equipmentname;
    }

    public String getCreatetime () {
        return createtime;
    }

    public void setCreatetime (String createtime) {
        this.createtime = createtime;
    }

    public String getSettemperature () {
        return settemperature;
    }

    public void setSettemperature (String settemperature) {
        this.settemperature = settemperature;
    }

    public Integer getUid () {
        return uid;
    }

    public void setUid (Integer uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessid() {
        return businessid;
    }

    public void setBusinessid(Integer businessid) {
        this.businessid = businessid;
    }

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Integer equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getEquipmentnum() {
        return equipmentnum;
    }

    public void setEquipmentnum(String equipmentnum) {
        this.equipmentnum = equipmentnum == null ? null : equipmentnum.trim();
    }

    public List<Msg> getMsgs () {
        return msgs;
    }

    public void setMsgs (List<Msg> msgs) {
        this.msgs = msgs;
    }

    @Override
    public String toString () {
        return "FatherMsg{" +
                "id=" + id +
                ", businessid=" + businessid +
                ", msgid=" + msgid +
                ", userid=" + userid +
                ", equipmentid=" + equipmentid +
                ", method='" + method + '\'' +
                ", endtime=" + endtime +
                ", equipmentnum='" + equipmentnum + '\'' +
                ", msgs=" + msgs +
                '}';
    }
}