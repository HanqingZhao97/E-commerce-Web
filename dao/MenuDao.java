package com.yunniu.lease.dao;

import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {
    @Select(value = {"<script>",
            "select m.*",
            "from admin a",
            "left join role r",
            "on a.role_id=r.role_id",
            "LEFT JOIN menu m",
            "on FIND_IN_SET(m.menu_id,r.menu_id)",
            "where a.admin_id=#{adminId} and m.menu_type=0",
            "order by m.menu_level desc ,m.menu_order",
            "</script>"})
    List<Menu> getMenuByAdminId(String adminId);

    @Select(value = {"<script>",
            "select m.*,m1.menu_name menuNamep",
            "from menu m",
            "left JOIN menu m1",
            "on m.menu_pid=m1.menu_id",
            "where m.menu_type=0 and m.menu_level>0",
            "<when test='menuName !=null and menuName!=\"\"'>and m.menu_name like concat('%',#{menuName},'%')</when>",
            "<when test='menuPid !=null and menuPid!=\"\"'>and m.menu_pid=#{menuPid}</when>",
            "ORDER BY m.menu_level,m1.menu_order,m.menu_order",
            "limit #{startCount},#{pageSize}",
            "</script>"})
    List<Menu> getMenuList(ManageParams mps);

    @Select(value = {"<script>",
            "select count(1) count",
            "from menu m",
            "left JOIN menu m1",
            "on m.menu_pid=m1.menu_id",
            "where m.menu_type=0 and m.menu_level>0",
            "<when test='menuName !=null and menuName!=\"\"'>and m.menu_name like concat('%',#{menuName},'%')</when>",
            "<when test='menuPid !=null and menuPid!=\"\"'>and m.menu_pid=#{menuPid}</when>",
            "</script>"})
    int getMenuListCount(ManageParams mps);

    @Select(value = {"<script>",
            "select * from menu where menu_id=#{menuId}",
            "</script>"})
    Menu getMenuById(String id);

    @Select(value = {"<script>",
            "select menu_id,menu_name",
            "from menu",
            "<where>",
            "<when test='menuId !=null and menuId!=\"\"'>and menu_id!=#{menuId}</when>",
            "</where>",
            "ORDER BY menu_level,menu_order",
            "</script>"})
    List<Menu> getPIdList(String menuId);

    @Insert(value = {"<script>",
            "INSERT INTO menu (",
            "<when test='menuPid !=null and menuPid!=\"\"'>menu_pid,</when>",
            "<when test='menuName !=null and menuName!=\"\"'>menu_name,</when>",
            "<when test='menuUrl !=null and menuUrl!=\"\"'>menu_url,</when>",
            "<when test=' menuIcon!=null and menuIcon!=\"\"'>menu_icon,</when>",
            "<when test=' menuOrder!=null and menuOrder!=\"\"'>menu_order,</when>",
            "<when test=' menuLevel!=null and menuLevel!=\"\"'>menu_level,</when>",
            "create_time)",
            "VALUES (",
            "<when test='menuPid !=null and menuPid!=\"\"'>#{menuPid},</when>",
            "<when test='menuName !=null and menuName!=\"\"'>#{menuName},</when>",
            "<when test='menuUrl !=null and menuUrl!=\"\"'>#{menuUrl},</when>",
            "<when test=' menuIcon!=null and menuIcon!=\"\"'>#{menuIcon},</when>",
            "<when test=' menuOrder!=null and menuOrder!=\"\"'>#{menuOrder},</when>",
            "<when test=' menuLevel!=null and menuLevel!=\"\"'>#{menuLevel},</when>",
            "now())",
            "</script>"})
    int addMenu(Menu menu);

    @Update(value = {"<script>",
            "update menu SET",
            "<when test='menuPid !=null and menuPid!=\"\"'>menu_pid=#{menuPid},</when>",
            "<when test='menuName !=null and menuName!=\"\"'>menu_name=#{menuName},</when>",
            "<when test='menuUrl !=null and menuUrl!=\"\"'>menu_url=#{menuUrl},</when>",
            "<when test=' menuIcon!=null and menuIcon!=\"\"'>menu_icon=#{menuIcon},</when>",
            "<when test=' menuOrder!=null and menuOrder!=\"\"'>menu_order=#{menuOrder},</when>",
            "<when test=' menuLevel!=null and menuLevel!=\"\"'>menu_level=#{menuLevel},</when>",
            "modified_time=now()",
            "where menu_id=#{menuId}",
            "</script>"})
    int editMenu(Menu menu);

    @Select(value = {"<script>",
            "select count(1)",
            "from menu",
            "where menu_name=#{menuName } and menu_pid =#{menuPid}",
            "<when test='menuId !=null and menuId!=\"\"'>and menu_id !=#{menuId}</when>",
            "</script>"})
    int checkMenuName(Menu menu);

    @Select(value = {"<script>",
            "select DISTINCT m1.menu_id,m1.menu_name ",
            "from menu m ",
            "JOIN menu m1 ",
            "on m.menu_pid=m1.menu_id ",
            "order by m1.menu_id",
            "</script>"})
    List<Menu> getParentMenuList();

    @Select(value = {"<script>",
            "select GROUP_CONCAT(menu_id) ids from menu where FIND_IN_SET(menu_pid,#{ids})",
            "</script>"})
    String getMenuByPIds(String ids);

    @Delete(value = {"<script>",
            "delete from menu where FIND_IN_SET(menu_id,#{ids})",
            "</script>"})
    int deleteMenu(String ids);

    @Select(value = {"<script>",
            "select menu_id id,menu_name name,menu_pid pId from menu order by menu_level,menu_order",
            "</script>"})
    List<Map<String, String>> getAllMenu();

}
