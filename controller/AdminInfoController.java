package com.yunniu.lease.controller;

import cn.hutool.core.date.DateTime;
import com.yunniu.lease.dao.AdminInfoMapper;
import com.yunniu.lease.dao.EquipmentMapper;
import com.yunniu.lease.dao.EquipmentUserMapper;
import com.yunniu.lease.dao.UserInfoMapper;
import com.yunniu.lease.model.*;
import com.yunniu.lease.util.COSUtil;
import com.yunniu.lease.util.FileUtil;
import com.yunniu.lease.util.ImageUtil;
import com.yunniu.lease.util.UUIDUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/27 14:44
 */
@Controller
@RequestMapping("/adminfo")
public class AdminInfoController {
    @Resource
    private AdminInfoMapper adminInfoMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    
    @Resource
    private EquipmentUserMapper equipmentUserMapper;

    //信息发布页面跳转
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request){
        return "admininfo/list";
    }

    //信息发布页面跳转
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request){
        return "admininfo/add";
    }


    //信息发布页面跳转
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String eadit(Model model, HttpServletRequest request){
        return "admininfo/edit";
    }



    //群发
    @RequestMapping(value = "/selectinfo",method = RequestMethod.GET)
    public String selectinfo(Model model, HttpServletRequest request){
        String id=request.getParameter("id");
        model.addAttribute("id",id);
        return "admininfo/equipment_user_list";
    }



    //添加商家
    @RequestMapping(value = "/addAdminInfo",method = RequestMethod.POST)
    @ResponseBody
    public Result adminInfo(AdminInfo adminInfo){
       
        Result result=null;
        Integer info=0;
        if(adminInfo.getId()!=null){
            info=adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
        }else{
            adminInfo.setCreatetime(new Date());
            info=adminInfoMapper.insertSelective(adminInfo); 
        }
        if(info==1){
            result=new Result(200,"成功",info);
        }else{
            result=new Result(100,"失败",info);
        }
        return result;
    }

    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        List<AdminInfo> list= adminInfoMapper.pageList(params);
        Integer count= adminInfoMapper.count(params);
        TableResult tableResult = new TableResult(count, list);
        return tableResult;
    }

    @RequestMapping(value = "/getadminInfo",method=RequestMethod.GET)
    public String getBusiness(Model model, HttpServletRequest request){

        String sid=request.getParameter("id");
        Integer id=null;
        if(sid!=null){
            id=Integer.parseInt(sid);
        }
        AdminInfo adminInfo=adminInfoMapper.selectByPrimaryKey(id);
        model.addAttribute("adminInfo",adminInfo);
        return "admininfo/edit";
    }

    @RequestMapping(value = "/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result delte(@RequestBody Integer [] ids){
        Result result=null;
        Integer info=null;
        if(ids.length>1){
            for(int i=0;i<ids.length;i++){
                info= adminInfoMapper.deleteByPrimaryKey(ids[i]);
            }
        }else{
            Integer id=ids[0];
            info= adminInfoMapper.deleteByPrimaryKey(id);
        }

        if(info==1){
            result=new Result(200,"成功",info);
        }else{
            result=new Result(100,"失败",info);
        }
        return result;
    }

    //群发
    @RequestMapping(value = "/insertIds",method = RequestMethod.POST)
    @ResponseBody
    public Result insertUserInfo(@RequestBody Integer [] ids){
        Result result=null;
        Integer info=null;

        for(int i=1;i<ids.length;i++){
            info= userInfoMapper.insert(new UserInfo(ids[i],ids[0],0,new Date()));
        }

        if(ids[0]!=null){
            AdminInfo adminInfo=new AdminInfo();
            adminInfo.setId(ids[0]);
            adminInfo.setIstate(1);
            adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
        }

        if(info==1){
            result=new Result(200,"成功",info);
        }else{
            result=new Result(100,"失败",info);
        }

        return result;
    }
    
    
    //全发
    @RequestMapping(value = "/insertUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Result insertUserInfo(@RequestBody Integer id){
        Result result=null;
        Integer info=null;

        List<EquipmentUser> userList=equipmentUserMapper.getList();
        
        for(int i=0;i<userList.size();i++){
           info= userInfoMapper.insert(new UserInfo(userList.get(i).getId(),id,0,new Date()));
        }
        
        if(id!=null){
            AdminInfo adminInfo=new AdminInfo();
            adminInfo.setId(id);
            adminInfo.setIstate(1);
            adminInfoMapper.updateByPrimaryKeySelective(adminInfo);
        }
        
        if(info==1){
            result=new Result(200,"成功",info);
        }else{
            result=new Result(100,"失败",info);
        }
    
        return result;
    }

}
