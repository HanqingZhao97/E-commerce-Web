package com.yunniu.lease.service;

import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.Result;

/**
 * @author WanYaJun
 * @date 2022/6/8 22:15
 */
public interface MapService {
    public Result getMapCoordinate(Business business);
}
