package com.yunniu.lease.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.service.MapService;
import com.yunniu.lease.util.HttpClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/8 22:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MapServiceImpl implements MapService {
    @Override  
    public Result getMapCoordinate (Business business) {
        Map<String, String> map = new HashMap<>();
        String url = "https://apis.map.qq.com/ws/geocoder/v1";

        map.put("address", business.getSaddress());
        map.put("key", "XWNBZ-DWAWS-IPIOE-6UM7C-3ZITK-RPBSW");
        String result = HttpClientUtil.doGet(url, map);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getInteger("status") == 0) {
            JSONObject result1 = jsonObject.getJSONObject("result");
            
            System.out.println(result1.getString("title")+"---");
            JSONObject location = result1.getJSONObject("location");
        
           
            business.setDlng(location.getString("lng"));//经度
            business.setDlat(location.getString("lat"));//纬度
            business.setTitle(result1.getString("title"));
            
            } else {
            return new Result(103, jsonObject.getString("message"));
        }
        return new Result(1000,"成功",business);
    }
}
