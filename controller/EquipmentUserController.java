package com.yunniu.lease.controller;

import com.yunniu.lease.dao.EquipmentUserMapper;
import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.EquipmentUser;
import com.yunniu.lease.model.Pages;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.util.QingTools;
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
 * @date 2022/6/11 8:03
 */
@Controller
@RequestMapping("/EquipmentUser")
public class EquipmentUserController {
    @Resource
    EquipmentUserMapper equipmentUserMapper;
    
    //设备用户路径list
    @RequestMapping(value ="/listurl",method = RequestMethod.GET)
    public String EquiUrlList(Model model, HttpServletRequest request){
        
        return "equipment_user/equipment_user_list";
    }
    
    //设备添加路径
    @RequestMapping(value = "/addurl",method = RequestMethod.GET)
    public String EquiaddUrl(Model model, HttpServletRequest request){
      
        return "equipment_user/equipment_user_add";
    }
    
    //修改设备路径
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String updateEquiUpdate(Model model, Integer id){
        
        EquipmentUser equipmentUser=equipmentUserMapper.selectByPrimaryKey(id);
        model.addAttribute("equipmentUser",equipmentUser);
        
        return "equipment_user/equipment_user_edit";
    }
    
    //添加设备用户
    @RequestMapping(value="/addequiUser",method = RequestMethod.POST)
    @ResponseBody
    public Integer addEquiUser(EquipmentUser equipmentUser){
        //判单是否添加成功
        Integer info=0;
        //添加图片地址
        if(equipmentUser.getId()!=null){//修改
            if(equipmentUser.getUserImg()==null){
                equipmentUser.setUserImg(QingTools.getImg(equipmentUser.getBuinesImgArr())); 
            }
            info= equipmentUserMapper.updateByPrimaryKeySelective(equipmentUser);
        }else{
            if(equipmentUser.getUserImg()==null){
                equipmentUser.setUserImg(QingTools.getImg(equipmentUser.getBuinesImgArr()));
            }
            equipmentUser.setCreateTime(new Date());
            info=equipmentUserMapper.insertSelective(equipmentUser);
        }
        return info;
    }

    //分页查询
    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);

        Integer count= equipmentUserMapper.count();//总数据量
        List<EquipmentUser> list=equipmentUserMapper.pageList(params);
        TableResult tableResult = new TableResult(count, list);
        return tableResult;
    }

    //控制页面跳转
    @RequestMapping(value = "/delete",method= RequestMethod.POST)
    @ResponseBody
    public Integer equipemtUserDelete(Model model,@RequestBody Integer [] ids){
        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=equipmentUserMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    
    
}
