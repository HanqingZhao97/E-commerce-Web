package com.yunniu.lease.dao;

import com.yunniu.lease.model.Msg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MsgMapper {
   
    @Delete("<script>" +
                "delete from msg where id=#{id}" +
            "</script>")
    int deleteByPrimaryKey(Integer id);

    
    
    @Insert("<script>" +
            "insert into msg\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"setVoltage != null\" >\n" +
            "        set_voltage,\n" +
            "      </if>\n" +
            "      <if test=\"setTime != null\" >\n" +
            "        set_time,\n" +
            "      </if>\n" +
            "      <if test=\"voltageTime != null\" >\n" +
            "        voltage_time,\n" +
            "      </if>\n" +
            "      <if test=\"setCold != null\" >\n" +
            "        set_cold,\n" +
            "      </if>\n" +
            "      <if test=\"getCold != null\" >\n" +
            "        get_cold,\n" +
            "      </if>\n" +
            "      <if test=\"setTemperature != null\" >\n" +
            "        set_temperature,\n" +
            "      </if>\n" +
            "      <if test=\"temperature != null\" >\n" +
            "        temperature,\n" +
            "      </if>\n" +
            "      <if test=\"powerSupply != null\" >\n" +
            "        power_supply,\n" +
            "      </if>\n" +
            "      <if test=\"createMsg != null\" >\n" +
            "        create_msg,\n" +
            "      </if>\n" +
            "      <if test=\"endMsg != null\" >\n" +
            "        end_msg,\n" +
            "      </if>\n" +
            "      <if test=\"equipmentId != null\" >\n" +
            "        equipment_id,\n" +
            "      </if>\n" +
            "      <if test=\"userId != null\" >\n" +
            "        user_id,\n" +
            "      </if>\n" +
            "      <if test=\"fatermsgid != null\" >\n" +
            "        fatermsgid,\n" +
            "      </if>\n" +
            "      <if test=\"numbername != null\" >\n" +
            "        numbername,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"setVoltage != null\" >\n" +
            "        #{setVoltage,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"setTime != null\" >\n" +
            "        #{setTime,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"voltageTime != null\" >\n" +
            "        #{voltageTime,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"setCold != null\" >\n" +
            "        #{setCold,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"getCold != null\" >\n" +
            "        #{getCold,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"setTemperature != null\" >\n" +
            "        #{setTemperature,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"temperature != null\" >\n" +
            "        #{temperature,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"powerSupply != null\" >\n" +
            "        #{powerSupply,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"createMsg != null\" >\n" +
            "        #{createMsg,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"endMsg != null\" >\n" +
            "        #{endMsg,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"equipmentId != null\" >\n" +
            "        #{equipmentId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"userId != null\" >\n" +
            "        #{userId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"fatermsgid != null\" >\n" +
            "        #{fatermsgid,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"numbername != null\" >\n" +
            "        #{numbername,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "    </trim>" +
            "</script>")
    int insertSelective(Msg record);
    

    @Select("<script>" +
            "select msg.*,msg.power_supply AS powerSupply,msg.set_time AS setTime,msg.set_cold from msg " +
            "<where>" +
            " <if test=\"id != null\" >" +
            "    msg.fatermsgid=#{id,jdbcType=INTEGER}"+
            " </if>" +
            "</where>" +
            "</script>")
    List<Msg> selectByPrimaryKey(Integer id);


    @Select("<script>" +
            "select msg.*,msg.power_supply AS powerSupply,msg.set_time AS setTime,msg.set_cold from msg " +
            "<where>" +
            " <if test=\"id != null\" >" +
            "    msg.numbername=#{name,jdbcType=VARCHAR}"+
            " </if>" +
            "</where>" +
            "</script>")
    List<Msg> selectByPrimaryKeys(String name);
    
    
    @Select("<script>" +
                "select count(1) from msg"+
            "</script>")
    public Integer count();
    
    @Select("<script>"+
            " SELECT " +
                "business.saddress," +
                "business.iphone," +
                "equipment.equipment_name AS equipmentName," +
                "equipment_user.iphone AS useriphone," +
                "equipment.equipment_num AS equipmentNum," +
                "msg.set_voltage AS setVoltage," +
                "equipment_user.sname," +
                "msg.create_msg AS createMsg," +
                "msg.end_msg AS endMsg," +
                "msg.id "+
            "FROM " +
            "msg " +
            "LEFT JOIN equipment " +
            "ON msg.equipment_id = equipment.id " +
            "LEFT JOIN equipment_user " +
            "ON equipment_user.id = msg.user_id " +
            "LEFT JOIN business ON " +
            "equipment.business_id=business.id " +
            "<where>" +
                " <if test=\"id != null\">" +
                "    AND msg.id=#{id,jdbcType=INTEGER}" +
                " </if>" +
                " <if test=\"setVoltage != null\" >" +
                "    AND set_voltage=#{setVoltage,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"setTime != null\" >" +
                "    AND set_time=#{setTime,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"voltageTime != null\" >" +
                "    AND   voltage_time=#{voltageTime,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"setCold != null\" >" +
                "    AND   set_cold=#{setCold,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"getCold != null\" >" +
                "    AND   get_cold=#{getCold,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"setTemperature != null\" >" +
                "     AND  set_temperature=#{setTemperature,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"temperature != null\" >" +
                "    AND  temperature=#{temperature,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"powerSupply != null\" >" +
                "    AND    power_supply=#{powerSupply,jdbcType=VARCHAR}" +
                " </if>" +
                " <if test=\"createMsg != null\" >" +
                "    AND    create_msg=#{createMsg,jdbcType=TIMESTAMP}" +
                " </if>" +
                " <if test=\"endMsg != null\" >" +
                "     AND   end_msg=#{endMsg,jdbcType=TIMESTAMP}" +
                " </if>" +
                " <if test=\"equipmentId != null\" >" +
                "    AND    equipment_id=#{equipmentId,jdbcType=INTEGER}" +
                " </if>" +
                " <if test=\"userId != null\" >" +
                "     AND  user_id=#{userId,jdbcType=INTEGER}" +
                " </if>" +
                " <if test=\"useriphone != null\" >" +
                "     AND  equipment_user.iphone=#{useriphone,jdbcType=INTEGER}" +
                " </if>" +
                " <if test=\"iphone != null\" >" +
                "     AND  business.iphone=#{iphone,jdbcType=INTEGER}" +
                " </if>" +
                " <if test=\"sname != null\" >" +
                "     AND  equipment_user.sname=#{sname,jdbcType=INTEGER}" +
                " </if>" +
            "</where>" +
            " ORDER BY msg.create_msg desc "+
            " limit ${startCount},${limit}"+
            "</script>")
    List<Msg> selectPageList(Map<String, Object> map);
    
    int updateByPrimaryKeySelective(Msg record);

    int updateByPrimaryKey(Msg record);
}