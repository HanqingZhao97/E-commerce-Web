package com.yunniu.lease.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {

    private String orderId;
    private String shareUserId;//通过分享商二维码下单    分销商用户ID

    private String userId;
    //商品名称
//    private String goodsName;
    //商品图片
//    private String goodsImg;

    //支付订单编号
    private String outTradeNo;
    //微信订单号
    private String transactionId;
    //0待支付,1待发货,2待收货,3租赁中4已完成
    private Integer orderState;
    //提醒发货
    private Integer remind;
    //金额
    private double totalAmount;
    //运费
//    private double transportAmount;
    // 支付金额
    private double payAmount;

    private double refundAmount; //退款金额

    //数量
    private int goodsNum;

    //配送方式ID
    private String deliveryId;
    //自取 取货地址ID
    private String mapCoordinateId;
    //自取 取货地址
    private String mapCoordinateName;
    //配送方式
    private String deliveryName;
    //收货地址ID
    private String userAddressId;
    //收货人姓名
    private String receiverName;
    //收货人手机
    private String receiverPhone;

    private String province;

    private String city;

    private String county;

    private String receiverAddress;
    //备注
    private String orderRemarks;
    //快递名称
    private String trackingName;

    private String trackingNum;
    //支付时间
    private String payTime;
    //下单时间
    private String createTime;

    //发货时间
    private String deliverTime;
    //收货时间
    private String finishTime;
    //租赁开始日期
    private String leaseStartDate;
    //租赁结束日期
    private String leaseEndDate;
    //租赁天数
    private Integer leaseDay;
    //0普通商品1租赁商品
    private String isLease;


//    private String isFirst;是否首次

//    private List<OrderDetailGoods> orderDetailGoodsList = new ArrayList<>();


    private List<OrderDetail> orderDetailGoodsList = new ArrayList<>();


//    private List<Trace> traceList = new ArrayList<>();


    private String orderDetails = null;

    private String orderStateName;


    public void orderStateNameSet() {
        //0待支付,1已支付,2已发货,3已收货,4已评价,5已取消
        switch (this.orderState) {
            case 0:
                this.orderStateName = "待支付";
                break;
            case 1:
                this.orderStateName = "已支付";
                break;
            case 2:
                this.orderStateName = "已发货";
                break;
            case 3:
                this.orderStateName = "已收货";
                break;
            case 4:
                this.orderStateName = "已评价";
                break;
            case 5:
                this.orderStateName = "已取消";
                break;
        }

    }

    public String getOrderId () {
        return orderId;
    }

    public void setOrderId (String orderId) {
        this.orderId = orderId;
    }

    public String getShareUserId () {
        return shareUserId;
    }

    public void setShareUserId (String shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getOutTradeNo () {
        return outTradeNo;
    }

    public void setOutTradeNo (String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransactionId () {
        return transactionId;
    }

    public void setTransactionId (String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getOrderState () {
        return orderState;
    }

    public void setOrderState (Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getRemind () {
        return remind;
    }

    public void setRemind (Integer remind) {
        this.remind = remind;
    }

    public double getTotalAmount () {
        return totalAmount;
    }

    public void setTotalAmount (double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPayAmount () {
        return payAmount;
    }

    public void setPayAmount (double payAmount) {
        this.payAmount = payAmount;
    }

    public double getRefundAmount () {
        return refundAmount;
    }

    public void setRefundAmount (double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getGoodsNum () {
        return goodsNum;
    }

    public void setGoodsNum (int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getDeliveryId () {
        return deliveryId;
    }

    public void setDeliveryId (String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getMapCoordinateId () {
        return mapCoordinateId;
    }

    public void setMapCoordinateId (String mapCoordinateId) {
        this.mapCoordinateId = mapCoordinateId;
    }

    public String getMapCoordinateName () {
        return mapCoordinateName;
    }

    public void setMapCoordinateName (String mapCoordinateName) {
        this.mapCoordinateName = mapCoordinateName;
    }

    public String getDeliveryName () {
        return deliveryName;
    }

    public void setDeliveryName (String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getUserAddressId () {
        return userAddressId;
    }

    public void setUserAddressId (String userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getReceiverName () {
        return receiverName;
    }

    public void setReceiverName (String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone () {
        return receiverPhone;
    }

    public void setReceiverPhone (String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getProvince () {
        return province;
    }

    public void setProvince (String province) {
        this.province = province;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getCounty () {
        return county;
    }

    public void setCounty (String county) {
        this.county = county;
    }

    public String getReceiverAddress () {
        return receiverAddress;
    }

    public void setReceiverAddress (String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getOrderRemarks () {
        return orderRemarks;
    }

    public void setOrderRemarks (String orderRemarks) {
        this.orderRemarks = orderRemarks;
    }

    public String getTrackingName () {
        return trackingName;
    }

    public void setTrackingName (String trackingName) {
        this.trackingName = trackingName;
    }

    public String getTrackingNum () {
        return trackingNum;
    }

    public void setTrackingNum (String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getPayTime () {
        return payTime;
    }

    public void setPayTime (String payTime) {
        this.payTime = payTime;
    }

    public String getCreateTime () {
        return createTime;
    }

    public void setCreateTime (String createTime) {
        this.createTime = createTime;
    }

    public String getDeliverTime () {
        return deliverTime;
    }

    public void setDeliverTime (String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getFinishTime () {
        return finishTime;
    }

    public void setFinishTime (String finishTime) {
        this.finishTime = finishTime;
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

    public String getIsLease () {
        return isLease;
    }

    public void setIsLease (String isLease) {
        this.isLease = isLease;
    }

    public List<OrderDetail> getOrderDetailGoodsList () {
        return orderDetailGoodsList;
    }

    public void setOrderDetailGoodsList (List<OrderDetail> orderDetailGoodsList) {
        this.orderDetailGoodsList = orderDetailGoodsList;
    }

    public String getOrderDetails () {
        return orderDetails;
    }

    public void setOrderDetails (String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderStateName () {
        return orderStateName;
    }

    public void setOrderStateName (String orderStateName) {
        this.orderStateName = orderStateName;
    }
}
