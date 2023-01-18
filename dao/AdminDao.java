package com.yunniu.lease.dao;

import com.yunniu.lease.model.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDao {


//    @Select(value = {"<script>",
//            "select * ",
//            "from admin a",
//            "<where>",
//            "<when abolishState = #{account} ></when>",
//            "</where>",
//            "</script>"})
//    Admin fgetAdmin(String account);


    @Select(value = {"<script>",
            "select a.*,r.role_name",
            "from admin a",
            "LEFT JOIN role r",
            "on a.role_id=r.role_id",
            "<where>",
            "<when test='abolishState !=null and abolishState!=\"\"'>and a.abolish_state=#{abolishState}</when>",
            "<when test='roleId !=null and roleId!=\"\"'>and a.role_id=#{roleId}</when>",
            "<when test='adminName !=null and adminName !=\"\"'>and a.admin_name like concat('%',#{adminName},'%')</when>",
            "<when test='adminAccount !=null and adminAccount !=\"\"'>and a.admin_account like concat('%',#{adminAccount},'%')</when>",
            "</where>",
            "ORDER BY a.admin_id",
            "limit ${startCount},${limit}",
            "</script>"})
    List<Admin> getAdminList(Map<String, Object> mps);

    @Select(value = {"<script>",
            "select count(1)",
            "from admin a",
            "LEFT JOIN role r",
            "on a.role_id=r.role_id",
            "<where>",
            "<when test='abolishState !=null and abolishState!=\"\"'>and a.abolish_state=#{abolishState}</when>",
            "<when test='roleId !=null and roleId!=\"\"'>and a.role_id=#{roleId}</when>",
            "<when test='adminName !=null and adminName !=\"\"'>and a.admin_name like concat('%',#{adminName},'%')</when>",
            "<when test='adminAccount !=null and adminAccount !=\"\"'>and a.admin_account like concat('%',#{adminAccount},'%')</when>",
            "</where>",
            "</script>"})
    int getAdminListCount(Map<String, Object> mps);


    @Select(value = {"<script>",
            "select a.*,r.role_name",
            "from admin a",
            "LEFT JOIN role r",
            "on a.role_id=r.role_id",
            "where a.admin_id=#{adminId}",
            "</script>"})
    Admin getAdminById(String adminId);

    @Update(value = {"<script>",
            "update admin SET",
            "<when test='adminAccount !=null and adminAccount !=\"\"'>admin_account=#{adminAccount},</when>",
            "<when test='roleId !=null and roleId !=\"\"'>role_id=#{roleId},</when>",
            "<when test='adminPassword !=null and adminPassword !=\"\"'>admin_password=#{adminPassword},</when>",
            "<when test='adminSalt !=null and adminSalt !=\"\"'>admin_salt=#{adminSalt},</when>",
            "<when test='adminName !=null and adminName !=\"\"'>admin_name=#{adminName},</when>",
            "<when test='adminHeadLink !=null and adminHeadLink !=\"\"'>admin_head_link=#{adminHeadLink},</when>",
            "<when test='adminRemark !=null and adminRemark !=\"\"'>admin_remark=#{adminRemark},</when>",
            "<when test='adminAddress !=null and adminAddress !=\"\"'>admin_address=#{adminAddress},</when>",
            "<when test='adminPhone !=null and adminPhone !=\"\"'>admin_phone=#{adminPhone},</when>",
            "<when test='abolishState !=null and abolishState !=\"\"'>abolish_state=#{abolishState},</when>",
            "modified_time=NOW()",
            "where admin_id=#{adminId}",
            "</script>"})
    int editAdmin(Admin admin);

    @Insert(value = {"<script>",
            "insert into admin(",
            "<when test='adminAccount !=null and adminAccount !=\"\"'>admin_account,</when>",
            "<when test='roleId !=null and roleId !=\"\"'>role_id,</when>",
            "<when test='adminPassword !=null and adminPassword !=\"\"'>admin_password,</when>",
            "<when test='adminSalt !=null and adminSalt !=\"\"'>admin_salt,</when>",
            "<when test='adminName !=null and adminName !=\"\"'>admin_name,</when>",
            "<when test='adminHeadLink !=null and adminHeadLink !=\"\"'>admin_head_link,</when>",
            "<when test='adminRemark !=null and adminRemark !=\"\"'>admin_remark,</when>",
            "<when test='adminAddress !=null and adminAddress !=\"\"'>admin_address,</when>",
            "<when test='adminPhone !=null and adminPhone !=\"\"'>admin_phone,</when>",
            "create_time)",
            "values(",
            "<when test='adminAccount !=null and adminAccount !=\"\"'>#{adminAccount},</when>",
            "<when test='roleId !=null and roleId !=\"\"'>#{roleId},</when>",
            "<when test='adminPassword !=null and adminPassword !=\"\"'>#{adminPassword},</when>",
            "<when test='adminSalt !=null and adminSalt !=\"\"'>#{adminSalt},</when>",
            "<when test='adminName !=null and adminName !=\"\"'>#{adminName},</when>",
            "<when test='adminHeadLink !=null and adminHeadLink !=\"\"'>#{adminHeadLink},</when>",
            "<when test='adminRemark !=null and adminRemark !=\"\"'>#{adminRemark},</when>",
            "<when test='adminAddress !=null and adminAddress !=\"\"'>#{adminAddress},</when>",
            "<when test='adminPhone !=null and adminPhone !=\"\"'>#{adminPhone},</when>",
            "NOW())",
            "</script>"})
    int addAdmin(Admin admin);

    @Delete(value = {"<script>",
            "delete from admin where FIND_IN_SET(admin_id,#{ids}) and admin_id!=1",
            "</script>"})
    int delAdmin(String ids);


    @Select(value = {"<script>",
            "select a.*,r.role_name ",
            "from admin a ",
            "LEFT JOIN role r ",
            "on a.role_id=r.role_id ",
            "where a.admin_account=#{account}  limit 1",
            "</script>"})
    Admin getAdminByAccount(String account);

    @Update(value = {"<script>",
            "update admin SET last_login_time=now()",
            "where admin_account=#{adminAccount}",
            "</script>"})
    int updateLoginTime(String adminAccount);


    @Update(value = {"<script>",
            "update admin SET",
            "<when test='adminPassword !=null and adminPassword !=\"\"'>admin_password=#{adminPassword},</when>",
            "<when test='adminSalt !=null and adminSalt !=\"\"'>admin_salt=#{adminSalt},</when>",
            "<when test='adminName !=null and adminName !=\"\"'>admin_name=#{adminName},</when>",
            "<when test='adminHeadLink !=null and adminHeadLink !=\"\"'>admin_head_link=#{adminHeadLink},</when>",
            "<when test='adminRemark !=null and adminRemark !=\"\"'>admin_remark=#{adminRemark},</when>",
            "<when test='adminAddress !=null and adminAddress !=\"\"'>admin_address=#{adminAddress},</when>",
            "<when test='adminPhone !=null and adminPhone !=\"\"'>admin_phone=#{adminPhone},</when>",
            "modified_time=NOW()",
            "where admin_id=#{adminId}",
            "</script>"})
    int editAdminInfo(Admin admin);

}
