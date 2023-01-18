package com.yunniu.lease.dao;

import com.yunniu.lease.model.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper {
    
    int deleteByPrimaryKey(Integer id);

    @Insert("<script>" +
            "insert into userinfo\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        id,\n" +
            "      </if>\n" +
            "      <if test=\"userid != null\" >\n" +
            "        userid,\n" +
            "      </if>\n" +
            "      <if test=\"msgid != null\" >\n" +
            "        msgid,\n" +
            "      </if>\n" +
            "      <if test=\"istate != null\" >\n" +
            "        istate,\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        createtime,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n" +
            "      <if test=\"id != null\" >\n" +
            "        #{id,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"userid != null\" >\n" +
            "        #{userid,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"msgid != null\" >\n" +
            "        #{msgid,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"istate != null\" >\n" +
            "        #{istate,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"createtime != null\" >\n" +
            "        #{createtime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "    </trim>" +
            "</script>")
    int insert(UserInfo record);

    
    
    @Select("<script>" +
            "SELECT\n" +
            "userinfo.id,\n" +
            "userinfo.userid,\n" +
            "userinfo.istate,\n" +
            "userinfo.createtime,\n" +
            "admininfo.txt,\n" +
            "admininfo.type\n" +
            "FROM\n" +
            "userinfo\n" +
            "INNER JOIN admininfo ON userinfo.msgid = admininfo.id\n"+
            "<where>" +
            "  <if test=\"id != null\" >\n" +
            "    AND id=#{id,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "  <if test=\"userid != null\" >\n" +
            "    AND userid=#{userid,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "  <if test=\"msgid != null\" >\n" +
            "    AND msgid=#{msgid,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "  <if test=\"istate != null\" >\n" +
            "    AND istate=#{istate,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "</where>"+
            " ORDER BY userinfo.createtime desc"+
            "</script>")
    public List<UserInfo> getPageList(Map<String,Object> map);
    
    @Select("<script>" +
            "SELECT\n" +
            "count(1) "+
            "FROM\n" +
            "userinfo\n" +
            "INNER JOIN admininfo ON userinfo.msgid = admininfo.id\n"+
            "<where>" +
            "  <if test=\"id != null\" >\n" +
            "    AND id=#{id,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "  <if test=\"userid != null\" >\n" +
            "    AND userid=#{userid,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "  <if test=\"msgid != null\" >\n" +
            "    AND msgid=#{msgid,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "  <if test=\"istate != null\" >\n" +
            "    AND istate=#{istate,jdbcType=INTEGER}\n" +
            "  </if>\n" +
            "</where>"+
            "</script>")
    public Integer count(Map<String,Object>map);
    
    
    UserInfo selectByPrimaryKey(Integer id);

    
    int updateByPrimaryKeySelective(UserInfo record);

    
    int updateByPrimaryKey(UserInfo record);
}