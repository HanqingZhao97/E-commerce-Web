package com.yunniu.lease.service;

import com.yunniu.lease.model.OrderMain;

import java.util.List;

public interface OrderMainService {
    //1.查询的service
    public List<OrderMain> getOrderMainService();

    //连表查询的service
    public List<OrderMain> getOrderMainListService();

    //2.插入的service
    public Integer addOrderMainService(OrderMain orderMain);

    //3.删除的service
    public Integer deleteOrderMainService(Integer id);

    //4.修改的service
    public Integer updateOrderMainService(OrderMain orderMain);

    //5.根据当前ID查询对应的数据
    public OrderMain getOrderMainID(Integer id);

}
