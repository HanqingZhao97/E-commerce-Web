package com.yunniu.lease.dao;

import com.yunniu.lease.model.EquipmentType;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EquipmentTypeMapper {
    
    @Delete("<script>" +
            "DELETE FROM equipment_type WHERE id=#{id};" +
            "</script>")
    int deleteByPrimaryKey(Integer id);

    @Insert("<script>" +
            "  insert into equipment_type" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        id," +
            "      </if>" +
            "      <if test=\"typeName != null\" >" +
            "        type_name," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        #{id,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"typeName != null\" >" +
            "        #{typeName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    int insert(EquipmentType record);
    
   


    EquipmentType selectByPrimaryKey(Integer id);
    
    @Update("<script>" +
            " update equipment_type" +
            "    <set >" +
            "      <if test=\"typeName != null\" >" +
            "        type_name = #{typeName,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateByPrimaryKeySelective(EquipmentType record);



    /**
     * 分页查询
     * @param equipmentType
     * @return
     */
    @Select({"<script>",
              "SELECT equipment_type.id,equipment_type.type_name AS typeName,equipment_type.create_time AS createTime" +
              " FROM equipment_type " +
              "<where>"+
                "<when test='id !=null'> equipment_type.id=#{id}</when>"+
                "<when test='typeName !=null'>equipment_type.type_name=#{typeName}</when>"+
                "</where>"+
                "ORDER BY equipment_type.create_time desc "+
                " limit ${startCount},${limit}"+
            "</script>"})
    List<EquipmentType> getPageList(Map<String, Object> map);
    
    
  


    @Select({"<script>",
            "SELECT count(1) FROM equipment_type",
            "</script>"})
    Integer getCount(Map<String, Object> map);
    
    
    
    
    @Select("<script>" +
            "SELECT equipment_type.id,equipment_type.type_name AS typeName,equipment_type.create_time AS createTime" +
            " FROM equipment_type " +
            "<where>"+
            "<when test='id !=null'> equipment_type.id=#{id}</when>"+
            "</where>"+
            "</script>")
    EquipmentType selectId(Integer id);


    @Select("<script>" +
            "SELECT equipment_type.id,equipment_type.type_name AS typeName,equipment_type.create_time AS createTime" +
            " FROM equipment_type"+
            "</script>")
    List<EquipmentType> selectAll();
}