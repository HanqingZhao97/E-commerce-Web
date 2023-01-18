package com.yunniu.lease.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.model.Admin;
import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Menu;
import com.yunniu.lease.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu/")
public class MenuController {
    @Resource
    private MenuService menuService;

    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public String menu(Model model) {
        model.addAttribute("pMenuList", menuService.getParentMenuList());
        return "menu/menu";
    }

    /**
     * 根据adminID查询菜单列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getMenu", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMenu(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        String adminId = admin.getAdminId();
        JSONObject jo = new JSONObject();
        if (!"".equals(adminId)) {
            jo.put("adminMenu", menuService.getMenuByAdminId("" + adminId));
        } else {
            jo.put("success", false);
            jo.put("msg", "登录状态超时,请重新登录");
        }
        return jo;
    }

    @RequestMapping(value = "getObjById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMenuById(String id) {
        Menu menu = menuService.getMenuById(id);
        JSONObject jo = new JSONObject();
        if (menu != null) {
            jo.put("obj", menu);
            jo.put("success", true);
        } else {
            jo.put("success", false);
            jo.put("msg", "获取数据出错!");
        }
        return jo;

    }

    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getMenuList(ManageParams mps) {
        return menuService.getMenuList(mps);
    }

    @RequestMapping(value = "addOrEdit")
    public String addOrEditMenu(Model model, String id) {
        model.addAttribute("id", id);
        model.addAttribute("pIdList", menuService.getPIdList(id));
        return "menu/addOrEditMenu";
    }

    @RequestMapping(value = "Icon")
    public String Icon(Model model, HttpServletRequest request) {
        return "menu/unicode";
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveMenu(Menu menu) {
        return menuService.saveMenu(menu);
    }

    @RequestMapping(value = "getAllMenu", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getAllMenu() {
        List<Map<String, String>> list = menuService.getAllMenu();
        JSONObject jo = new JSONObject();
        jo.put("allMenu", list);
        return jo;
    }

    @RequestMapping(value = "delById", method = RequestMethod.POST)
    @ResponseBody
    public String deleteById(String ids) {
        return menuService.deleteById(ids);
    }

}