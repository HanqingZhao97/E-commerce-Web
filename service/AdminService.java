package com.yunniu.lease.service;

import com.yunniu.lease.model.Admin;
import com.yunniu.lease.model.EquipmentType;
import com.yunniu.lease.model.Result;
import com.yunniu.lease.model.TableResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface AdminService {

    Admin getAdminByAccount(String account);

    void updateLoginTime(String adminAccount);

    TableResult getAdminList(HttpServletRequest request);

    Admin getAdminById(String id);

    Result delAdmin(String id);

    Result addAdmin(Admin admin);

    Result editAdmin(Admin admin);

    Result abolishAdmin(Admin admin);

    Result editAdminInfo(Admin admin);


    Result loginApp(String account, String password);

    /**
     * @author WanYaJun
     * @date 2022/6/7 17:09
     */

    interface EquipmentService {
        public List<EquipmentType> getList(EquipmentType equipmentType);
    }
}
