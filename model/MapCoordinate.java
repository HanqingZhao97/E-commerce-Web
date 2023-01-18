package com.yunniu.lease.model;

import lombok.Data;

@Data
public class MapCoordinate {

    private String id;
    //用户填写地址
    private String address;
    //用户填写地址电话
    private String phone;
    //地图接口获取地址
    private String title;
    //地图经度
    private String lat;
    //地图纬度
    private String lng;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getLat () {
        return lat;
    }

    public void setLat (String lat) {
        this.lat = lat;
    }

    public String getLng () {
        return lng;
    }

    public void setLng (String lng) {
        this.lng = lng;
    }
}
