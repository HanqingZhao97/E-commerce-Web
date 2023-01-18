package com.yunniu.lease.controller;

import cn.hutool.core.date.DateTime;
import com.yunniu.lease.model.Equipment;
import com.yunniu.lease.model.EquipmentType;
import com.yunniu.lease.model.Pages;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.EquipmentTypeService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/7 16:51
 */
@Controller
@RequestMapping("/equipemtType")
public class EquipmentTypeController {
    
    @Autowired
    private EquipmentTypeService equipmentTypeService;
    //控制页面跳转
    @RequestMapping(value = "/list",method= RequestMethod.GET)
    public String equipemtTypePage(Model model, HttpServletRequest request){
        return "equipment_type/list";
    }

    //控制页面跳转
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params=Pages.getParams(request);
        System.out.println(params.toString());
        List<EquipmentType> list= equipmentTypeService.getList(params);
        Integer count= equipmentTypeService.getCount(params);
        TableResult tableResult = new TableResult(count, list);
        System.out.println(list.toString());
        return tableResult;
    }


    //控制页面跳转
    @RequestMapping(value = "/delete",method= RequestMethod.POST)
    @ResponseBody
    public Integer equipemtTypePageList(Model model,@RequestBody Integer [] ids){
       Integer info=equipmentTypeService.deleteTypeId(ids);
       return info;
    }
    
    @RequestMapping(value ="/addOrEdit",method = RequestMethod.GET)
    public String addOrEdit(Model model, Integer id){
        String url=null;
        if(id!=null){
            url="equipment_type/edit";
            System.out.println(equipmentTypeService.selectId(id).toString()+"-------");
            model.addAttribute("type",equipmentTypeService.selectId(id));
        }else{
            url="equipment_type/add";
        }
        return url;
    }

    @RequestMapping(value ="/addType",method = RequestMethod.POST)
    @ResponseBody
    public Integer addType(@RequestBody EquipmentType equipmentType){
        equipmentType.setCreateTime(new DateTime());
        Integer info=equipmentTypeService.insertType(equipmentType);
        return info;
    }

    @RequestMapping(value ="/updateType",method = RequestMethod.POST)
    @ResponseBody
    public Integer updateType(@RequestBody EquipmentType equipmentType){
        equipmentType.setCreateTime(new DateTime());
        Integer info=equipmentTypeService.updateTypeService(equipmentType);
        return info;
    }
    
}
