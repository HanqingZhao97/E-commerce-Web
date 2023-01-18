package com.yunniu.lease.dao;

import com.yunniu.lease.model.FatherMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FatherMsgMapper {
   
    int deleteByPrimaryKey(Integer id);

    
    @Insert("<script>" +
            "insert into father_msg\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"businessid != null\" >\n" +
            "        businessid,\n" +
            "      </if>\n" +
            "      <if test=\"userid != null\" >\n" +
            "        userid,\n" +
            "      </if>\n" +
            "      <if test=\"equipmentid != null\" >\n" +
            "        equipmentid,\n" +
            "      </if>\n" +
            "      <if test=\"method != null\" >\n" +
            "        method,\n" +
            "      </if>\n" +
            "      <if test=\"endtime != null\" >\n" +
            "        endtime,\n" +
            "      </if>\n" +
            "      <if test=\"equipmentnum != null\" >\n" +
            "        equipmentnum,\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        createtime,\n" +
            "      </if>\n" +
            "      <if test=\"settemperature != null\" >\n" +
            "        settemperature,\n" +
            "      </if>\n" +
            "      <if test=\"equipmentname != null\" >\n" +
            "        equipmentname,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"businessid != null\" >\n" +
            "        #{businessid,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"userid != null\" >\n" +
            "        #{userid,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"equipmentid != null\" >\n" +
            "        #{equipmentid,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"method != null\" >\n" +
            "        #{method,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"endtime != null\" >\n" +
            "        #{endtime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"equipmentnum != null\" >\n" +
            "        #{equipmentnum,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        #{createtime,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"settemperature != null\" >\n" +
            "        #{settemperature,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"equipmentname != null\" >\n" +
            "        #{equipmentname,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int insertSelective(FatherMsg record);

    
    @Select("<script>" +
            "SELECT\n" +
            "equipment_user.sname,\n" +
            "equipment_user.iphone,\n"+
            "father_msg.* \n" +
            "FROM\n" +
            "father_msg \n" +
            "INNER JOIN equipment_user ON father_msg.userid=equipment_user.id\n" +
            "<where>" +
            "<if test=\"userid != null\" >\n" +
            "  AND father_msg.userid = #{userid,jdbcType=INTEGER}\n" +
            "</if>" +
            "<if test=\"iphone != null\" >\n" +
            "  AND equipment_user.iphone = #{iphone,jdbcType=VARCHAR}\n" +
            "</if>" +
            "<if test=\"sname != null\" >\n" +
            "  AND equipment_user.sname = #{sname,jdbcType=VARCHAR}\n" +
            "</if>" +
            "<if test=\"equipmentname != null\" >\n" +
            "  AND father_msg.equipmentname = #{equipmentname,jdbcType=VARCHAR}\n" +
            "</if>" +
            "</where>"+
            " ORDER BY father_msg.endtime desc "+
            " limit ${startCount},${limit}"+
            "</script>")
    List<FatherMsg> selectByPageList(Map<String,Object>map);



    @Select("<script>" +
            "SELECT\n" +
            "distinct father_msg.equipmentname \n" +
            "FROM\n" +
            "father_msg \n" +
            "INNER JOIN equipment_user ON father_msg.userid=equipment_user.id\n" +
            "<where>" +
            "<if test=\"userid != null\" >\n" +
            " AND father_msg.userid = #{userid,jdbcType=INTEGER}\n" +
            "</if>" +
            "<if test=\"iphone != null\" >\n" +
            " AND equipment_user.iphone = #{iphone,jdbcType=VARCHAR}\n" +
            "</if>" +
            "<if test=\"sname != null\" >\n" +
            " AND equipment_user.sname = #{sname,jdbcType=VARCHAR}\n" +
            "</if>\n" +
            "</where>\n"+
            " limit ${startCount},${limit}"+
            "</script>")
    List<FatherMsg> selectByPageListemp(Map<String,Object>map);


    @Select("<script>" +
            "select count(1) FROM father_msg " +
            "INNER JOIN equipment_user ON father_msg.userid=equipment_user.id\n" +
            "<where>" +
            "<if test=\"uid != null\" >\n" +
            "  father_msg.userid = #{userid,jdbcType=INTEGER}\n" +
            "</if>" +
            "</where>"+
            "</script>")
    Integer count(Map<String,Object> map);

    

}