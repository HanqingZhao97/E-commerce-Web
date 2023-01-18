package com.yunniu.lease.controller;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.model.Admin;
import com.yunniu.lease.result.LoginException;
import com.yunniu.lease.service.AdminService;
import com.yunniu.lease.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 登录
 *
 * @author Administrator
 */
@Controller
public class LoginController {
    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/login")
    public String login0(Model model) {
        return "login";
    }

    @RequestMapping(value = "/")
    public String indx(Model model) {
        Subject currentAdmin = SecurityUtils.getSubject();
        Admin user = (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        if (!currentAdmin.isAuthenticated() || user == null) {
            return "redirect:login";
        } else {
            return "redirect:index";
        }
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        try {
            Admin user = (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
            model.addAttribute("admin", adminService.getAdminById(user.getAdminId()));
            return "index";
        } catch (LoginException e) {
            return "redirect:login";
        }

    }


    /**
     * 登录判断
     */
    @RequestMapping(value = "/confirmUser", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(String account, String password) {
        JSONObject jo = new JSONObject();
        Subject currentAdmin = SecurityUtils.getSubject();
        Admin user = (Admin) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
        if (!currentAdmin.isAuthenticated() || user == null) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            try {
                // 登录认证 - 调用userRealm
                currentAdmin.login(token);
                jo.put("success", true);
                adminService.updateLoginTime(account);
            } catch (IncorrectCredentialsException ice) {
                jo.put("msg", "密码错误!");
                jo.put("success", false);
                // hrow new IncorrectCredentialsException("密码错误！");
            } catch (AuthenticationException ae) {
                jo.put("msg", "账号不存在或已停用!!");
                jo.put("success", false);
            }
        } else {
            jo.put("success", true);
        }
        return jo.toString();
    }

}
