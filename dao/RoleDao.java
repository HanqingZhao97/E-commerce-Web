package com.yunniu.lease.dao;

import com.yunniu.lease.model.ManageParams;
import com.yunniu.lease.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {


    @Select(value = {"<script>",
            "select count(1)",
            "from role",
            "<where>",
            "<when test='roleName !=null and roleName!=\"\"'>and role_name like concat('%',#{roleName},'%')</when>",
            "</where>",
            "</script>"})
    int getRoleListCount(ManageParams mps);

    @Select(value = {"<script>",
            "select count(1) from role where role_name =#{roleName}  and role_id!=#{roleId}",
            "</script>"})
    int checkRoleName(Role role);

    @Update(value = {"<script>",
            "update role set",
            "role_name=#{roleName},",
            "menu_id=#{menuId},",
            "role_remark=#{roleRemark},",
            "modified_time=NOW()",
            "where role_id=#{roleId}",
            "</script>"})
    int editRole(Role role);

    @Insert(value = {"<script>",
            "insert role",
            "(role_name,menu_id,role_remark,create_time)",
            "VALUE",
            "(#{roleName},#{menuId},#{roleRemark},NOW())",
            "</script>"})
    int addRole(Role role);

    @Delete(value = {"<script>",
            "delete from role where FIND_IN_SET(role_id,#{ids})",
            "</script>"})
    int delRoleById(String ids);

    @Delete(value = {"<script>",
            "delete from admin where FIND_IN_SET(role_id,#{ids})",
            "</script>"})
    int delAdminByRoleId(String ids);

    @Select(value = {"<script>",
            "select *",
            "from role",
            "<where>",
            "<when test='roleName !=null and roleName!=\"\"'>and role_name like concat('%',#{roleName},'%')</when>",
            "</where>",
            "limit #{startCount},#{pageSize}",
            "</script>"})
    List<Role> getRoleList(ManageParams mps);

    @Select(value = {"<script>",
            "select * from role order by role_id",
            "</script>"})
    List<Role> getAllRoleList();

    @Select("select * from role where role_id=#{roleId}")
    Role getRoleById(String roleId);
}
