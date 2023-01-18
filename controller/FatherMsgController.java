package com.yunniu.lease.controller;

import com.yunniu.lease.dao.FatherMsgMapper;
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
 * @date 2022/6/25 7:36
 */
@Controller
@RequestMapping("/fathermsg")
public class FatherMsgController {
    
    @Resource
    private FatherMsgMapper fatherMsgMapper;
    
    @Resource
    private MsgMapper msgMapper;
    
    //HttpServletRequest request,
    @RequestMapping(value="/addMsgFather",method= RequestMethod.POST)
    @ResponseBody
    public Result addMsgFather(@RequestBody FatherMsg fatherMsgs){
        Integer info=0;
        Integer id=null;
        //生成结束日期
        fatherMsgs.setEndtime(new Date());
        
        //查询该用户下是否有是否有该设备，如果有则不添加，如果没有则田间返回当前ID
        fatherMsgMapper.insertSelective(fatherMsgs);
        id=fatherMsgs.getId();
        if(id!=null){
            for(int i=0;i<fatherMsgs.getMsgs().size();i++){
                Msg msg=fatherMsgs.getMsgs().get(i);
                msg.setFatermsgid(id);
                msg.setNumbername(fatherMsgs.getEquipmentname());
                info=msgMapper.insertSelective(msg);
            }
        }
        Result result=new Result(200,"添加成功",info);
        return result;
    }

    /**
     * 分页查询
     * @param
     * @return
     */
    //控制页面跳转
    @RequestMapping(value = "/getList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult getList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        List<FatherMsg> fatehrs=fatherMsgMapper.selectByPageList(params);
       
        fatehrs.get(fatehrs.size()-1).setPringTime(new Date());
     
        
        for(int i=0;i<fatehrs.size();i++){
          List<Msg> msgs=msgMapper.selectByPrimaryKey(fatehrs.get(i).getId());
          fatehrs.get(i).setMsgs(msgs);
        }
        
        Integer count=fatherMsgMapper.count(params);
        TableResult tableResult = new TableResult(count,fatehrs);
        return tableResult;
    }


    //控制页面跳转
    @RequestMapping(value = "/getLists",method= RequestMethod.POST)
    @ResponseBody
    public TableResult getLists(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        List<FatherMsg> fatehrs=fatherMsgMapper.selectByPageList(params);
        fatehrs.get(fatehrs.size()-1).setPringTime(new Date());
        for(int i=0;i<fatehrs.size();i++){
            List<Msg> msgs=msgMapper.selectByPrimaryKeys(fatehrs.get(i).getEquipmentname());
            fatehrs.get(i).setMsgs(msgs);
        }

        Integer count=fatherMsgMapper.count(params);
        TableResult tableResult = new TableResult(count,fatehrs);
        return tableResult;
    }

    //控制页面跳转
    @RequestMapping(value = "/getListemps",method= RequestMethod.POST)
    @ResponseBody
    public TableResult getListemps(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        List<FatherMsg> fatehrs=fatherMsgMapper.selectByPageListemp(params);
        Integer count=fatherMsgMapper.count(params);
        TableResult tableResult = new TableResult(count,fatehrs);
        return tableResult;
    }



   
    
    


}
