package com.yunniu.lease.controller;

import com.yunniu.lease.model.Admin;
import com.yunniu.lease.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class WelcomeController {

    @Resource
    private AdminService adminService;

    @RequestMapping(value = "/welcome")
    public String welcome(Model model) {
        Admin user = (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        model.addAttribute("admin", adminService.getAdminById(user.getAdminId()));
        return "welcome";
    }


}
