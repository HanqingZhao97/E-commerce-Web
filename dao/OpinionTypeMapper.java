package com.yunniu.lease.dao;

import com.yunniu.lease.model.OpinionType;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OpinionTypeMapper {
    
    @Delete("<script>" +
            "delete from opinion_type where id=#{id}" +
            "</script>")
    int deleteByPrimaryKey(Integer id);

    
   @Select("<script>" +
            "select opinion_type.*,opinion_type.create_time AS createTime from opinion_type where id=#{id}" +
            "</script>")
    OpinionType selectId(Integer id);
    
    @Insert("<script>" +
            "insert into opinion_type " +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        id," +
            "      </if>" +
            "      <if test=\"opinionName != null\" >" +
            "        opinion_name," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        #{id,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"opinionName != null\" >" +
            "        #{opinionName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    int insertSelective(OpinionType record);
    
    @Select("<script>" +
            "select count(1) opinion_type"+
            "</script>")
    Integer selectCount();

    /**
     * 分页查询
     * @param 
     * @return
     */
    @Select({"<script>",
            "SELECT opinion_type.*" +
                    " FROM opinion_type " +
                    "<where>"+
                    "<when test='id !=null'> opinion_type.id=#{id}</when>"+
                    "<when test='opinionName !=null'>opinion_type.opinion_name=#{opinionName}</when>"+
                    "</where>"+
                    "ORDER BY opinion_type.create_time desc "+
                    " limit ${startCount},${limit}"+
                    "</script>"})
    List<OpinionType> getPageList(Map<String,Object> map);

    @Select("<script>" +
            "select opinion_type.*,opinion_type.create_time AS createTime from opinion_type where id=#{id}" +
            "</script>")
    OpinionType selectByPrimaryKey(Integer id);
    @Update("<script>" +
            " update opinion_type" +
            "    <set >" +
            "      <if test=\"opinionName != null\" >" +
            "        opinion_name = #{opinionName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "    </set>" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateByPrimaryKeySelective(OpinionType record);
    
    @Select("<script>" +
            "select * from opinion_type;" +
            "</script>")
    List<OpinionType> getList();


}