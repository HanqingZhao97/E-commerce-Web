package com.yunniu.lease.controller;

import com.yunniu.lease.dao.OpinionMapper;
import com.yunniu.lease.dao.OpinionTypeMapper;
import com.yunniu.lease.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/11 17:51
 */
@Controller
@RequestMapping("/opinion_type")
public class opinionTypeController {
    @Resource
    private OpinionTypeMapper opinionTypeMapper;

    //列表页面跳转
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String opinionTypelist(Model model, HttpServletRequest request){
        return "opinion_type/opinion_type_list";
    }

    //添加页面跳转
    @RequestMapping(value = "/addlist",method = RequestMethod.GET)
    public String opinionTypemsglist(Model model, HttpServletRequest request){
        return "opinion_type/opinion_type_add";
    }

    //列表页面跳转
    @RequestMapping(value = "/uplist",method = RequestMethod.GET)
    public String updateopinionTypelist(Model model, HttpServletRequest request,Integer id){
        
        if(id!=null){
            OpinionType opinionType=opinionTypeMapper.selectByPrimaryKey(id);
            model.addAttribute("opinionType",opinionType);
        }
        
        return "opinion_type/opinion_type_edit";
    }

    /**
     * 分页查询
     * @param 
     * @return
     */
    //控制页面跳转
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        System.out.println(params.toString()+"------");
        List<OpinionType> list= opinionTypeMapper.getPageList(params);
        Integer count= opinionTypeMapper.selectCount();
        TableResult tableResult = new TableResult(count, list);
        System.out.println(list.toString());
        return tableResult;
    }


    @RequestMapping(value="/addOpinionType",method = RequestMethod.POST)
    @ResponseBody
    public Integer addOpinionType(@Valid @RequestBody OpinionType opinionType){
        Integer info=0;
        if(opinionType.getId()!=null){
            info= opinionTypeMapper.updateByPrimaryKeySelective(opinionType);
        }else{
            opinionType.setCreateTime(new Date());
            info= opinionTypeMapper.insertSelective(opinionType); 
        }
        return info;
    }
    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(@RequestBody Integer [] ids){
        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=opinionTypeMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    
    
}
