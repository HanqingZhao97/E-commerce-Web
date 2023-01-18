package com.yunniu.lease.service;

import com.yunniu.lease.model.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface OrderService {
    Result addOrder(Order order, OrderDetail orderDetail);

    TableResult getOrderList(HttpServletRequest request);

    Result getAppOrderList(HttpServletRequest request);

    Result cancelOrder(Order order);

    Order getOrderById(String orderId);

    RefundDeposit getRefundInfo(String orderId);

    Result addCartOrder(Order order, String userCartId);

    Result finishOrder(Integer userId, String orderId);

    Result delOrder(String orderId);

    Result remind(String orderId);

    Result tracking(String orderId, String trackingNum);

    Result receiving(Order order);

    Result refund(Order order);


    Goods getGoodsPrice(String goodsId, String specId, Integer goodsNum, Integer leaseDay);

}
