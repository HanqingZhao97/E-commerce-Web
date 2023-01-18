package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.AdminDao;
import com.yunniu.lease.model.Admin;
import com.yunniu.lease.model.Pages;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.AdminService;
import com.yunniu.lease.util.TokenSign;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Override
    public TableResult getAdminList(HttpServletRequest request) {
        Map<String, Object> params = Pages.getParams(request);
        int count = adminDao.getAdminListCount(params);
        List<Admin> list = adminDao.getAdminList(params);
        return new TableResult(count, list);
    }


    @Override
    public Admin getAdminById(String id) {
        return adminDao.getAdminById(id);
    }

    @Override
    public Result delAdmin(String id) {
        try {
            int res = adminDao.delAdmin(id);
            if (res > 0) {
                return new Result();
            }
        } catch (
                DataIntegrityViolationException e) {
            return new Result().error("数据使用中,无法删除!");
        }
        return new Result().error();
    }

    @Override
    public Result addAdmin(Admin admin) {
        Admin admin1 = adminDao.getAdminByAccount(admin.getAdminAccount());
        if (admin1 != null) {
            return new Result().error("账号已存在!");
        }
        String password = admin.getAdminPassword();
        String saltStr = UUID.randomUUID().toString();
        ByteSource salt = ByteSource.Util.bytes(saltStr);
        if (password == null || password.isEmpty()) {
            password = "123456";
        }
        String pwd = new SimpleHash("MD5", password, salt).toString();
        admin.setAdminSalt(saltStr);
        admin.setAdminPassword(pwd);

        int res = adminDao.addAdmin(admin);
        if (res > 0) {
            return new Result();
        }
        return new Result().error();
    }

    @Override
    public Result editAdmin(Admin admin) {
        Admin admin1 = adminDao.getAdminByAccount(admin.getAdminAccount());
        if (admin1 != null && !admin.getAdminId().equals(admin1.getAdminId())) {
            return new Result().error("账号已存在!");
        }
        String password = admin.getAdminPassword();
        if (password != null && !password.isEmpty()) {
            String saltStr = UUID.randomUUID().toString();
            ByteSource salt = ByteSource.Util.bytes(saltStr);
            String pwd = new SimpleHash("MD5", password, salt).toString();
            admin.setAdminSalt(saltStr);
            admin.setAdminPassword(pwd);
        }

        try {
            int res = adminDao.editAdmin(admin);
            if (res > 0) {
                return new Result();
            }
        } catch (
                DataIntegrityViolationException e) {
            return new Result().error("数据使用中,无法删除!");
        }

        return new Result().error();
    }


    @Override
    public Result abolishAdmin(Admin admin) {
        int res = adminDao.editAdmin(admin);
        if (res > 0) {
            return new Result();
        }
        return new Result().error();
    }

    @Override
    public Result editAdminInfo(Admin admin) {
        String password = admin.getAdminPassword();
        if (password != null && !password.isEmpty()) {
            String saltStr = UUID.randomUUID().toString();
            ByteSource salt = ByteSource.Util.bytes(saltStr);
            String pwd = new SimpleHash("MD5", password, salt).toString();
            admin.setAdminSalt(saltStr);
            admin.setAdminPassword(pwd);
        }
        int res = adminDao.editAdminInfo(admin);
        if (res > 0) {
            return new Result();
        }
        return new Result().error();
    }

    @Override
    public Result loginApp(String account, String password) {
        Result result = new Result();
        if (account.equals("yunniu") && password.equals("123456")) {
            String token = TokenSign.sign(account, password);
            result.setMsg(token);
        } else {
            return new Result().error();
        }
        return result;
    }

    @Override
    public Admin getAdminByAccount(String account) {
        return adminDao.getAdminByAccount(account);
    }

    @Override
    public void updateLoginTime(String adminAccount) {
        adminDao.updateLoginTime(adminAccount);
    }


}
