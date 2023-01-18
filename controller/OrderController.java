package com.yunniu.lease.controller;

import com.yunniu.lease.model.Order;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;


    @RequestMapping(value = "/list")
    public String order(Model model) {
        model.addAttribute("orderState", 1);
        return "order/list";
    }


    @RequestMapping(value = "/orderDetail")
    public String orderDetail(Model model, String orderId) {
        model.addAttribute("obj", orderService.getOrderById(orderId));
        return "order/orderDetail";
    }


    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getOrderList(HttpServletRequest request) {
        return orderService.getOrderList(request);
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Result delOrder(String id) {
        return orderService.delOrder(id);
    }

//    /**
//     * 导出订单
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/exportOrder")
//    public void exportOrder(HttpServletRequest request, HttpServletResponse response) {
//        orderService.exportOrder(request, response);
//    }


    @RequestMapping(value = "/toDeliver")
    public String toDeliver(Model model, String orderId) {
        model.addAttribute("obj", orderService.getOrderById(orderId));
        return "order/tracking";
    }

    /**
     * 发货
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/tracking", method = RequestMethod.POST)
    @ResponseBody
    public Result tracking(String orderId, String trackingNum) {
        return orderService.tracking(orderId, trackingNum);
    }


    /**
     * 收货
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/receiving", method = RequestMethod.POST)
    @ResponseBody
    public Result receiving(Order order) {
        return orderService.receiving(order);
    }


    //查询退还金额
    @RequestMapping(value = "/toRefund")
    public String toRefund(Model model, String orderId) {
        model.addAttribute("obj", orderService.getRefundInfo(orderId));
        return "order/refund";
    }


    /**
     * 退款
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public Result refund(Order order) {
        return orderService.refund(order);
    }


}
