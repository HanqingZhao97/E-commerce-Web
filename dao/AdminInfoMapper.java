package com.yunniu.lease.dao;

import com.yunniu.lease.model.AdminInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
@Mapper
public interface AdminInfoMapper {
    
    @Insert("<script>" +
            " insert into admininfo\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"sname != null\" >\n" +
            "        sname,\n" +
            "      </if>\n" +
            "      <if test=\"type != null\" >\n" +
            "        type,\n" +
            "      </if>\n" +
            "      <if test=\"istate != null\" >\n" +
            "        istate,\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        createtime,\n" +
            "      </if>\n" +
            "      <if test=\"txt != null\" >\n" +
            "        txt,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"sname != null\" >\n" +
            "        #{sname,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"type != null\" >\n" +
            "        #{type,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"istate != null\" >\n" +
            "        #{istate,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        #{createtime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"txt != null\" >\n" +
            "        #{txt,jdbcType=LONGVARCHAR},\n" +
            "      </if>\n" +
            "    </trim>" +
            "</script>")
    int insertSelective(AdminInfo record);
    
    @Delete("<script>" +
            "delete from admininfo where id=#{id,jdbcType=INTEGER}" +
            "</script>")
    Integer deleteByPrimaryKey(Integer id);
    
    @Update("<script>" +
            "update admininfo\n" +
            "    <set >\n" +
            "      <if test=\"sname != null\" >\n" +
            "        sname = #{sname,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"type != null\" >\n" +
            "        type = #{type,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"istate != null\" >\n" +
            "        istate = #{istate,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        createtime = #{createtime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"txt != null\" >\n" +
            "        txt = #{txt,jdbcType=LONGVARCHAR},\n" +
            "      </if>\n" +
            "    </set>\n" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateByPrimaryKeySelective(AdminInfo record);
    
    @Select("<script>" +
            "select * from admininfo " +
            "<where>" +
            "<if test=\"id != null\" >\n" +
            "    AND id=#{id,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "</where>"+
            "</script>")
    AdminInfo selectByPrimaryKey(Integer id);
    
    @Select("<script>" +
            "select * from admininfo " +
            "<where>" +
            "<if test=\"id != null\" >\n" +
            "    AND id=#{id,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "<if test=\"sname != null\" >\n" +
            "    AND sname=#{sname,jdbcType=VARCHAR}\n" +
            "</if>\n" +
            "<if test=\"type != null\" >\n" +
            "    AND type=#{type,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "<if test=\"istate != null\" >\n" +
            "    AND istate=#{istate,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "<if test=\"createtime != null\" >\n" +
            "    AND createtime=#{createtime,jdbcType=TIMESTAMP}\n" +
            "</if>\n" +
            "<if test=\"txt != null\" >\n" +
            "    AND txt=#{txt,jdbcType=LONGVARCHAR}\n" +
            "</if>" +
            "</where>"+
            " ORDER BY admininfo.createtime desc"+
            " limit ${startCount},${limit} "+
            "</script>")
    List<AdminInfo> pageList(Map<String,Object>map);


    @Select("<script>" +
            "select count(1) from admininfo " +
            "<where>" +
            "<if test=\"id != null\" >\n" +
            "    AND id=#{id,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "<if test=\"sname != null\" >\n" +
            "    AND sname=#{sname,jdbcType=VARCHAR}\n" +
            "</if>\n" +
            "<if test=\"type != null\" >\n" +
            "    AND type=#{type,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "<if test=\"istate != null\" >\n" +
            "    AND istate=#{istate,jdbcType=INTEGER}\n" +
            "</if>\n" +
            "<if test=\"createtime != null\" >\n" +
            "    AND createtime=#{createtime,jdbcType=TIMESTAMP}\n" +
            "</if>\n" +
            "<if test=\"txt != null\" >\n" +
            "    AND txt=#{txt,jdbcType=LONGVARCHAR}\n" +
            "</if>" +
            "</where>"+
            "</script>")
    Integer count(Map<String,Object> map);

}