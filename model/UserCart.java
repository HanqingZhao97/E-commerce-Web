package com.yunniu.lease.model;

import lombok.Data;

@Data
public class UserCart {

    private String userCartId;

    private String userId;

    private String goodsId;

    private Double goodsPrice;

    private String goodsName;

    private String goodsSpecName;

    private Double goodsSpecPrice;

    private String goodsSpecId;

    private String deliveryId;

    private Integer goodsNum;

    private String goodsImg;

    private String createTime;

    //0普通商品  1租赁商品
    private String isLease;
    //租赁天数
    private Integer leaseDay;


    //购物车商品选中状态     0未选中  1选中
    private Integer userCartType;

    public String getUserCartId () {
        return userCartId;
    }

    public void setUserCartId (String userCartId) {
        this.userCartId = userCartId;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getGoodsId () {
        return goodsId;
    }

    public void setGoodsId (String goodsId) {
        this.goodsId = goodsId;
    }

    public Double getGoodsPrice () {
        return goodsPrice;
    }

    public void setGoodsPrice (Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsName () {
        return goodsName;
    }

    public void setGoodsName (String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSpecName () {
        return goodsSpecName;
    }

    public void setGoodsSpecName (String goodsSpecName) {
        this.goodsSpecName = goodsSpecName;
    }

    public Double getGoodsSpecPrice () {
        return goodsSpecPrice;
    }

    public void setGoodsSpecPrice (Double goodsSpecPrice) {
        this.goodsSpecPrice = goodsSpecPrice;
    }

    public String getGoodsSpecId () {
        return goodsSpecId;
    }

    public void setGoodsSpecId (String goodsSpecId) {
        this.goodsSpecId = goodsSpecId;
    }

    public String getDeliveryId () {
        return deliveryId;
    }

    public void setDeliveryId (String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getGoodsNum () {
        return goodsNum;
    }

    public void setGoodsNum (Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsImg () {
        return goodsImg;
    }

    public void setGoodsImg (String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getCreateTime () {
        return createTime;
    }

    public void setCreateTime (String createTime) {
        this.createTime = createTime;
    }

    public String getIsLease () {
        return isLease;
    }

    public void setIsLease (String isLease) {
        this.isLease = isLease;
    }

    public Integer getLeaseDay () {
        return leaseDay;
    }

    public void setLeaseDay (Integer leaseDay) {
        this.leaseDay = leaseDay;
    }

    public Integer getUserCartType () {
        return userCartType;
    }

    public void setUserCartType (Integer userCartType) {
        this.userCartType = userCartType;
    }
}
