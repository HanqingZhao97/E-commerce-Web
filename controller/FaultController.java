package com.yunniu.lease.controller;

import com.yunniu.lease.dao.FaultMapper;
import com.yunniu.lease.model.*;
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
 * @author WanYaJun
 * @date 2022/6/13 9:05
 */
@Controller
@RequestMapping("/fult")
public class FaultController {
    
    @Resource
    private FaultMapper faultMapper;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String faultlist(Model model, HttpServletRequest request){
        return "fault/fault_list";
    }
    
    
    
    //添加页面跳转
    @RequestMapping(value = "/addlist",method = RequestMethod.GET)
    public String faultmsglist(Model model, HttpServletRequest request){
        return "fault/fault_add";
    }

    //列表页面跳转
    @RequestMapping(value = "/uplist",method = RequestMethod.GET)
    public String updatefault(Model model, HttpServletRequest request,Integer id){
        Fault fault=faultMapper.selectByPrimaryKey(id);
        model.addAttribute("fault",fault);
        
        return "fault/fault_edit";
    }
    
    //添加和修改
    @RequestMapping(value = "/updateFault",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateFault(Fault fault){
        Integer info=0;
        if(fault.getId()!=null){
            info= faultMapper.updateByPrimaryKeySelective(fault);
        }else{
            info=faultMapper.insertSelective(fault);
        }
        return info;
    }

    //分页查询
    @RequestMapping(value ="/pageFaultList",method = RequestMethod.POST)
    @ResponseBody
    public TableResult selectPageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);

        List<Fault> list= faultMapper.getPageList(params);
        Integer count=faultMapper.count();

        TableResult tableResult = new TableResult(count, list);
        return tableResult;
    }
    
    //删除
    //1.删除设备
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteEquipmentMapper(@RequestBody Integer [] ids){

        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=faultMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    
    

}
