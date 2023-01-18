package com.yunniu.lease.controller;

import com.yunniu.lease.dao.EquipmentUserMapper;
import com.yunniu.lease.dao.OpinionMapper;
import com.yunniu.lease.dao.OpinionTypeMapper;
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
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/12 14:04
 */
@Controller
@RequestMapping("/opinion")
public class OpinionController {
    
    @Resource
    private EquipmentUserMapper equipmentUserMapper;
    @Resource
    private OpinionTypeMapper opinionTypeMapper;
    
    @Resource
    private OpinionMapper opinionMapper;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String opinionlist(
            Model model, HttpServletRequest request){
        return "opinion/opinion_list";
    }

    //添加页面跳转
    @RequestMapping(value = "/addlist",method = RequestMethod.GET)
    public String opinionmsglist(Model model, HttpServletRequest request){
        List<EquipmentUser> equipmentUsers=equipmentUserMapper.getList();
        List<OpinionType> opinionTypes=opinionTypeMapper.getList();
        model.addAttribute("users",equipmentUsers);
        model.addAttribute("types",opinionTypes);
        return "opinion/opinion_add.html";
    }

    //列表页面跳转
    @RequestMapping(value = "/uplist",method = RequestMethod.GET)
    public String updateopinion(Model model, HttpServletRequest request,Integer id){
        return "opinion/opinion_edit";
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
        List<Opinion> list= opinionMapper.getPageList(params);
        Integer count=opinionMapper.count();
        TableResult tableResult = new TableResult(count, list);
        return tableResult;
    }

    
    @RequestMapping(value="/addOpinionType",method = RequestMethod.POST)
    @ResponseBody
    public Integer addOpinionType(Opinion opinion){
        Integer info=0;
        if(opinion.getId()!=null){
             opinion.setImg(QingTools.getImg(opinion.getOpinionImgArr()));
             info=opinionMapper.updateByPrimaryKeySelective(opinion);
        }else{
             opinion.setImg(QingTools.getImg(opinion.getOpinionImgArr()));
             opinion.setCreateTime(new Date());
             info=opinionMapper.insertSelective(opinion);
        }
        return info;
    }

    @RequestMapping(value="/addOpinion",method = RequestMethod.POST)
    @ResponseBody
    public Result addOpinion(Opinion opinion){
        Integer info=0;
        if(opinion.getId()!=null){
            if(opinion.getOpinionImgArr()!=null){
                opinion.setImg(QingTools.getImg(opinion.getOpinionImgArr()));
            }
            info=opinionMapper.updateByPrimaryKeySelective(opinion);
        }else{
            if(opinion.getOpinionImgArr()!=null){
                opinion.setImg(QingTools.getImg(opinion.getOpinionImgArr()));
            }
            opinion.setCreateTime(new Date());
            info=opinionMapper.insertSelective(opinion);
        }
        Result result=null;
        if(info==1){
            result=new Result(200,"添加成功",info);
        }else{
            result=new Result(100,"添加失败",info);
        }
        return result;
    }
    
    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    @ResponseBody
    public Integer delete(@RequestBody Integer [] ids){
        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=opinionMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }
    
}
