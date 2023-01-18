package com.yunniu.lease.model;

import lombok.Data;

@Data
public class RefundDeposit {

    private String orderId;
    private String goodsName;
    // 付款金额/押金
    private Double goodsPrice;
    private Integer goodsNum;
    private String payAmount;

    //租赁开始日期
    private String leaseStartDate;
    //租赁结束日期
    private String leaseEndDate;
    //租赁天数
    private Integer leaseDay;
    //租赁每天价格
    private Double leaseDayPrice;
    // 退款金额
    private Double refundAmount;

    public String getOrderId () {
        return orderId;
    }

    public void setOrderId (String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsName () {
        return goodsName;
    }

    public void setGoodsName (String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsPrice () {
        return goodsPrice;
    }

    public void setGoodsPrice (Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsNum () {
        return goodsNum;
    }

    public void setGoodsNum (Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getPayAmount () {
        return payAmount;
    }

    public void setPayAmount (String payAmount) {
        this.payAmount = payAmount;
    }

    public String getLeaseStartDate () {
        return leaseStartDate;
    }

    public void setLeaseStartDate (String leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public String getLeaseEndDate () {
        return leaseEndDate;
    }

    public void setLeaseEndDate (String leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public Integer getLeaseDay () {
        return leaseDay;
    }

    public void setLeaseDay (Integer leaseDay) {
        this.leaseDay = leaseDay;
    }

    public Double getLeaseDayPrice () {
        return leaseDayPrice;
    }

    public void setLeaseDayPrice (Double leaseDayPrice) {
        this.leaseDayPrice = leaseDayPrice;
    }

    public Double getRefundAmount () {
        return refundAmount;
    }

    public void setRefundAmount (Double refundAmount) {
        this.refundAmount = refundAmount;
    }
}
