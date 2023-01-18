package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.EquipmentTypeMapper;
import com.yunniu.lease.model.EquipmentType;
import com.yunniu.lease.service.EquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/7 17:10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EquipmentTypeServiceImpl implements EquipmentTypeService {
    
    @Resource
    private EquipmentTypeMapper equipmentTypeMapper;
    
    @Override
    public List<EquipmentType> getList (Map<String, Object> map) {
    
        return equipmentTypeMapper.getPageList(map);
    }

    @Override
    public Integer getCount (Map<String, Object> map) {
        
        return equipmentTypeMapper.getCount(map);
    }

    @Override
    public Integer deleteTypeId (Integer[] ids) {
        Integer info=0;
        for(int i=0;i<ids.length;i++){
            info=equipmentTypeMapper.deleteByPrimaryKey(ids[i]);
        }
        return info;
    }

    @Override
    public Integer insertType (EquipmentType equipmentType) {
        Integer info=equipmentTypeMapper.insert(equipmentType);
        return info;
    }

    @Override
    public EquipmentType selectId (Integer id) {
        return equipmentTypeMapper.selectId(id);
    }

    @Override
    public Integer updateTypeService (EquipmentType equipmentType) {
        
        return equipmentTypeMapper.updateByPrimaryKeySelective(equipmentType);
    }
}
