package com.yunniu.lease.controller;

import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.model.UserAskLog;
import com.yunniu.lease.service.UserRebateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/userRebate/")
public class UserRebateController {


    @Resource
    private UserRebateService userRebateService;


    @RequestMapping(value = "/list")
    public String goods(Model model) {
        model.addAttribute("userRole", 0);
        return "userRebate/list";
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getGoodsList(HttpServletRequest request) {
        return userRebateService.getUserRebateList(request);
    }


    //更新提现状态   1已提现  2已拒绝
    @RequestMapping(value = "/updateUserAskLogType", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserAskLogType(UserAskLog userAskLog) {
        return userRebateService.updateUserAskLogType(userAskLog);
    }


}
