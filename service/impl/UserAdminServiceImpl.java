package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.UserAdminMapper;
import com.yunniu.lease.model.UserAdmin;
import com.yunniu.lease.service.UserAdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hanqing Zhao
 * @date 2022/08/02
 */
@Service
public class UserAdminServiceImpl implements UserAdminService {

    @Resource
    //Resource 帮忙 new 一个 object
    private UserAdminMapper userAdminMapper;

    @Override
    public List<UserAdmin> getUserService() {
        List<UserAdmin> userAdmins = userAdminMapper.getUserAdmin();
        return userAdmins;
    }

    @Override
    public Integer insertUserService(UserAdmin userAdmin) {
        //1 means success, 0 means fail
        Integer info = userAdminMapper.addUser(userAdmin);
        return info;
    }

    @Override
    public Integer deleteUserService(Integer id) {
        Integer info = userAdminMapper.deleteUser(id);
        return info;
    }

    @Override
    public Integer updateUserService(UserAdmin userAdmin) {
        Integer info = userAdminMapper.updateUser(userAdmin);
        return info;
    }

    @Override
    public UserAdmin getUserID(Integer id) {
        UserAdmin userAdmin = userAdminMapper.getUserID(id);
        return userAdmin;
    }
}
