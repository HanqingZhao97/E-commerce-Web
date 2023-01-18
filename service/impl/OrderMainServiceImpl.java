package com.yunniu.lease.service.impl;
import com.yunniu.lease.dao.OrderMainMapper;
import com.yunniu.lease.model.OrderMain;
import com.yunniu.lease.service.OrderMainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hanqing Zhao
 * @date 2022/09/05
 */

@Service
public class OrderMainServiceImpl implements OrderMainService {

    @Resource
    //Resource 帮忙 new 一个 object
    private OrderMainMapper orderMainMapper;

    @Override
    public List<OrderMain> getOrderMainService() {
        List<OrderMain> orderMains = orderMainMapper.getOrderMain();
        return orderMains;
    }

    @Override
    public List<OrderMain> getOrderMainListService() {
        List<OrderMain> list = orderMainMapper.findPageList();
        return list;
    }

    @Override
    public Integer addOrderMainService(OrderMain OrderMain) {
        //1 means success, 0 means fail
        Integer info = orderMainMapper.addOrderMain(OrderMain);
        return info;
    }

    @Override
    public Integer deleteOrderMainService(Integer id) {
        Integer info = orderMainMapper.deleteOrderMain(id);
        return info;
    }

    @Override
    public Integer updateOrderMainService(OrderMain OrderMain) {
        Integer info = orderMainMapper.updateOrderMain(OrderMain);
        return info;
    }

    @Override
    public OrderMain getOrderMainID(Integer id) {
        OrderMain orderMain = orderMainMapper.getOrderMainID(id);
        return orderMain;
    }
}
