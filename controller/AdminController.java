package com.yunniu.lease.controller;

import com.yunniu.lease.model.Admin;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.AdminService;
import com.yunniu.lease.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;


    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/list")
    public String admin(Model model) {
        model.addAttribute("roleList", roleService.getAllRoleList());
        return "admin/list";
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getAdminList(HttpServletRequest request) {
        return adminService.getAdminList(request);
    }

    @RequestMapping(value = "/toAdd")
    public String addAdmin(Model model) {
        model.addAttribute("roleList", roleService.getAllRoleList());
        return "admin/add";
    }

    @RequestMapping(value = "/toEdit")
    public String editAdmin(Model model, String id) {
        model.addAttribute("obj", adminService.getAdminById(id));
        model.addAttribute("roleList", roleService.getAllRoleList());
        return "admin/edit";
    }

    @RequestMapping(value = "/adminInfo")
    public String adminDetail(Model model) {
        Admin user = (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        model.addAttribute("obj", adminService.getAdminById(user.getAdminId()));
        return "admin/adminInfo";
    }

    @RequestMapping(value = "/adminEdit")
    public String adminEdit(Model model, String adminId) {
        model.addAttribute("obj", adminService.getAdminById(adminId));
        return "admin/adminInfo";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result addAdmin(Admin admin) {
        return adminService.addAdmin(admin);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result editAdmin(Admin admin) {
        return adminService.editAdmin(admin);
    }

    @RequestMapping(value = "/editAdminInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result editAdminInfo(Admin admin) {
        return adminService.editAdminInfo(admin);
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Result delAdmin(String id) {
        return adminService.delAdmin(id);
    }

    @RequestMapping(value = "/abolish", method = RequestMethod.POST)
    @ResponseBody
    public Result abolishAdmin(Admin admin) {
        return adminService.abolishAdmin(admin);
    }


}
