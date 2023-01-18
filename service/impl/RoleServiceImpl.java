package com.yunniu.lease.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.dao.RoleDao;
import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Role;
import com.yunniu.lease.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;


    @Override
    public JSONObject getRoleList(ManageParams mps) {
        JSONObject jo = new JSONObject();
        int count = roleDao.getRoleListCount(mps);
        mps = ManageParams.getParams(mps, count);
        jo.put("page", mps);
        jo.put("list", roleDao.getRoleList(mps));
        return jo;
    }

    @Override
    public List<Role> getAllRoleList() {
        return roleDao.getAllRoleList();
    }


    @Override
    public String saveRole(Role role) {
        JSONObject jo = new JSONObject();
        int countRoleName = roleDao.checkRoleName(role);
        if (countRoleName > 0) {
            jo.put("success", false);
            jo.put("msg", "角色名已存在!");
            return jo.toString();
        }

        if (role.getRoleId() != 0) {
            // 编辑
            int res = roleDao.editRole(role);
            if (res > 0) {
                jo.put("msg", "保存成功!");
                jo.put("success", true);
            } else {
                jo.put("msg", "保存失败!");
                jo.put("success", false);
            }
        } else {
            //新增
            int res = roleDao.addRole(role);
            if (res > 0) {
                jo.put("msg", "保存成功!");
                jo.put("success", true);
            } else {
                jo.put("msg", "保存失败!");
                jo.put("success", false);
            }
        }
        return jo.toString();
    }

    @Override
    public String delRoleById(String ids) {
        int res = roleDao.delRoleById(ids);
        int res1 = roleDao.delAdminByRoleId(ids);//删除管理员
        JSONObject jo = new JSONObject();
        if (res > 0) {
            jo.put("msg", "成功删除" + res + "条信息!");
            jo.put("success", true);
        } else {
            jo.put("msg", "删除失败!");
            jo.put("success", false);
        }
        return jo.toString();
    }

    @Override
    public Role getRoleById(String roleId) {
        return roleDao.getRoleById(roleId);
    }

}
