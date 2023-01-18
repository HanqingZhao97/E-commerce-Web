package com.yunniu.lease.controller;

import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.MapCoordinate;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.service.MapCoordinateService;
import com.yunniu.lease.service.MapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/8 16:52
 */
@Controller
@RequestMapping("/testMap")
public class MapController {
    @Resource
    private MapService mapService;
    
    @RequestMapping(value="/map",method = RequestMethod.GET)
    public String url(Model model, HttpServletRequest request){
        return "map/index";
    }


    @RequestMapping(value="/seachmap",method = RequestMethod.GET)
    public String seachmap(Model model, HttpServletRequest request){
        return "map/PickUp";
    }

    @RequestMapping(value = "/addMapCoordinate", method = RequestMethod.POST)
    @ResponseBody
    public Result addMapCoordinate(@RequestBody Business business) {
        return mapService.getMapCoordinate(business);
    }
}
