package com.yunniu.lease.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Role;
import com.yunniu.lease.service.MenuService;
import com.yunniu.lease.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色管理
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    /**
     * 首页
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/role")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "role/role";
    }

    @RequestMapping(value = "/addOrEdit")
    public String addOrEditRole(Model model, String id) {
        model.addAttribute("id", id);
        JSONObject jo = new JSONObject();
        jo.put("menu", menuService.getAllMenu());
        model.addAttribute("allMenu", jo.toString());
        return "role/addOrEditRole";
    }


    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getRoleList(ManageParams mps) {
        return roleService.getRoleList(mps);
    }


    @RequestMapping(value = "/getObjById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getRoleList(String id) {
        JSONObject jo = new JSONObject();
        Role role = roleService.getRoleById(id);
        if (role != null) {
            jo.put("success", true);
            jo.put("obj", role);
        } else {
            jo.put("success", false);
            jo.put("msg", "获取信息失败!");
        }
        return jo;
    }

    /**
     * 保存角色
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveRole(Role role) {
        return roleService.saveRole(role);
    }

    @RequestMapping(value = "/delById", method = RequestMethod.POST)
    @ResponseBody
    public String delRoleById(String ids) {
        return roleService.delRoleById(ids);
    }

}