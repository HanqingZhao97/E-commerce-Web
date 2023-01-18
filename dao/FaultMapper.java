package com.yunniu.lease.dao;

import com.yunniu.lease.model.Fault;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaultMapper {
    
    @Delete("<script>" +
            "delete from fault where id=#{id}" +
            "</script>")
    int deleteByPrimaryKey(Integer id);
    
    @Insert("<script>" +
            "insert into fault" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        id," +
            "      </if>" +
            "      <if test=\"faultInfo != null\" >" +
            "        fault_info," +
            "      </if>" +
            "      <if test=\"reason != null\" >" +
            "        reason," +
            "      </if>" +
            "      <if test=\"method != null\" >" +
            "        method," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        #{id,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"faultInfo != null\" >" +
            "        #{faultInfo,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"reason != null\" >" +
            "        #{reason,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"method != null\" >" +
            "        #{method,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    int insertSelective(Fault record);

   @Select("<script>" +
           "select fault.id,fault.fault_info,fault.reason,fault.method from fault where id = #{id,jdbcType=INTEGER}" +
           "</script>")
    Fault selectByPrimaryKey(Integer id);
   
   @Select("<script>" +
           "select count(1) from fault where id=#{id}"+
           "</script>")
   Integer count();

    @Select({"<script>",
                    "SELECT " +
                        "fault.id," +
                        "fault.fault_info," +
                        "fault.reason," +
                        "fault.method " +
                        "FROM " +
                        "fault "+
                        "<where>"+
                        "<if test=\"id != null\" >" +
                        "   AND id=#{id,jdbcType=INTEGER}" +
                        "</if>" +
                        "<if test=\"faultInfo != null\" >" +
                        "   AND fault_info=#{faultInfo,jdbcType=VARCHAR}" +
                        "</if>" +
                        "<if test=\"reason != null\" >" +
                        "   AND reason=#{reason,jdbcType=VARCHAR}" +
                        "</if>" +
                        "<if test=\"method != null\" >" +
                        "   AND method=#{method,jdbcType=VARCHAR}" +
                        "</if>"+
                        "</where>"+
                        " limit ${startCount},${limit}"+
                        "</script>"})
   public List<Fault> getPageList(Map<String,Object>map);

   
   @Update("<script>" +
           " update fault" +
           "    <set>" +
           "      <if test=\"faultInfo != null\" >" +
           "        fault_info = #{faultInfo,jdbcType=VARCHAR}," +
           "      </if>" +
           "      <if test=\"reason != null\" >" +
           "        reason = #{reason,jdbcType=VARCHAR}," +
           "      </if>" +
           "      <if test=\"method != null\" >" +
           "        method = #{method,jdbcType=VARCHAR}," +
           "      </if>" +
           "    </set>" +
           "    where id = #{id,jdbcType=INTEGER}" +
           "</script>")
    int updateByPrimaryKeySelective(Fault record);
   
   
   

   
   
}