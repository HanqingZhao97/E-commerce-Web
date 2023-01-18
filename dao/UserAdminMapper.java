package com.yunniu.lease.dao;

import com.yunniu.lease.model.UserAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserAdminMapper {
    //1.查询用户
    @Select(value = {"<script>",
            "SELECT * FROM user_admin",
            "</script>"})
    public List<UserAdmin> getUserAdmin();

    @Select(value = {"<script>",
            "SELECT * FROM user_admin WHERE id = #{id}",
            "</script>"})
    public UserAdmin getUserID(Integer id);

    //2.添加用户
    @Insert(value = {"<script>",
            "INSERT INTO user_admin(`name`,`password`, address, email, iphone)",
            "VALUES(#{name},#{password},#{address},#{email},#{iphone})",
            "</script>"})
    public Integer addUser(UserAdmin userAdmin);


    //3.删除用户
    @Delete("<script>" +
            "DELETE FROM user_admin WHERE id=#{id,jdbcType=INTEGER}" +
            "</script>")
    public Integer deleteUser(Integer id);

    //4.修改用户
    @Update("<script>"+
            "UPDATE user_admin SET `name`=#{name},`password`=#{password}, address=#{address}, email=#{email}, iphone=#{iphone} "+
            "WHERE id = #{id}"+
            "</script>")
    public Integer updateUser(UserAdmin record);
}
