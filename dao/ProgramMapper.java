package com.yunniu.lease.dao;

import com.yunniu.lease.model.Program;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProgramMapper {

    @Delete("<script>" +
            "delete from program\n" +
            "where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int deleteByPrimaryKey(Integer id);
    
    @Select("<script>" +
            "insert into program\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"sname != null\" >\n" +
            "        sname,\n" +
            "      </if>\n" +
            "      <if test=\"token != null\" >\n" +
            "        token,\n" +
            "      </if>\n" +
            "      <if test=\"url != null\" >\n" +
            "        url,\n" +
            "      </if>\n" +
            "      <if test=\"imgCode != null\" >\n" +
            "        img_code,\n" +
            "      </if>\n" +
            "      <if test=\"appid != null\" >\n" +
            "        appid,\n" +
            "      </if>\n" +
            "      <if test=\"appsecretid != null\" >\n" +
            "        appsecretid,\n" +
            "      </if>\n" +
            "      <if test=\"swxmchno != null\" >\n" +
            "        swxmchno,\n" +
            "      </if>\n" +
            "      <if test=\"businessId != null\" >\n" +
            "        business_id,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"sname != null\" >\n" +
            "        #{sname,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"token != null\" >\n" +
            "        #{token,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"url != null\" >\n" +
            "        #{url,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"imgCode != null\" >\n" +
            "        #{imgCode,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"appid != null\" >\n" +
            "        #{appid,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"appsecretid != null\" >\n" +
            "        #{appsecretid,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"swxmchno != null\" >\n" +
            "        #{swxmchno,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"businessId != null\" >\n" +
            "        #{businessId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "    </trim>" +
            "</script>")
    int insertSelective(Program record);
    
    
    @Select("<script>" +
            "select program.*,program.img_code AS imgCode,program.business_id AS businessId" +
            "    from program\n" +
            "    <where>" +
            "    id = #{id,jdbcType=INTEGER}" +
            "    </where>" +
            "</script>")
    Program selectByPrimaryKey(Integer id);
    
    
    @Select("<sciprt>" +
            "" +
            "</script>")
    List<Program> getPageList(Program record);

    @Select("<script>" +
            "select program.*,program.img_code AS imgCode,business_id AS AS businessId" +
            "    from program\n" +
            "    <where>" +
            "    id = #{id,jdbcType=INTEGER}" +
            "    </where>" +
            "</script>")
    List<Program> selectByPrimaryKeys(Integer id);
    
    
    @Update("<script>" +
            "update program\n" +
            "    <set>\n" +
            "      <if test=\"sname != null\" >\n" +
            "        sname = #{sname,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"token != null\" >\n" +
            "        token = #{token,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"url != null\" >\n" +
            "        url = #{url,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"imgCode != null\" >\n" +
            "        img_code = #{imgCode,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"appid != null\" >\n" +
            "        appid = #{appid,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"appsecretid != null\" >\n" +
            "        appsecretid = #{appsecretid,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"swxmchno != null\" >\n" +
            "        swxmchno = #{swxmchno,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"businessId != null\" >\n" +
            "        business_id = #{businessId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "    </set>\n" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateByPrimaryKeySelective(Program record);
    
    @Select("<script>" +
            " select count(1) from program " +
            "</script>")
    public Integer count();
    
    @Select("<script>" +
            "SELECT\n" +
            "program.*,\n" +
            "program.img_code AS imgCode,\n" +
            "business_id AS businessId,\n" +
            "business.buiness_name AS buinessName,\n" +
            "business.iphone \n" +
            " FROM\n" +
            " program\n" +
            " LEFT JOIN business \n" +
            " ON program.business_id = business.id"+
            "<where>" +
            "      <if test=\"id != null\" >\n" +
            "       AND id=#{id,jdbcType=INTEGER}\n" +
            "      </if>\n" +
            "      <if test=\"sname != null\" >\n" +
            "       AND sname=#{sname,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"token != null\" >\n" +
            "       AND token=#{token,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"url != null\" >\n" +
            "       AND url=#{url,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"imgCode != null\" >\n" +
            "       AND imgCode=#{imgCode,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"appid != null\" >\n" +
            "       AND appid=#{appid,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"appsecretid != null\" >\n" +
            "       AND appsecretid=#{appsecretid,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"swxmchno != null\" >\n" +
            "       AND swxmchno=#{swxmchno,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"iphone != null\" >\n" +
            "       AND business.iphone=#{iphone,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "      <if test=\"buinessName != null\" >\n" +
            "       AND business.buiness_name=#{buinessName,jdbcType=VARCHAR}\n" +
            "      </if>\n" +
            "</where>" +
            " limit ${startCount},${limit}"+
            "</script>")
    List<Program> pageList(Map<String,Object> map);
    
}