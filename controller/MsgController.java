package com.yunniu.lease.controller;

import com.yunniu.lease.dao.EquipmentMapper;
import com.yunniu.lease.dao.EquipmentUserMapper;
import com.yunniu.lease.dao.MsgMapper;
import com.yunniu.lease.model.*;
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
 * @date 2022/6/11 14:03
 */
@Controller
@RequestMapping("/msg")
public class MsgController {
    
    @Resource
    MsgMapper msgMapper;//信息
    
    @Resource
    EquipmentUserMapper equipmentUserMapper;//设备用户
    
    @Resource
    EquipmentMapper equipmentMapper;//设备

    //列表页面跳转
    @RequestMapping(value = "/msglist",method = RequestMethod.GET)
    public String msglist(Model model, HttpServletRequest request){
        return "msg/msg_list";
    }

    //添加页面跳转
    @RequestMapping(value = "/addmsglist",method = RequestMethod.GET)
    public String addmsglist(Model model, HttpServletRequest request){
        
        List<EquipmentUser> equipmentUser= equipmentUserMapper.getList();

        List<Equipment>equipmentlist= equipmentMapper.getList();

        model.addAttribute("equipmentUser",equipmentUser);
        model.addAttribute("equipmentlist",equipmentlist);
        
        return "msg/msg_add";
    }

    //列表页面跳转
    @RequestMapping(value = "/updatemsglist",method = RequestMethod.GET)
    public String updatemsglist(Model model, HttpServletRequest request){
        return "msg/update_msg";
    }
    
    //分页查询
    //分页查询
    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
  
        List<Msg>list=msgMapper.selectPageList(params);
        
        Integer count=msgMapper.count();
   
        TableResult tableResult = new TableResult(count, list);
        return tableResult;
    }
    
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Integer add(Msg msg){
 
        Integer info=0;
        info=msgMapper.insertSelective(msg);
        return info;
    }

    @RequestMapping(value = "/adds",method = RequestMethod.POST)
    @ResponseBody
    public Integer add(@RequestBody List<Msg> msgs){
        Integer info=0;
        for(int i=0;i<msgs.size();i++){
            Msg msg=msgs.get(i);
            info=msgMapper.insertSelective(msg);
        }
        return info;
    }
    
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(@RequestBody Integer [] ids){
        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=msgMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    
    
}
