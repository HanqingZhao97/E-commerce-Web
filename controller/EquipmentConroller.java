package com.yunniu.lease.controller;

import cn.hutool.core.date.DateTime;
import com.yunniu.lease.dao.BusinessMapper;
import com.yunniu.lease.dao.EquipmentMapper;
import com.yunniu.lease.dao.EquipmentTypeMapper;
import com.yunniu.lease.model.*;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.util.*;
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
 * @date 2022/6/10 10:03
 */
@Controller
@RequestMapping("/equipment")
public class EquipmentConroller {
    //商家
    @Resource
    private BusinessMapper businessMapper;
    
 
    //设备类型
    @Resource
    private EquipmentTypeMapper equipmentTypeMapper;
    
    
    //设备
    @Resource
    private EquipmentMapper equipmentMapper;
    
    
    
    
    //设备页面访问
    @RequestMapping(value ="/equipmentlist",method = RequestMethod.GET)
    public String equipmentUrl(Model model, HttpServletRequest request){
        
        return "equipment/equipment_list";
    }


    //添加设备路径
    @RequestMapping(value ="/equipmentAdd",method = RequestMethod.GET)
    public String equipmentAdd(Model model, HttpServletRequest request){
        //1.查询商家
        List <Business> businesses=businessMapper.selectByPrimarys(null);
        //2.查询设备类型
        List <EquipmentType> equipmentTypes=equipmentTypeMapper.selectAll();
        //3.放入mode当中
        model.addAttribute("businesses",businesses);
        model.addAttribute("equipmentTypes",equipmentTypes);
        
        return "equipment/equipment_add";
    }
    
    //设备数据提交
    @RequestMapping(value = "/equipmentAddsub",method = RequestMethod.POST)
    @ResponseBody
    public Result equipmentAddsub(Equipment equipment){
        if(equipment.getId()!=null){
            equipment.setCreateTime(new DateTime());
            String [] picArr=equipment.getBuinesImgArr();
            String pics = "";
            if (picArr.length > 0) {
                for (int i = 0; i < picArr.length; i++) {
                    if (picArr[i].startsWith("data:image")) {
                        String suffix = picArr[i].substring(picArr[i].indexOf("/") + 1, picArr[i].indexOf(";"));
                        String realpath = FileUtil.BasePath + "/goods/" + UUIDUtils.getUUID() + "." + suffix;
                        if (ImageUtil.GenerateImage(picArr[i + 1], realpath)) {
                            String url = COSUtil.uploadFileByPath(realpath);
                            FileUtil.deleteFilesByRealPath(realpath);
                            if ("".equals(pics)) {
                                pics = url;
                            } else {
                                pics = pics + "," + url;
                            }
                        } else {
                            return new Result().error("图片存储出错!");
                        }
                    } else if (picArr[i].startsWith("http")) {
                        picArr[i] = picArr[i].replace("amp;", "");
                        if ("".equals(pics)) {
                            pics = picArr[i];
                        } else {
                            pics = pics + "," + picArr[i];
                        }
                    }
                    System.out.println(pics+"++++");
                    equipment.setImg(pics);
                }
            }
            equipment.setImg(pics);
            Integer info=equipmentMapper.updateEquipemnt(equipment);
            return new Result(info,"修改成功",info);
        }else{
            equipment.setImg(QingTools.getImg(equipment.getBuinesImgArr()));
            Integer info=equipmentMapper.insertSelective(equipment);
            return new Result(info,"添加成功",info);
        }
      
    }

    //分页查询
    @RequestMapping(value ="/pageList",method = RequestMethod.POST)
    @ResponseBody
    public TableResult selectPageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);

        List<Equipment> equipments=equipmentMapper.getDaoPageList(params);
   
        Integer count=equipmentMapper.count();


        TableResult tableResult = new TableResult(count, equipments);
        return tableResult;
    }
    
    
    //1.删除设备
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteEquipmentMapper(@RequestBody Integer [] ids){

        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=equipmentMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    
    //修改设备路径
    @RequestMapping(value ="/updateEquipementMapper",method = RequestMethod.GET)
    public String updateEquipementMapper(Model model,Integer id){
        //1.查询商家
        List <Business> businesses=businessMapper.selectByPrimarys(null);
        //2.查询设备类型
        List <EquipmentType> equipmentTypes=equipmentTypeMapper.selectAll();
        //3.查询设备
        Equipment equipment=equipmentMapper.selectByPrimaryKey(id);

        model.addAttribute("businesses",businesses);
        
        model.addAttribute("equipmentTypes",equipmentTypes);
        
        model.addAttribute("equipment",equipment);
        return "equipment/equipment_edit";
    }
}
