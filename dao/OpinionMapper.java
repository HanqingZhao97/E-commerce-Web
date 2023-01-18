package com.yunniu.lease.dao;

import com.yunniu.lease.model.Opinion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OpinionMapper {
    
    @Delete("<script>" +
            "delete from opinion where id=#{id}" +
            "</script>")
    int deleteByPrimaryKey(Integer id);
    
   
    @Insert("<script>" +
            "insert into opinion\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"img != null\" >\n" +
            "        img,\n" +
            "      </if>\n" +
            "      <if test=\"createTime != null\" >\n" +
            "        create_time,\n" +
            "      </if>\n" +
            "      <if test=\"opinionMsg != null\" >\n" +
            "        opinion_msg,\n" +
            "      </if>\n" +
            "      <if test=\"opiniontypeId != null\" >\n" +
            "        opiniontypeId,\n" +
            "      </if>\n" +
            "      <if test=\"userId != null\" >\n" +
            "        userId,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"img != null\" >\n" +
            "        #{img,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"createTime != null\" >\n" +
            "        #{createTime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"opinionMsg != null\" >\n" +
            "        #{opinionMsg,jdbcType=LONGVARCHAR},\n" +
            "      </if>" +
            "      <if test=\"opiniontypeId != null\" >\n" +
            "        #{opiniontypeId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"userId != null\" >\n" +
            "        #{userId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      </trim>" +
            "</script>")
    int insertSelective(Opinion record);
    
    @Select("<script>" +
            "select * from opinion where id=#{id}" +
            "</script>")
    Opinion selectByPrimaryKey(Integer id);
    
    
    @Select("<sciprt>" +
            " update opinion\n" +
            "    <set >\n" +
            "      <if test=\"img != null\" >\n" +
            "        img = #{img,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"createTime != null\" >\n" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"opinionMsg != null\" >\n" +
            "        opinion_msg = #{opinionMsg,jdbcType=LONGVARCHAR},\n" +
            "      </if>\n" +
            "    </set>\n" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateByPrimaryKeySelective(Opinion record);
    
    
    //分页查询
    @Select("<script>" +
            "SELECT\n" +
            "opinion.id,\n" +
            "opinion.create_time AS createTime,\n" +
            "opinion.opinion_msg AS opinionMsg,\n" +
            "opinion.img,\n" +
            "opinion_type.id AS opiniontypeId,\n" +
            "opinion_type.opinion_name AS opinionName,\n" +
            "equipment_user.sname AS userName,\n" +
            "equipment_user.iphone\n" +
            "FROM\n" +
            "opinion\n" +
            "LEFT JOIN opinion_type ON opinion.opiniontypeId=opinion_type.id\n" +
            "LEFT JOIN equipment_user ON equipment_user.id=opinion.userId "+
            "<where>" +
                "<if test=\"id != null\" >\n" +
                "  AND id=#{id,jdbcType=INTEGER}\n" +
                "</if>\n" +
                "<if test=\"img != null\" >\n" +
                "  AND img=#{img,jdbcType=VARCHAR}\n" +
                "</if>\n" +
                "<if test=\"createTime != null\" >\n" +
                "  AND create_time=#{createTime,jdbcType=TIMESTAMP}\n" +
                "</if>\n" +
                "<if test=\"opinionMsg != null\" >\n" +
                "  AND opinion_msg=#{opinionMsg,jdbcType=LONGVARCHAR}\n" +
                "</if>" +
                "<if test=\"iphone != null\" >\n" +
                "  AND equipment_user.iphone=#{iphone,jdbcType=VARCHAR}\n" +
                "</if>" +
            "</where>"+
            " ORDER BY opinion.create_time desc "+
            " limit ${startCount},${limit}"+
            "</script>")
    public List<Opinion> getPageList(Map<String,Object>map);
    
    
    @Select("<script>" +
            " select count(1) from opinion" +
            "</script>")
    public Integer count();

 
    
}