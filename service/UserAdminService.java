package com.yunniu.lease.service;

import com.yunniu.lease.model.UserAdmin;

import java.util.List;

public interface UserAdminService {
    //1.查询的service
    public List<UserAdmin> getUserService();

    //2.插入的service
    public Integer insertUserService(UserAdmin userAdmin);

    //3.删除的service
    public Integer deleteUserService(Integer id);

    //4.修改的service
    public Integer updateUserService(UserAdmin userAdmin);

    //5.根据当前ID查询对应的数据
    public UserAdmin getUserID(Integer id);

}
