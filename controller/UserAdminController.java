package com.yunniu.lease.controller;

import com.yunniu.lease.model.*;
import com.yunniu.lease.service.UserAdminService;
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

/**
 * @author
 * @date
 */

@Controller
@RequestMapping("/userAdmin")
public class UserAdminController {
    @Resource
    private UserAdminService userAdminService;
    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserRole(UserAdmin userAdmin) {
        Result result = null;
        Integer info = userAdminService.insertUserService(userAdmin);
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
    public TableResult userAdminPageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        System.out.println("---------------");
        System.out.println("---------------");

        List<UserAdmin> list = userAdminService.getUserService();
        System.out.println("查询出来的数据="+list.toString());
        //List<Business> list= businessService.selectByPrimarys(params);
        //Integer count= businessService.pageCount(params);
        TableResult tableResult = new TableResult(1, list);

        return tableResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Integer add(@RequestBody UserAdmin userAdmin){
        Integer info=0;
        System.out.println(userAdmin.toString());
        System.out.println(userAdmin.toString());
        info=userAdminService.insertUserService(userAdmin);
        return info;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteUser(@RequestBody UserAdmin user) {
        Integer info = userAdminService.deleteUserService(user.getId());
        return info;
    }

    @RequestMapping(value = "/updateUserAdmin", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserAdmin(UserAdmin userAdmin) {
        Result result = null;
        Integer info = userAdminService.updateUserService(userAdmin);
        if(info==1){
            result = new Result(100, "success", info);
        }else{
            result = new Result(200, "fail", info);
        }
        return result;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String userAdminList(
            Model model, HttpServletRequest request){
        return "user_admin/userAdmin_list";
    }

    //用户添加路径
    @RequestMapping(value = "/userAddUrl",method = RequestMethod.GET)
    public String userAddUrl(Model model, HttpServletRequest request){

        return "user_admin/userAdmin_add";
    }

    //用户修改路径
    @RequestMapping(value = "/userUpdate",method = RequestMethod.GET)
    public String userUpdate(Model model, HttpServletRequest request){
        //接受前端发过来的ID，可通过ID找出当前ID对应的数据
        String id = request.getParameter("id");
        UserAdmin userAdmin = userAdminService.getUserID(Integer.parseInt(id));
        //可以通过model将查询到的对象带入到相对应的界面
        model.addAttribute("user",userAdmin);
        return "user_admin/userAdmin_edit";
    }

}
