package com.yunniu.lease.service;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Role;

import java.util.List;

public interface RoleService {
    JSONObject getRoleList(ManageParams mps);

    List<Role> getAllRoleList();

    String saveRole(Role role);

    String delRoleById(String ids);

    Role getRoleById(String roleId);
}
