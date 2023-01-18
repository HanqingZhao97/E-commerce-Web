package com.yunniu.lease.controller;


import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.model.User;
import com.yunniu.lease.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping(value = "/list")
    public String goods(Model model) {
        model.addAttribute("userRole", 0);
        return "user/list";
    }

    //分销商list
    @RequestMapping(value = "/retailList")
    public String retail(Model model) {
        model.addAttribute("userRole", 1);
        return "user/list";
    }

    //分销设置
    @RequestMapping(value = "/toDistributor")
    public String distributor(Model model) {
        model.addAttribute("obj", userService.getDistributorInfo());
        return "user/distributor";
    }


    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getGoodsList(HttpServletRequest request) {
        return userService.getUserList(request);
    }


    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserRole(User user) {
        return userService.updateUserRole(user);
    }


    @RequestMapping(value = "/updateDistributor", method = RequestMethod.POST)
    @ResponseBody
    public Result updateDistributor(String minute) {
        return userService.updateDistributor(minute);
    }


}
