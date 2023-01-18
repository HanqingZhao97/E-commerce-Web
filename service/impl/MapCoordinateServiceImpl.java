package com.yunniu.lease.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.dao.MapCoordinateDao;
import com.yunniu.lease.model.*;
import com.yunniu.lease.service.MapCoordinateService;
import com.yunniu.lease.util.HttpClientUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapCoordinateServiceImpl implements MapCoordinateService {

    @Resource
    private MapCoordinateDao mapCoordinateDao;

    @Override
    public TableResult getMapCoordinateList(HttpServletRequest request) {
        Map<String, Object> params = Pages.getParams(request);
        List<MapCoordinate> list = mapCoordinateDao.getMapCoordinateList(params);
        Integer count = mapCoordinateDao.getMapCoordinateCount(params);
        return new TableResult(count, list);
    }

    @Override
    public List<MapCoordinate> getMapCoordinateAll() {
        return mapCoordinateDao.getMapCoordinateAll();
    }

    @Override
    public Result addMapCoordinate(MapCoordinate mapCoordinate) {
        Map<String, String> map = new HashMap<>();
        String url = "https://apis.map.qq.com/ws/geocoder/v1";
//        https://apis.map.qq.com/ws/geocoder/v1/?address=%E4%B8%8A%E6%B5%B7%E5%B8%82%E4%B8%87%E8%BE%BE%E5%B9%BF%E5%9C%BA&key=XWNBZ-DWAWS-IPIOE-6UM7C-3ZITK-RPBSW
        map.put("address", mapCoordinate.getAddress());
        map.put("key", "XWNBZ-DWAWS-IPIOE-6UM7C-3ZITK-RPBSW");
        String result = HttpClientUtil.doGet(url, map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("status") == 0) {
            JSONObject result1 = jsonObject.getJSONObject("result");
            mapCoordinate.setTitle(result1.getString("title"));
            JSONObject location = result1.getJSONObject("location");
            mapCoordinate.setLng(location.getString("lng"));
            mapCoordinate.setLat(location.getString("lat"));
        } else {
            return new Result(103, jsonObject.getString("message"));
        }
        Integer res = mapCoordinateDao.addMapCoordinate(mapCoordinate);
        if (res > 0) {
            return new Result("新增成功");
        }
        return new Result("新增失败");
    }

    @Override
    public Result updateMapCoordinate(MapCoordinate mapCoordinate) {
        return null;
    }

    @Override
    public Result delMapCoordinate(String ids) {
        Integer res = mapCoordinateDao.delMapCoordinate(ids);
        if (res > 0) {
            return new Result("删除成功");
        }
        return new Result("删除失败");
    }


}
