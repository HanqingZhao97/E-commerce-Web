package com.yunniu.lease.service;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {


    List<Menu> getMenuByAdminId(String adminId);

    JSONObject getMenuList(ManageParams mps);

    Menu getMenuById(String id);

    List<Menu> getPIdList(String id);

    JSONObject saveMenu(Menu menu);

    List<Menu> getParentMenuList();

    String deleteById(String ids);

    List<Map<String, String>> getAllMenu();

}
