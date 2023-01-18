package com.yunniu.lease.controller;


import com.yunniu.lease.model.*;
import com.yunniu.lease.service.GoodsClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/goodsClass")
public class GoodsClassController {
    @Resource
    GoodsClassService goodsClassService;


    @RequestMapping(value = "/list")
    public String goodsClass(Model model) {
        return "goodsClass/list";
    }

    @RequestMapping(value = "/toBanner")
    public String toBanner(Model model) {
        model.addAttribute("obj", goodsClassService.getBanner());
        return "goodsClass/banner";
    }


    @RequestMapping(value = "getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getGoodsClassList(HttpServletRequest request) {
        return goodsClassService.getGoodsClassListPage(request);
    }


    @RequestMapping(value = "getPlist", method = RequestMethod.POST)
    @ResponseBody
    public List<GoodsClass> getPgoodsClassList(HttpServletRequest request) {
        return goodsClassService.getPgoodsClassList();
    }


    @RequestMapping(value = "/addOrEdit")
    public String addOrEdit(Model model, String id) {
        model.addAttribute("goodsClassId", id);
        model.addAttribute("goodsClassList", goodsClassService.getPgoodsClassList());
        return "goodsClass/edit";
    }


    @RequestMapping(value = "/getGoodsClassById", method = RequestMethod.POST)
    @ResponseBody
    public GoodsClass getGoodsClassById(String id) {
        return goodsClassService.getGoodsClassById(id);
    }


    @RequestMapping(value = "/updateGoodsClass", method = RequestMethod.POST)
    @ResponseBody
    public Result updateGoodsClass(GoodsClass goodsClass) {
        return goodsClassService.updateGoodsClass(goodsClass);
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Result delGoodsClass(String id) {
        return goodsClassService.delGoodsClass(id);
    }


    @RequestMapping(value = "/updateBanner", method = RequestMethod.POST)
    @ResponseBody
    public Result updateBanner(Banner banner) {
        Result result = goodsClassService.updateBanner(banner);
        return result;
    }


}
