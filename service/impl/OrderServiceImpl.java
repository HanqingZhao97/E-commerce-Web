package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.*;
import com.yunniu.lease.model.*;
import com.yunniu.lease.model.Dictionary;
import com.yunniu.lease.service.OrderService;
import com.yunniu.lease.util.Common;
import com.yunniu.lease.util.OfTime;
import com.yunniu.lease.wx.WxUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {


    @Resource
    private GoodsDao goodsDao;
    @Resource
    private UserDao userDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private IndexDao indexDao;
    @Resource
    private GoodsClassDao goodsClassDao;
    @Resource
    private MapCoordinateDao mapCoordinateDao;


    @Override
    public Result addOrder(Order order, OrderDetail orderDetail) {

        String goodsSpecId = orderDetail.getGoodsSpecId();
        if (null != goodsSpecId && goodsSpecId.equals("0") || goodsSpecId.equals("")) {
            orderDetail.setGoodsSpecId(null);
        }

        Goods goods = getGoodsPrice(orderDetail.getGoodsId(), orderDetail.getGoodsSpecId(), orderDetail.getGoodsNum(), orderDetail.getLeaseDay());
        if (null == goods) {
            return new Result(103, "下单失败");
        }
        String orderId = Common.getDateNumber();
        orderDetail.setOrderId(orderId);
        orderDetail.setGoodsName(goods.getGoodsName());
        orderDetail.setGoodsSpecName(goods.getGoodsSpecName());
        orderDetail.setGoodsPrice(goods.getTotalAmount());
        orderDetail.setLeaseDayPrice(goods.getGoodsPrice());
        orderDetail.setGoodsImg(goods.getGoodsIndexImg());
        order.setLeaseDay(orderDetail.getLeaseDay());
        order.setOrderId(orderId);
        UserAddress userAddress = userDao.getUserAddressById(order.getUserAddressId());
        order.setGoodsNum(orderDetail.getGoodsNum());
        order.setReceiverName(userAddress.getReceiverName());
        order.setReceiverPhone(userAddress.getReceiverPhone());
        order.setProvince(userAddress.getProvince());
        order.setCity(userAddress.getCity());
        order.setCounty(userAddress.getCounty());
        order.setReceiverAddress(userAddress.getReceiverAddress());
        String deliveryId = order.getDeliveryId();
        if (null != deliveryId && !deliveryId.equals("")) {
            Dictionary dictionary = goodsClassDao.getDictionaryById(deliveryId);
            if (null == dictionary) {
                return new Result(103, "请选择正确的配送方式");
            }
            if (deliveryId.equals("7")) {
                String mapCoordinateId = order.getMapCoordinateId();
                if (null == mapCoordinateId || mapCoordinateId.equals("") || mapCoordinateId.equals("0")) {
                    return new Result(103, "请选择取货地址");
                }
            }
            order.setDeliveryName(dictionary.getName());
            orderDetail.setDeliveryName(dictionary.getName());
        } else {
            return new Result(103, "请选择配送方式");
        }

        order.setTotalAmount(goods.getTotalAmount());
        order.setIsLease(goods.getIsLease());
        int res1 = orderDao.addOrder(order);//插入订单
        List<OrderDetail> list = new ArrayList<>();
        list.add(orderDetail);
        int res2 = orderDao.addOrderDetail(list);//插入订单明细


        if (res1 < 1 || res2 < 1) {
            throw new RuntimeException();
        }
//        List<OrderDetailGoods> orderDetailList = orderDao.getOrderDetailList(orderId);
        order.setOrderDetailGoodsList(list);
//        int min = indexDao.getSettingById(1);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Order order1 = orderDao.getOrderById(orderId);
//                if (order1.getOrderState() == 0) {
//                    //取消订单,释放库存
//                    orderDao.cancelOrder0(orderId);
////                    orderDao.backGoodsSpec(orderId);
////                    orderDao.backGoodsStock(orderId);//更新商品库存
////                    goodsDao.updateGoodsStockBygoodsId(orderDetail.getGoodsId());//更新商品库存
//                }
//
//            }
//
//        }, 1000 * 60 * min);
        return new Result(order);
    }


    @Override
    public Result addCartOrder(Order order, String userCartId) {
        String orderId = Common.getDateNumber();
        List<OrderDetail> list = userDao.getUserCartGoodsDetail(userCartId);
        if (list.size() < 1) {
            return new Result(102);
        }
        int num = 0;
        Double totalAmount = 0d;
        String isLease;
//        Integer res3 = 1;
//        Integer res4 = 1;
        for (int i = 0; i < list.size(); i++) {
            OrderDetail orderDetail = list.get(i);
            String goodsSpecId = orderDetail.getGoodsSpecId();
            if (null != goodsSpecId && goodsSpecId.equals("0") || goodsSpecId.equals("")) {
                orderDetail.setGoodsSpecId(null);
            }
            Goods goods = getGoodsPrice(orderDetail.getGoodsId(), goodsSpecId, orderDetail.getGoodsNum(), orderDetail.getLeaseDay());
            isLease = goods.getIsLease();
            if (null != isLease && isLease.equals("1")) {
//                OrderDetail orderDetail1 = new OrderDetail();
//                Order order1 = new Order();
//                orderDetail1.setOrderId(orderDetail.getGoodsId());
//                orderDetail1.setGoodsSpecId(orderDetail.getGoodsSpecId());
//                orderDetail1.setGoodsNum(orderDetail.getGoodsNum());
//                orderDetail1.setLeaseDay(orderDetail.getLeaseDay());
                Result result = addOrder(order, orderDetail);
                if (null == result || result.getCode() != 100) {
                    return result;
                } else {
                    list.remove(i);
                    if (list.size() < 1) {
                        return result;
                    }
                    i--;
                    continue;
                }
            }
//            if (orderDetail.getGoodsSpecId() == null) {
//                if (goods.getGoodsStock() < orderDetail.getGoodsNum()) {
//                    return new Result(104, "下单失败,商品库存不足!");
//                }
//                price = goods.getGoodsPrice();
//                if (goods.getIsLease().equals("1")) {
//                    deposit += goods.getDeposit();
//                }
//                Goods goods1 = new Goods();
//                goods1.setGoodsId(orderDetail.getGoodsId());
//                goods1.setGoodsStock(goods.getGoodsStock() - orderDetail.getGoodsNum());
//                res4 = goodsDao.updateGoods(goods1);//更新商品库存
//            } else {
//                GoodsSpec goodsSpec = goodsDao.getGoodsSpecById(orderDetail.getGoodsSpecId(), goods.getGoodsId());
//                if (goodsSpec.getGoodsSpecStock() < orderDetail.getGoodsNum()) {
//                    return new Result(104, "下单失败,商品库存不足!");
//                }
//                if (goods.getIsLease().equals("1")) {
//                    deposit += goodsSpec.getDeposit();
//                }
//                price = goodsSpec.getGoodsPrice();
////                res3 = goodsDao.reduceGoodsSpecStock(orderId);//减少库存
//                res3 = goodsDao.updateGoodsStockByGoodsSpecId(goodsSpec.getGoodsSpecId(), orderDetail.getGoodsNum());
//                GoodsSpec goodsSpec1 = new GoodsSpec();
//                res4 = goodsDao.updateGoodsStockBygoodsId(orderDetail.getGoodsId());//更新商品库存
//            }
//            if (res3 < 1 || res4 < 1) {
//                throw new RuntimeException();
//            }
            String deliveryId = order.getDeliveryId();
            if (null != deliveryId && !deliveryId.equals("")) {
                Dictionary dictionary = goodsClassDao.getDictionaryById(deliveryId);
                if (null == dictionary) {
                    return new Result(103, "请选择正确的配送方式");
                }
                if (deliveryId.equals("7")) {
                    String mapCoordinateId = order.getMapCoordinateId();
                    if (null == mapCoordinateId || mapCoordinateId.equals("") || mapCoordinateId.equals("0")) {
                        return new Result(103, "请选择取货地址");
                    }
                }
                order.setDeliveryName(dictionary.getName());
                orderDetail.setDeliveryName(dictionary.getName());
            } else {
                return new Result(103, "请选择配送方式");
            }
            orderDetail.setGoodsName(goods.getGoodsName());
            orderDetail.setGoodsSpecName(goods.getGoodsSpecName());
            orderDetail.setGoodsPrice(goods.getTotalAmount());
            orderDetail.setLeaseDayPrice(goods.getGoodsPrice());
            orderDetail.setGoodsImg(goods.getGoodsIndexImg());
            list.get(i).setOrderId(orderId);
            list.set(i, orderDetail);
            num += orderDetail.getGoodsNum();
            totalAmount += (goods.getTotalAmount());
        }
        order.setIsLease("0");
        order.setOrderId(orderId);
        order.setGoodsNum(num);
        UserAddress userAddress = userDao.getUserAddressById(order.getUserAddressId());
        order.setReceiverName(userAddress.getReceiverName());
        order.setReceiverPhone(userAddress.getReceiverPhone());
        order.setProvince(userAddress.getProvince());
        order.setCity(userAddress.getCity());
        order.setCounty(userAddress.getCounty());
        order.setReceiverAddress(userAddress.getReceiverAddress());
        order.setTotalAmount(totalAmount);
        int res1 = orderDao.addOrder(order);//插入订单
        int res2 = orderDao.addOrderDetail(list);//插入订单明细
//        int res5 = userDao.delCartGoods(userCartId);
        int res5 = 3;
        if (res1 < 1 || res2 < 1 || res5 < 1) {
            throw new RuntimeException();
        }
//        List<OrderDetailGoods> orderDetailList = orderDao.getOrderDetailList(orderId);
//        order.setOrderDetailGoodsList(orderDetailList);
        order.setOrderDetailGoodsList(list);
//        int min = indexDao.getSettingById(1);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Order order1 = orderDao.getOrderById(orderId);
//                if (order1.getOrderState() == 0) {
//                    //取消订单,释放库存
//                    orderDao.cancelOrder0(orderId);
////                    orderDao.backGoodsSpec(orderId);
////                    orderDao.backGoodsStock(orderId);//更新商品库存
//                }
//
//            }
//
//        }, 1000 * 60 * min);
        return new Result(order);
    }


    @Override
    public TableResult getOrderList(HttpServletRequest request) {
        Map<String, Object> map = Pages.getParams(request);
        int count = orderDao.getOrderListCount(map);
        List<Order> list = orderDao.getOrderList(map);
        if (list.size() > 0) {
            String ids = "";
            for (int i = 0; i < list.size(); i++) {
                ids += list.get(i).getOrderId() + ",";
            }
            List<OrderDetail> list1 = orderDao.getOrderDetailList(ids);
            for (int m = 0; m < list.size(); m++) {
                for (int n = 0; n < list1.size(); n++) {
                    if (list.get(m).getOrderId().equals(list1.get(n).getOrderId())) {
                        OrderDetail orderDetailGoods = list1.get(n);
                        list.get(m).getOrderDetailGoodsList().add(orderDetailGoods);
//                        String str = list1.get(n).getGoodsName() + " | " + list1.get(n).getGoodsSpecName() + " | " + list1.get(n).getGoodsNum();
                        String goodsSpecName = orderDetailGoods.getGoodsSpecName();
                        if (null == goodsSpecName) {
                            goodsSpecName = "默认";
                        }
                        String str = orderDetailGoods.getGoodsName() + " | " + goodsSpecName + " | " + orderDetailGoods.getGoodsNum();
                        if (list.get(m).getOrderDetails() == null) {
                            list.get(m).setOrderDetails(str);
                        } else {
                            list.get(m).setOrderDetails(list.get(m).getOrderDetails() + "<span style=\"color:green;\">   &&  </span>" + str);
                        }
                    }
                }
            }
        }
        return new TableResult(count, list);
    }

    @Override
    public Result getAppOrderList(HttpServletRequest request) {
        Map<String, Object> map1 = Pages.getParams(request);
        List<Order> list = orderDao.getOrderList(map1);
        List<Order> orderList = orderDao.getOrderList(map1);
        if (list.size() > 0) {
            String ids = "";
            for (int i = 0; i < list.size(); i++) {
                ids += list.get(i).getOrderId() + ",";
            }
//            List<OrderDetailGoods> orderDetailGoodsList = orderDao.getOrderDetailListByIds(ids);
            List<OrderDetail> orderDetailList = orderDao.getOrderDetailList(ids);
            for (int m = 0; m < list.size(); m++) {
                Order order = list.get(m);
                if (null != order.getDeliveryId() && order.getDeliveryId().equals("7")) {
                    MapCoordinate mapCoordinateInfo = mapCoordinateDao.getMapCoordinateInfo(order.getMapCoordinateId());
                    if (null != mapCoordinateInfo) {
                        order.setMapCoordinateName(mapCoordinateInfo.getAddress());
                        list.remove(m);
                        list.add(m, order);
                    }
                }
                for (int n = 0; n < orderDetailList.size(); n++) {
                    if (list.get(m).getOrderId().equals(orderDetailList.get(n).getOrderId())) {
                        list.get(m).getOrderDetailGoodsList().add(orderDetailList.get(n));
                    }
                }
            }
        }
        return new Result(list);
    }

    @Override
    public Result cancelOrder(Order order1) {
        Order order = orderDao.getOrderById(order1.getOrderId());
        if (order.getUserId() != order1.getUserId()) {
            return new Result(102);
        }
        if (order.getOrderState() == 0) {
            int res = orderDao.cancelOrder0(order1.getOrderId());
            if (res < 1) {
                return new Result(103);
            }
        } else if (order.getOrderState() == 1) {
            int res = orderDao.cancelOrder1(order1.getOrderId());
            if (res < 1) {
                return new Result(103);
            }
            //退款打款
//            try {
//                boolean b = WxUtil.refund(order);
//                if (!b) {
//                    throw new RuntimeException();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException();
//            }
        } else {
            return new Result(102);
        }
        return new Result();
    }

//    @Override
//    public Result getOrderById(String orderId) {
//        Order order = orderDao.getOrderById(orderId);
//        List<OrderDetailGoods> orderDetailGoodsList = orderDao.getOrderDetailListByIds(orderId);
//        List<Map<String, Object>> list = orderDao.getOrderDetailCommentState(orderId);
//        for (int i = 0; i < orderDetailGoodsList.size(); i++) {
//            for (int j = 0; j < list.size(); j++) {
//                if (orderDetailGoodsList.get(i).getOrderDetailId().equals(list.get(j).getOrderDetailId())) {
//                    if (list.get(j).getCommentId() != null && !"".equals(list.get(j).getCommentId())) {
//                        orderDetailGoodsList.get(i).setCommentState(1);
//                    } else {
//                        orderDetailGoodsList.get(i).setCommentState(0);
//                    }
//                }
//            }
//        }
//        order.setOrderDetailGoodsList(orderDetailGoodsList);
//        if (order.getOrderState() == 2 || order.getOrderState() == 3) {
//            order.setTraceList(DepponUtil.newTraceQuery(order.getTrackingNum()));
//        }
//        return new Result(order);
//    }


    @Override
    public Order getOrderById(String orderId) {
        Order order = orderDao.getOrderById(orderId);
//        List<OrderDetailGoods> orderDetailGoodsList = orderDao.getOrderDetailListByIds(orderId);
        List<OrderDetail> orderDetailGoodsList = orderDao.getOrderDetailList(orderId);
        for (int i = 0; i < orderDetailGoodsList.size(); i++) {
            OrderDetail orderDetailGoods = orderDetailGoodsList.get(i);
        }
        if (null != order.getDeliveryId() && order.getDeliveryId().equals("7")) {
            MapCoordinate mapCoordinateInfo = mapCoordinateDao.getMapCoordinateInfo(order.getMapCoordinateId());
            if (null != mapCoordinateInfo) {
                order.setMapCoordinateName(mapCoordinateInfo.getAddress());
            }
        }
        order.setOrderDetailGoodsList(orderDetailGoodsList);
        order.orderStateNameSet();
        return order;
    }

    @Override
    public RefundDeposit getRefundInfo(String orderId) {
        RefundDeposit refundInfo = orderDao.getRefundInfo(orderId);
        Double rent = refundInfo.getGoodsNum() * refundInfo.getLeaseDayPrice() * refundInfo.getLeaseDay();
        Double refundAmount = refundInfo.getGoodsPrice() - rent;
        if (refundAmount < 0) {
            refundInfo.setRefundAmount(0.0);
        } else {
            refundInfo.setRefundAmount(refundInfo.getGoodsPrice() - rent);
        }
        return refundInfo;
    }


    @Override
    public Result finishOrder(Integer userId, String orderId) {
        return null;
    }

    @Override
    public Result delOrder(String orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (null == order.getOrderState() || order.getOrderState() != 0) {
            return new Result("不是未支付的订单").error();
        }

        int res = orderDao.delOrderDetail(orderId);
        int res1 = orderDao.delOrder(orderId);
        if (res > 0 && res1 > 0) {
            return new Result();
        }
        new RuntimeException();
        return new Result().error();
    }

    @Override
    public Result remind(String orderId) {
        Order order = orderDao.getOrderById(orderId);
        if (null == order.getOrderState() || order.getOrderState() != 1) {
            return new Result(103, "不是待发货的订单");
        }
        Integer remind = orderDao.remind(orderId);
        return new Result();
    }

    @Override
    public Result tracking(String orderId, String trackingNum) {
        int res = orderDao.tracking(orderId, trackingNum);//发货
        if (res >= 1) {
            return new Result("发货成功");
        }
        return new Result(103);
    }

    @Override
    public Result receiving(Order order) {
//        Order order = orderDao.getOrderById(orderId);
        if (order.getOrderState() == 4) {
            int res = orderDao.receiving(order);//收货
            if (res >= 1) {
                return new Result("订单已完成");
            }
        } else if (order.getOrderState() == 3) {
            Order orderInfo = orderDao.getOrderById(order.getOrderId());
            try {
                String shortTime = OfTime.getShortTime();
                String date = OfTime.increaseTheTime(shortTime, "date", orderInfo.getLeaseDay());
                order.setLeaseStartDate(shortTime);
                order.setLeaseEndDate(date);
                int res = orderDao.receiving(order);//收货  租赁中
                if (res >= 1) {
                    return new Result("租赁中到期时间:" + date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return new Result(103, "租赁日期计算失败");
            }
        } else {
            return new Result(103, "订单异常");
        }
        return new Result(103);
    }

    @Override
    public Result refund(Order order) {
        Order order1 = orderDao.getOrderById(order.getOrderId());
        Double payAmount = order1.getPayAmount();
        Double refundAmount = order.getRefundAmount();
//        if (null != refundAmount && refundAmount > 0 && refundAmount < payAmount) {
//            try {
//                Boolean boo = WxUtil.refund(order1);
//                if (boo) {
//                    return new Result(100, "退款成功");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return new Result(103, "退款失败");
//            }
//        }
        return new Result(103, "退款失败");
    }


    public Goods getGoodsPrice(String goodsId, String specId, Integer goodsNum, Integer leaseDay) {
        Goods goods = goodsDao.getGoodsById(goodsId);
        GoodsSpec goodsSpec = null;
        Double goodsPrice = 0.0;
        if (specId != null && !specId.equals("")) {
            goodsSpec = goodsDao.getGoodsSpecById(specId, goodsId);
            if (null == goodsSpec) {
                return null;
            }
            goods.setGoodsSpecName(goodsSpec.getGoodsSpecName());
            goods.setGoodsIndexImg(goodsSpec.getGoodsSpecImg());
//            if (goodsSpec.getGoodsSpecStock() < goodsNum) {
////                return new Result(104, "下单失败,商品库存不足!");
//                return null;
//            }
        }
//        else if (goods.getGoodsStock() < goodsNum) {
////                return new Result(104, "下单失败,商品库存不足!");
//            return null;
//        }
        if (goodsNum < 1) {
            return null;
        }
        if (goods.getIsLease().equals("1")) {
            Integer minDay = indexDao.getSettingById(4);
            if (null == leaseDay || leaseDay < minDay) {
                return null;
            }

            if (goodsSpec == null) {
//                goodsPrice = goods.getGoodsPrice() * goodsNum * leaseDay + goods.getDeposit();
                goodsPrice = goodsNum * goods.getDeposit();
            } else {
//                goodsPrice = goodsSpec.getGoodsPrice() * goodsNum * leaseDay + goodsSpec.getDeposit();
                goodsPrice = goodsNum * goodsSpec.getDeposit();
            }
        } else {
            if (goodsSpec == null) {
                goodsPrice = goods.getGoodsPrice() * goodsNum;
            } else {
                goodsPrice = goodsSpec.getGoodsPrice() * goodsNum;
            }
        }
        goods.setTotalAmount(goodsPrice);
        goods.setGoodsPrice(goods.getGoodsPrice());
        return goods;
    }


}
