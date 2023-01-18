package com.yunniu.lease.controller;

import com.yunniu.lease.dao.BusinessMapper;
import com.yunniu.lease.dao.ProgramMapper;
import com.yunniu.lease.model.*;
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
 * @date 2022/6/13 11:30
 */
@Controller
@RequestMapping(value = "/program")
public class ProgramController {
    @Resource
    private ProgramMapper programMapper;
    
    @Resource
    private BusinessMapper businessMapper;
    
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String opinionlist(
            Model model, HttpServletRequest request){
        return "program/program_list";
    }

    //添加页面跳转
    @RequestMapping(value = "/addlist",method = RequestMethod.GET)
    public String opinionmsglist(Model model, HttpServletRequest request){
        List<Business> businesses=businessMapper.selectByPrimarys(null);
        model.addAttribute("businesses",businesses);
        
        return "program/userAdmin_add.html";
    }


    @RequestMapping(value="/add",method = RequestMethod.POST)
    @ResponseBody
    public Integer addOpinionType(Program program){
        Integer info=0;
        if(program.getId()!=null){
           
            program.setImgCode(QingTools.getImg(program.getProgramImgArr()));
            info=programMapper.updateByPrimaryKeySelective(program);
        }else{
            program.setImgCode(QingTools.getImg(program.getProgramImgArr()));
            info=programMapper.insertSelective(program);
        }
        return info;
    }
    
    //列表页面跳转
    @RequestMapping(value = "/uplist",method = RequestMethod.GET)
    public String updateopinion(Model model, HttpServletRequest request,Integer id){

        Program program=programMapper.selectByPrimaryKey(id);
        model.addAttribute("program",program);

        List<Business> businesses=businessMapper.selectByPrimarys(null);
        model.addAttribute("businesses",businesses);
        return "program/program_edit";
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
        List<Program> list= programMapper.pageList(params);
        Integer count= programMapper.count();
        TableResult tableResult = new TableResult(count, list);
        System.out.println(list.toString());
        return tableResult;
    }


    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(@RequestBody Integer [] ids){
        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=programMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    

}
