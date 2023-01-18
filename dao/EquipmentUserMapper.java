package com.yunniu.lease.dao;

import com.yunniu.lease.model.EquipmentUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EquipmentUserMapper {
   
    
    
    @Insert("<script>" +
            "insert into equipment_user" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        id," +
            "      </if>" +
            "      <if test=\"openid != null\" >" +
            "        openid," +
            "      </if>" +
            "      <if test=\"iphone != null\" >" +
            "        iphone," +
            "      </if>" +
            "      <if test=\"sname != null\" >" +
            "        sname," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time," +
            "      </if>" +
            "      <if test=\"userImg != null\" >" +
            "        user_img," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        #{id,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"openid != null\" >" +
            "        #{openid,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"iphone != null\" >" +
            "        #{iphone,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"sname != null\" >" +
            "        #{sname,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>"+
            "      <if test=\"userImg != null\" >" +
            "        #{userImg,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(EquipmentUser record);
    
    @Delete("<script>" +
            "DELETE FROM equipment_user WHERE equipment_user.id=#{id};" +
            "</script>")
    int deleteByPrimaryKey(Integer id);



    /**
     * @return 总数据量
     */
    @Select("<script>" +
            "select count(1) from equipment_user" +
            "</script>")
    public Integer count();

    /**
     * 分页查询
     * @param map
     * @return
     */
    @Select({"<script>",
             "SELECT " +
                     "equipment_user.*," +
                     "equipment_user.create_time AS createTime," +
                     "equipment_user.user_img AS userImg " +
                     "FROM " +
                     "equipment_user "+
                     "<where>" +
                     "      <if test=\"id != null\" >" +
                     "        AND id=#{id,jdbcType=INTEGER}" +
                     "      </if>" +
                     "      <if test=\"openid != null\" >" +
                     "        AND openid=#{openid,jdbcType=VARCHAR}" +
                     "      </if>" +
                     "      <if test=\"iphone != null\" >" +
                     "        AND iphone=#{iphone,jdbcType=VARCHAR}" +
                     "      </if>" +
                     "      <if test=\"sname != null\" >" +
                     "        AND sname=#{sname,jdbcType=VARCHAR}" +
                     "      </if>" +
                     "      <if test=\"createTime != null\" >" +
                     "        AND create_time= #{createTime,jdbcType=TIMESTAMP}" +
                     "      </if>"+
                     "</where>"+
                     "ORDER BY equipment_user.create_time desc "+
                     " limit ${startCount},${limit}"+
                     "</script>"})
    public List<EquipmentUser> pageList(Map<String, Object> map);
    
    
    @Select("<script>" +
            "SELECT " +
            "equipment_user.*," +
            "equipment_user.create_time AS createTime," +
            "equipment_user.user_img AS userImg " +
            "FROM " +
            "equipment_user where id=#{id}"+
            "</script>")
    EquipmentUser selectByPrimaryKey(Integer id);



    @Select("<script>" +
            "SELECT " +
            "equipment_user.*," +
            "equipment_user.create_time AS createTime," +
            "equipment_user.user_img AS userImg " +
            "FROM " +
            "equipment_user where openid=#{openid}"+
            "</script>")
    EquipmentUser selectByPrimaryopenid(String openid);
    
    
    @Update("<script>" +
            "update equipment_user " +
            "    <set>" +
            "      <if test=\"openid != null\" >" +
            "        openid = #{openid,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"iphone != null\" >" +
            "        iphone = #{iphone,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"sname != null\" >" +
            "        sname = #{sname,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test=\"userImg != null\" >" +
            "        user_img = #{userImg,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateByPrimaryKeySelective(EquipmentUser record);
    
    
    @Select("<script>" +
            "select * from equipment_user" +
            "</script>")
    public List<EquipmentUser> getList();

    int updateByPrimaryKey(EquipmentUser record);
}