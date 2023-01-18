package com.yunniu.lease.controller;

import com.yunniu.lease.dao.UserInfoMapper;
import com.yunniu.lease.model.AdminInfo;
import com.yunniu.lease.model.Pages;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author WanYaJun
 * @date 2022/6/28 11:46
 */
@Controller
@RequestMapping("/UserInfo")
public class UserInfoController {
    
    @Resource
    private UserInfoMapper userInfoMapper;
    
    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult equipemtTypePageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        List<UserInfo> list= userInfoMapper.getPageList(params);
        Integer count= userInfoMapper.count(params);
        TableResult tableResult = new TableResult(count,list);
        return tableResult;
    }
}
