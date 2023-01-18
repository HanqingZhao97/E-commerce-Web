package com.yunniu.lease.controller;

import com.yunniu.lease.model.*;
import com.yunniu.lease.service.GoodsClassService;
import com.yunniu.lease.service.GoodsService;
import com.yunniu.lease.util.COSUtil;
import com.yunniu.lease.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/goods/")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsClassService goodsClassService;


    @RequestMapping(value = "/list")
    public String goods(Model model, String isLease) {
//        model.addAttribute("isLease", isLease);
        model.addAttribute("goodsClassList", goodsClassService.getPgoodsClassList());
        if (isLease.equals("1")) {
            return "goods/rentList";
        } else {
            return "goods/list";//rentAddGoodsSpec
        }
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getGoodsList(HttpServletRequest request) {
        return goodsService.getGoodsList(request);
    }


    @RequestMapping(value = "/addOrEdit")
    public String editGoods(Model model, String id, String isLease) {
        List<GoodsClass> goodsClassList = goodsClassService.getPgoodsClassList();
        model.addAttribute("goodsId", id);
        if (id == null || id.equals("")) {
            model.addAttribute("obj", new Goods());
            model.addAttribute("getGoodsClassPid", goodsClassService.getPgoodsClassByPidList(goodsClassList.get(0).getGoodsClassId()));
        } else {
            Goods goods = goodsService.getGoodsById(id);
            model.addAttribute("obj", goods);
            model.addAttribute("getGoodsClassPid", goodsClassService.getPgoodsClassByPidList(goods.getGoodsClassPid() + ""));
        }
//        model.addAttribute("isLease", isLease);
        model.addAttribute("goodsClassList", goodsClassList);
        model.addAttribute("serviceList", goodsClassService.getDictionary("service"));
        model.addAttribute("deliveryList", goodsClassService.getDictionary("delivery"));

        if (isLease.equals("1")) {
            return "goods/rentAddOrEdit";
        } else {
            return "goods/addOrEdit";
        }
    }


    @RequestMapping(value = "/toSpec")
    public String toSpec(Model model, String id, String isLease) {
        List<GoodsSpec> goodsSpecByGoodsId = goodsService.getGoodsSpecByGoodsId(id);
        model.addAttribute("goodsId", id);
        model.addAttribute("number", goodsSpecByGoodsId.size());
        model.addAttribute("goodsSpecList", goodsSpecByGoodsId);
        if (null != isLease && isLease.equals("1")) {
            return "goods/rentAddGoodsSpec";
        } else {
            return "goods/addGoodsSpec";
        }
    }


    @RequestMapping(value = "/updateGoodsSpec")
    @ResponseBody
    public Result addGoodsSpec(GoodsSpecs goodsSpecs) {
        return goodsService.updateGoodsSpec(goodsSpecs);
    }


    @RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
    @ResponseBody
    public Result updateGoods(Goods goods) {
        return goodsService.updateGoods(goods);
    }

    @RequestMapping(value = "/getGoodsById", method = RequestMethod.POST)
    @ResponseBody
    public Goods getGoodsById(String id) {
        return goodsService.getGoodsById(id);
    }

    @RequestMapping(value = "/getGoodsClassByPid", method = RequestMethod.POST)
    @ResponseBody
    public List<GoodsClass> getGoodsClassByPid(String id) {
        return goodsClassService.getPgoodsClassByPidList(id);
    }


    @RequestMapping(value = "/getDictionary", method = RequestMethod.POST)
    @ResponseBody
    public List<Dictionary> getDictionary(String type) {
        return goodsClassService.getDictionary(type);
    }


    @RequestMapping(value = "/changeAbolishState", method = RequestMethod.POST)
    @ResponseBody
    public Result changeAbolishState(String goodsId, String abolishState) {
        return goodsService.changeAbolishState(goodsId, abolishState);
    }

    @RequestMapping(value = "/changeDeleteState", method = RequestMethod.POST)
    @ResponseBody
    public Result changeDeleteState(String id) {
        return goodsService.changeDeleteState(id);
    }


    @RequestMapping(value = "uploads")
    @ResponseBody
    public Map<String, Object> uploads(@RequestParam(value = "upfile", required = false) MultipartFile upfile)
            throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        try {
            if (!upfile.isEmpty()) {
                String path = FileUtil.saveFile(upfile, "img");
//                String url = OssUtil.uploadImgToOss(ff);
                String url = COSUtil.uploadFileByPath(path);
                rs.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
                rs.put("url", url); // 能访问到你如今图片的路径
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("state", e.getMessage());
            rs.put("url", "");
            rs.put("title", "");
            rs.put("original", "");
        }
        return rs;
    }


}