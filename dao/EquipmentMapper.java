package com.yunniu.lease.dao;

import com.yunniu.lease.model.Business;
import com.yunniu.lease.model.Equipment;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EquipmentMapper {
    
    @Delete("<script>" +
            "delete from equipment where id=#{id}" +
            "</script>")
    int deleteByPrimaryKey(Integer id);

    
    
    @Insert("<script>" +
            "insert into equipment" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        id," +
            "      </if>" +
            "      <if test=\"equipmentName != null\" >" +
            "        equipment_name," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time," +
            "      </if>" +
            "      <if test=\"equipmentTypeid != null\" >" +
            "        equipment_typeid," +
            "      </if>" +
            "      <if test=\"istate != null\" >" +
            "        istate," +
            "      </if>" +
            "      <if test=\"voltage != null\" >" +
            "        voltage," +
            "      </if>" +
            "      <if test=\"timeLength != null\" >" +
            "        time_length," +
            "      </if>" +
            "      <if test=\"cooling != null\" >" +
            "        cooling," +
            "      </if>" +
            "      <if test=\"temperatureIstate != null\" >" +
            "        temperature_istate," +
            "      </if>" +
            "      <if test=\"img != null\" >" +
            "        img," +
            "      </if>" +
            "      <if test=\"businessId != null\" >" +
            "        business_id," +
            "      </if>" +
            "      <if test=\"equipmentNum != null\" >" +
            "        equipment_num," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" +
            "      <if test=\"id != null\" >" +
            "        #{id,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"equipmentName != null\" >" +
            "        #{equipmentName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test=\"equipmentTypeid != null\" >" +
            "        #{equipmentTypeid,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"istate != null\" >" +
            "        #{istate,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"voltage != null\" >" +
            "        #{voltage,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"timeLength != null\" >" +
            "        #{timeLength,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"cooling != null\" >" +
            "        #{cooling,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"temperatureIstate != null\" >" +
            "        #{temperatureIstate,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"img != null\" >" +
            "        #{img,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"businessId != null\" >" +
            "        #{businessId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"equipmentNum != null\" >" +
            "        #{equipmentNum,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    int insertSelective(Equipment record);
    
    
    @Select("<script>" +
                "select " +
                "equipment.*,"+
                "equipment.id," +
                "equipment.equipment_name AS equipmentName,"+
                "equipment.equipment_num AS equipmentNum,"+
                "equipment.time_length AS timeLength,"+
                "equipment.temperature_istate AS temperatureIstate,"+
                "equipment.business_id AS business_Id,"+
                "equipment.equipment_typeid AS equipmentTypeid"+
                " from " +
                " equipment where id=#{id}" +
            "</script>")
    Equipment selectByPrimaryKey(Integer id);
    
    //分页查询
    @Select({"<script>",
             "SELECT " +
                     "equipment.id," +
                     "equipment.equipment_name AS equipmentName," +
                     "equipment.equipment_name, " +
                     "equipment.img," +
                     "business.iphone," +
                     "business.saddress," +
                     "business.buiness_name AS buinessName," +
                     "business.buinesimg," +
                     "equipment_type.type_name AS typeName " +
                     " FROM " +
                     "equipment," +
                     "equipment_type," +
                     "business " +
                     " WHERE " +
                     " equipment.equipment_typeid = equipment_type.id " +
                     " AND " +
                     "equipment.business_id = business.id "+
                     "      <if test=\"id != null\">" +
                     "        AND equipment.id=#{id,jdbcType=INTEGER}" +
                     "      </if>" +
                     "      <if test=\"equipmentName != null\" >" +
                     "        AND equipment_name=#{equipmentName,jdbcType=VARCHAR}" +
                     "      </if>" +
                     "      <if test=\"createTime != null\" >" +
                     "        AND create_time=#{createTime,jdbcType=TIMESTAMP}" +
                     "      </if>" +
                     "      <if  test=\"equipmentTypeid != null\" >" +
                     "        AND equipment_typeid=#{equipmentTypeid,jdbcType=INTEGER}" +
                     "      </if>" +
                     "      <if test=\"istate != null\" >" +
                     "        AND istate=#{istate,jdbcType=INTEGER}" +
                     "      </if>" +
                     "      <if  test=\"voltage != null\" >" +
                     "        AND voltage=#{voltage,jdbcType=VARCHAR}" +
                     "      </if>" +
                     "      <if  test=\"timeLength != null\" >" +
                     "        AND time_length=#{timeLength,jdbcType=VARCHAR}," +
                     "      </if>" +
                     "      <if test=\"cooling != null\" >" +
                     "        AND cooling=#{cooling,jdbcType=VARCHAR}" +
                     "      </if>" +
                     "      <if  test=\"temperatureIstate != null\" >" +
                     "        AND temperature_istate=#{temperatureIstate,jdbcType=VARCHAR}" +
                     "      </if>"+
                     "      <if  test=\"buinessName != null\" >" +
                     "        AND business.buiness_name=#{buinessName,jdbcType=VARCHAR}" +
                     "      </if>"+
                     "      <if  test=\"typeName != null\" >" +
                     "        AND equipment_type.type_name=#{typeName,jdbcType=VARCHAR}" +
                     "      </if>"+
                     "ORDER BY equipment.create_time desc "+
                     " limit ${startCount},${limit}"+
                    "</script>"})
    public List<Equipment> getDaoPageList(Map<String, Object> map);

    
    //总数量查询
    @Select("<script>" +
            "select count(1) FROM equipment;" +
            "</script>")
    public Integer count();
    
    
    //修改数据库
    @Update("<script>" +
            "update equipment" +
            "    <set >" +
            "      <if test=\"equipmentName != null\" >" +
            "        equipment_name = #{equipmentName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"createTime != null\" >" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test=\"equipmentTypeid != null\" >" +
            "        equipment_typeid = #{equipmentTypeid,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"istate != null\" >" +
            "        istate = #{istate,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"voltage != null\" >" +
            "        voltage = #{voltage,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"timeLength != null\" >" +
            "        time_length = #{timeLength,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"cooling != null\" >" +
            "        cooling = #{cooling,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"temperatureIstate != null\" >" +
            "        temperature_istate = #{temperatureIstate,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"img != null\" >" +
            "        img=#{img,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"businessId != null\" >" +
            "        business_id=#{businessId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test=\"equipmentNum != null\" >" +
            "        equipment_num=#{equipmentNum,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public Integer updateEquipemnt(Equipment equipment);

    @Select("<script>" +
                "select * from equipment" +
            "</script>")
    public List<Equipment> getList();
}