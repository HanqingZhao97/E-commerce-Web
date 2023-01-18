package com.yunniu.lease.controller;

import com.yunniu.lease.model.*;
import com.yunniu.lease.service.OrderMainService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderMain")
public class OrderMainController {
    @Resource
    private OrderMainService orderMainService;
    @RequestMapping(value = "/updateOrderMain", method = RequestMethod.POST)
    @ResponseBody
    public Result updateOrderMain(OrderMain orderMain) {
        Result result = null;
        Integer info = orderMainService.addOrderMainService(orderMain);
        if(info==1){
            result = new Result(100, "success", info);
        }else{
            result = new Result(200, "fail", info);
        }
        return result;
    }
    //分页查询
    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult orderMainPageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        System.out.println("---------------");
        System.out.println("---------------");

        List<OrderMain> list = orderMainService.getOrderMainService();
        System.out.println("查询出来的数据="+list.toString());

        TableResult tableResult = new TableResult(1, list);

        return tableResult;
    }

    //连表查询
    @RequestMapping(value = "/getFullPageList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult OrderMainFullList(){
        List<OrderMain> list = orderMainService.getOrderMainListService();
        TableResult tableResult = new TableResult(200,list,"success");
        return tableResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Integer add(@RequestBody OrderMain orderMain){
        Integer info=0;
        System.out.println(orderMain.toString());
        System.out.println(orderMain.toString());
        info=orderMainService.addOrderMainService(orderMain);
        return info;
    }

    @RequestMapping(value = "/deleteOrderMain", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteUser(@RequestBody UserAdmin user) {
        Integer info = orderMainService.deleteOrderMainService(user.getId());
        return info;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String orderMainList(
            Model model, HttpServletRequest request){
        return "orderMain/orderMain_list";
    }

    //用户添加路径
    @RequestMapping(value = "/orderMainAddUrl",method = RequestMethod.GET)
    public String orderMainAddUrl(Model model, HttpServletRequest request){

        return "orderMain/orderMain_add";
    }

    //用户修改路径
    @RequestMapping(value = "/orderMainUpdate",method = RequestMethod.GET)
    public String orderMainUpdate(Model model, HttpServletRequest request){
        //接受前端发过来的ID，可通过ID找出当前ID对应的数据
        String id = request.getParameter("id");
        OrderMain orderMain = orderMainService.getOrderMainID(Integer.parseInt(id));
        //可以通过model将查询到的对象带入到相对应的界面
        model.addAttribute("orderMain",orderMain);
        return "orderMain/orderMain_edit";
    }

}