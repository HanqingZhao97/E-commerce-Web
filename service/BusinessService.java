package com.yunniu.lease.service;

import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.Result;

import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/7 11:27
 */

public interface BusinessService {
    //根据ID查询商家
    public Business businessSelect(Integer id);
    
    //修改商家
    public Integer updateBusiness(Business business);
    
    //根据ID删除商家
    public Integer deleteBusiness(Integer id);
    
    //添加商家
    public Integer insertBusiness(Business business);

    /**
     * 根据条件分页查询
     * @return
     */
    public List<Business> selectByPrimarys(Map<String, Object> map);

    /**
     * 计算一共有多少页
     * @param map
     * @return
     */
    public Integer pageCount(Map<String, Object> map);

    //添加
    public Result addBusiness(Business business);
    
    
}
