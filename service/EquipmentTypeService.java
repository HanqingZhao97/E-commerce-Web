package com.yunniu.lease.service;

import com.yunniu.lease.model.Equipment;
import com.yunniu.lease.model.EquipmentType;

import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/7 17:12
 */
public interface EquipmentTypeService {
    /**
     * 分页查询
     * @param equipmentType
     * @return
     */
    public List<EquipmentType> getList(Map<String, Object> map);
    
    //数据总量
    public Integer getCount(Map<String, Object> map);
    
    
    //删除
    public Integer deleteTypeId(Integer [] ids);
    
    //添加
    public Integer insertType(EquipmentType equipmentType);
    
    //根据ID进行查询
    public EquipmentType selectId(Integer id);
    
    //修改
    public Integer updateTypeService(EquipmentType equipmentType);
}
