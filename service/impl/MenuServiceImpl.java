package com.yunniu.lease.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.dao.MenuDao;
import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Menu;
import com.yunniu.lease.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Override
    public List<Menu> getMenuByAdminId(String adminId) {

        List<Menu> menuList = menuDao.getMenuByAdminId(adminId);
        // 从低级往高级菜单循环
        while (menuList.size() > 1) {
            Menu menu = menuList.get(0);
            menuList.remove(0);
            for (int i = 0; i < menuList.size(); i++) {
                if (menu.getMenuPid().equals(menuList.get(i).getMenuId())) {
                    menuList.get(i).getChildMenus().add(menu);
                    break;
                }
            }
        }
        return menuList;
    }


    @Override
    public JSONObject getMenuList(ManageParams mps) {
        JSONObject jo = new JSONObject();
        int count = menuDao.getMenuListCount(mps);
        mps = ManageParams.getParams(mps, count);
        jo.put("page", mps);
        jo.put("list", menuDao.getMenuList(mps));
        return jo;
    }


    @Override
    public Menu getMenuById(String id) {
        return menuDao.getMenuById(id);
    }


    @Override
    public List<Menu> getPIdList(String menuId) {
        return menuDao.getPIdList(menuId);
    }


    @Override
    public JSONObject saveMenu(Menu menu) {
        JSONObject jo = new JSONObject();
        menu.setMenuName(menu.getMenuName().replace(" ", ""));
        int resAccount = menuDao.checkMenuName(menu);
        if (resAccount > 0) {
            jo.put("msg", "菜单已存在!");
            jo.put("success", false);
            return jo;
        }
        Menu pm = menuDao.getMenuById("" + menu.getMenuPid());
        if (pm != null) {
            menu.setMenuLevel(pm.getMenuLevel() + 1);
        }
        if (menu.getMenuId() != 0) {
            //编辑
            int res = menuDao.editMenu(menu);
            if (res > 0) {
                jo.put("success", true);
                jo.put("msg", "修改成功!");
            } else {
                jo.put("success", false);
                jo.put("msg", "修改失败!");
            }
        } else {
            //新增
            int res = menuDao.addMenu(menu);
            if (res > 0) {
                jo.put("success", true);
                jo.put("msg", "新增成功!");
            } else {
                jo.put("success", false);
                jo.put("msg", "新增失败!");
            }

        }
        return jo;
    }


    @Override
    public List<Menu> getParentMenuList() {
        return menuDao.getParentMenuList();
    }


    @Override
    public String deleteById(String ids) {
        String childs = "_";
        while (childs != null && !"".equals(childs)) {
            ids += "," + childs;
            childs = menuDao.getMenuByPIds(childs);
        }

        int res = menuDao.deleteMenu(ids);
        JSONObject jo = new JSONObject();
        if (res > 0) {
            jo.put("success", true);
            jo.put("msg", "已成功删除" + res + "条数据!");
        } else {
            jo.put("success", false);
            jo.put("msg", "删除失败!");
        }
        return jo.toString();
    }


    @Override
    public List<Map<String, String>> getAllMenu() {
        return menuDao.getAllMenu();
    }

}
