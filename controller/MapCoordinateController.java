package com.yunniu.lease.controller;


import com.yunniu.lease.model.MapCoordinate;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.MapCoordinateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mapCoordinate/")
public class MapCoordinateController {

    @Resource
    private MapCoordinateService mapCoordinateService;


    @RequestMapping(value = "/list")
    public String goods(Model model) {
        return "mapCoordinate/list";
    }

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public TableResult getMapCoordinateList(HttpServletRequest request) {
        return mapCoordinateService.getMapCoordinateList(request);
    }


    @RequestMapping(value = "/toAdd")
    public String toAdd(Model model) {
        return "mapCoordinate/add";
    }


    @RequestMapping(value = "/addMapCoordinate", method = RequestMethod.POST)
    @ResponseBody
    public Result addMapCoordinate(MapCoordinate mapCoordinate) {
        return mapCoordinateService.addMapCoordinate(mapCoordinate);
    }


    @RequestMapping(value = "/delMapCoordinate", method = RequestMethod.POST)
    @ResponseBody
    public Result delMapCoordinate(String id) {
        return mapCoordinateService.delMapCoordinate(id);
    }


}
