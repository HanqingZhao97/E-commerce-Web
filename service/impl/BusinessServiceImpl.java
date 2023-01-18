package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.BusinessMapper;
import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.service.BusinessService;
import com.yunniu.lease.util.COSUtil;
import com.yunniu.lease.util.FileUtil;
import com.yunniu.lease.util.ImageUtil;
import com.yunniu.lease.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/7 11:12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessServiceImpl implements BusinessService {
    @Resource
    private BusinessMapper businessMapper;
  

    public List<Business> selectByPrimarys(){
        List<Business> list= businessMapper.selectByPrimarys(null);
        return list;
    }
    
    @Override
    public Business businessSelect (Integer id) {
        Business business=businessMapper.selectByPrimaryKey(id);
        return business;
    }
    
    public Result addBusiness(Business business){
        String [] picArr=business.getBuinesImgArr();
        String pics = "";
        if (picArr.length > 0) {
            for (int i = 0; i < picArr.length; i++) {
                if (picArr[i].startsWith("data:image")) {
                    String suffix = picArr[i].substring(picArr[i].indexOf("/") + 1, picArr[i].indexOf(";"));
                    String realpath = FileUtil.BasePath + "/goods/" + UUIDUtils.getUUID() + "." + suffix;
                    if (ImageUtil.GenerateImage(picArr[i + 1], realpath)) {
                        String url = COSUtil.uploadFileByPath(realpath);
                        FileUtil.deleteFilesByRealPath(realpath);
                        if ("".equals(pics)) {
                            pics = url;
                        } else {
                            pics = pics + "," + url;
                        }
                    } else {
                        return new Result().error("图片存储出错!");
                    }
                } else if (picArr[i].startsWith("http")) {
                    picArr[i] = picArr[i].replace("amp;", "");
                    if ("".equals(pics)) {
                        pics = picArr[i];
                    } else {
                        pics = pics + "," + picArr[i];
                    }
                }
                business.setBuinesimg(pics);
            }
        
            return new Result(100,"成功", businessMapper.insertSelective(business));
            
        }
        
        return new Result().error("图片存储出错!");
    }

    @Override
    public Integer updateBusiness (Business business) {
        return businessMapper.updateByPrimaryKeySelective(business);
    }

    @Override
    public Integer deleteBusiness (Integer id) {
        
        return businessMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer insertBusiness (Business business) {
        
        return businessMapper.insertSelective(business);
    }

    @Override
    public List<Business> selectByPrimarys (Map<String, Object> map) {
        
        return businessMapper.getDaoPageList(map);
    }

    @Override
    public Integer pageCount (Map<String, Object> map) {
        return businessMapper.selectDataCount();
    }
}
