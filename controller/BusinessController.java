package com.yunniu.lease.controller;

import cn.hutool.core.date.DateTime;
import com.yunniu.lease.dao.BusinessMapper;
import com.yunniu.lease.model.*;
import com.yunniu.lease.util.COSUtil;
import com.yunniu.lease.util.FileUtil;
import com.yunniu.lease.util.ImageUtil;
import com.yunniu.lease.util.UUIDUtils;
import org.springframework.ui.Model;
import com.yunniu.lease.service.BusinessService;
import com.yunniu.lease.service.impl.BusinessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @date 2022/6/7 11:15
 */

@Controller
@RequestMapping("/business")
public class BusinessController {
   
    @Autowired
    private BusinessService businessService;
    
    @Resource
    private BusinessMapper businessMapper;
    
    
    //商家页面跳转
    @RequestMapping(value = "/businessUrl",method = RequestMethod.GET)
    public String businessUrl(Model model, HttpServletRequest request){
       return "business/list";
    }
    //添加商家页面跳转
    @RequestMapping(value = "/businessAdd",method = RequestMethod.GET)
    public String businessAdd(Model model, HttpServletRequest request){
        return "business/add";
    }
    //跳转到商家修改页面
    @RequestMapping(value = "/businessUpdate",method = RequestMethod.GET)
    public String businessUpdate(Model model,Integer id){
        Business business=businessMapper.selectByPrimaryKey(id);
        model.addAttribute("Business",business);
        return "business/edit";
    }
    
    //添加商家
    @RequestMapping(value = "/addBusiness",method = RequestMethod.POST)
    @ResponseBody
    public Result addBusiness(Business business){
        business.setCreateTime(new DateTime());
        Result result=null;
        if(business.getId()!=null){
            String [] picArr=business.getBuinesImgArr();
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
                    business.setBuinesimg(pics);
                }
            }
            Integer info=businessMapper.updateByPrimaryKeySelective(business);
            return new Result(info,"修改成功",info);
        }else{
           result=businessService.addBusiness(business);
        }
        return result;
    }
    
    
    //分页查询
    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        System.out.println(params.toString()+"123");
        System.out.println(params.get("iphone"));
        List<Business> list= businessService.selectByPrimarys(params);
        Integer count= businessService.pageCount(params);
        TableResult tableResult = new TableResult(count, list);
        System.out.println(list.toString());
        return tableResult;
    }
    
    
    
    @RequestMapping(value = "/getBusiness",method=RequestMethod.GET)
    public String getBusiness(Model model, HttpServletRequest request){
       
        String sid=request.getParameter("id");
        Integer id=null;
        if(sid!=null){
            id=Integer.parseInt(sid);
        }
        Business business=businessService.businessSelect(id);
        model.addAttribute("business");
      return "test";
    }

    @RequestMapping(value = "/insertBusiness",method=RequestMethod.GET)
    public String insertBusiness(Model model, HttpServletRequest request){
        Business obj=(Business)request.getAttribute("business");
        obj=new Business();
        String str=request.getParameter("buinessName");
        System.out.println(str);
        request.getParameter("dlng");
        obj.setBuinessName(str);
        Integer info= businessService.insertBusiness(obj);
        model.addAttribute("info",info);
        return "test";
    }
    @RequestMapping(value = "/delteBusiness",method=RequestMethod.POST)
    @ResponseBody
    public Integer delteBusiness(@RequestBody  Integer [] ids){
        Integer info=null;
        if(ids.length>1){
            for(int i=0;i<ids.length;i++){
                info= businessService.deleteBusiness(ids[i]);
            }
        }else{
            Integer id=ids[0];
            info= businessService.deleteBusiness(id);
        }
        return info;
    }

    @RequestMapping(value = "/updateBusiness",method=RequestMethod.GET)
    public String updateBusiness(Model model, HttpServletRequest request){
        Business obj=(Business)request.getAttribute("business");
        Integer info= businessService.updateBusiness(obj);
        model.addAttribute("info",info);
        return "test";
    }
    
    

   
    
}
