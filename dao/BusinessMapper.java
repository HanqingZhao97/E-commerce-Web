package com.yunniu.lease.dao;

import com.yunniu.lease.model.Business;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessMapper {

   @Delete(value = {"<script>",
            " delete from business" ,
                    "  where id = #{id}",
            "</script>"})
    int deleteByPrimaryKey(Integer id);


    @Insert(value = {"<script>",
            "insert into business " +
                    "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" +
                    "      <if test=\"id != null\" >" +
                    "        id," +
                    "      </if>" +
                    "      <if test=\"buinessName != null\" >" +
                    "        buiness_name," +
                    "      </if>" +
                    "      <if test=\"createTime != null\" >" +
                    "        create_time," +
                    "      </if>" +
                    "      <if test=\"dlng != null\" >" +
                    "        dlng," +
                    "      </if>" +
                    "      <if test=\"dlat != null\" >" +
                    "        dlat," +
                    "      </if>" +
                    "      <if test=\"saddress != null\" >" +
                    "        saddress," +
                    "      </if>" +
                    "      <if test=\"sprovince != null\" >" +
                    "        sprovince," +
                    "      </if>" +
                    "      <if test=\"scity != null\" >" +
                    "        scity," +
                    "      </if>" +
                    "      <if test=\"sregion != null\" >" +
                    "        sregion," +
                    "      </if>" +
                    "      <if test=\"istate != null\" >" +
                    "        istate," +
                    "      </if>" +
                    "      <if test=\"buinesImgcode != null\" >" +
                    "        buines_imgcode," +
                    "      </if>" +
                    "      <if test=\"iphone != null\" >" +
                    "        iphone," +
                    "      </if>" +
                    "      <if test=\"info != null\" >" +
                    "        info," +
                    "      </if>" +
                    "      <if test=\"buinesimg != null\" >" +
                    "        buinesimg," +
                    "      </if>" +
                    "    </trim>" +
                    "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" +
                    "      <if test=\"id != null\" >" +
                    "        #{id,jdbcType=INTEGER}," +
                    "      </if>" +
                    "      <if test=\"buinessName != null\" >" +
                    "        #{buinessName,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"createTime != null\" >" +
                    "        #{createTime,jdbcType=TIMESTAMP}," +
                    "      </if>" +
                    "      <if test=\"dlng != null\" >" +
                    "        #{dlng,jdbcType=DECIMAL}," +
                    "      </if>" +
                    "      <if test=\"dlat != null\" >" +
                    "        #{dlat,jdbcType=DECIMAL}," +
                    "      </if>" +
                    "      <if test=\"saddress != null\" >" +
                    "        #{saddress,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"sprovince != null\" >" +
                    "        #{sprovince,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"scity != null\" >" +
                    "        #{scity,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"sregion != null\" >" +
                    "        #{sregion,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"istate != null\" >" +
                    "        #{istate,jdbcType=INTEGER}," +
                    "      </if>" +
                    "      <if test=\"buinesImgcode != null\" >" +
                    "        #{buinesImgcode,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"iphone != null\" >" +
                    "        #{iphone,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "      <if test=\"info != null\" >" +
                    "        #{info,jdbcType=LONGVARCHAR}," +
                    "      </if>" +
                    "      <if test=\"buinesimg != null\" >" +
                    "         #{buinesimg,jdbcType=VARCHAR}," +
                    "      </if>" +
                    "    </trim>",
            "</script>"})
    int insertSelective(Business record);
    
    @Select("<script>"+
            " select "+
            "business.*" +
            ",business.buiness_name AS buinessName"+
            ",business.buines_imgcode AS buinesImgcode "+
            "   from business" +
            "   where id = #{id,jdbcType=INTEGER}"+
            "</script>")
    Business selectByPrimaryKey(Integer id);


    @Select("<script>"+
            " select * "+
            "   from business"+
            "</script>")
    List<Business> selectByPrimarys(Business business);
    
    
    //查询总数据量
    @Select("<script>"+
            " select count(1)"+
            "   from business"+
            "</script>")
    public Integer selectDataCount();
    
    //分页查询
    @Select({"<script>",
                "SELECT " +
                    "business.*" +
                    ",business.buiness_name AS buinessName"+
                    ",business.buines_imgcode AS buinesImgcode "+
                 "FROM business"+
                    "<where>"+
                        "      <if test=\"id != null\" >" +
                        "           AND id=#{id,jdbcType=INTEGER}" +
                        "      </if>" +
                        "      <if test=\"buinessName != null\" >" +
                        "           AND  buiness_name LIKE  CONCAT('%',#{buinessName},'%')" +
                        "      </if>" +
                        "      <if test=\"dlng != null\" >" +
                        "           AND  dlng=#{dlng,jdbcType=DECIMAL}" +
                        "      </if>" +
                        "      <if test=\"dlat != null\" >" +
                        "           AND  dlat=#{dlat,jdbcType=DECIMAL}" +
                        "      </if>" +
                        "      <if test=\"saddress != null\" >" +
                        "           AND  saddress=#{saddress,jdbcType=VARCHAR}" +
                        "      </if>" +
                        "      <if test=\"sprovince != null\" >" +
                        "           AND  sprovince=#{sprovince,jdbcType=VARCHAR}" +
                        "      </if>" +
                        "      <if test=\"scity != null\" >" +
                        "           AND  scity=#{scity,jdbcType=VARCHAR}" +
                        "      </if>" +
                        "      <if test=\"sregion != null\" >" +
                        "           AND  sregion=#{sregion,jdbcType=VARCHAR}" +
                        "      </if>" +
                        "      <if test=\"istate != null\" >" +
                        "           AND  istate=#{istate,jdbcType=INTEGER}" +
                        "      </if>" +
                        "      <if test=\"buinesImgcode != null\" >" +
                        "           AND buines_imgcode=#{buinesImgcode,jdbcType=VARCHAR}" +
                        "      </if>" +
                        "      <if test=\"iphone != null\" >" +
                        "           AND business.iphone=#{iphone,jdbcType=VARCHAR}" +
                        "      </if>" +
                        "      <if test=\"info != null\" >" +
                        "           AND info=#{info,jdbcType=LONGVARCHAR}" +
                        "      </if>"+
                    "</where>"+
                    " ORDER BY business.create_time desc "+
                    " limit ${startCount},${limit}"+
             "</script>"})
    public List<Business> getDaoPageList(Map<String, Object> map);
    
    
   @Update("<script>"+
            " update business"+
            "    <set >" +
            "      <if test=\"buinessName != null\" >"+
            "        buiness_name = #{buinessName,jdbcType=VARCHAR},"+
            "      </if>" +
            "      <if test=\"createTime != null\" >"+
            "        create_time = #{createTime,jdbcType=TIMESTAMP},"+
            "      </if>"+
            "      <if test=\"dlng != null\" >" +
            "        dlng = #{dlng,jdbcType=DECIMAL}," +
            "      </if>"+
            "      <if test=\"dlat != null\" >"+
            "        dlat = #{dlat,jdbcType=DECIMAL},"+
            "      </if>"+
            "      <if test=\"saddress != null\" >"+
            "        saddress = #{saddress,jdbcType=VARCHAR},"+
            "      </if>" +
            "      <if test=\"sprovince != null\" >" +
            "        sprovince = #{sprovince,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"scity != null\" >" +
            "        scity = #{scity,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test=\"sregion != null\" >"+
            "        sregion = #{sregion,jdbcType=VARCHAR},"+
            "      </if>"+
            "      <if test=\"istate != null\" >"+
            "        istate = #{istate,jdbcType=INTEGER}," +
            "      </if>"+
            "      <if test=\"buinesImgcode != null\" >" +
            "        buines_imgcode = #{buinesImgcode,jdbcType=VARCHAR},"+
            "      </if>"+
            "      <if test=\"iphone != null\" >"+
            "        iphone = #{iphone,jdbcType=VARCHAR},"+
            "      </if>"+
            "      <if test=\"info != null\" >"+
            "        info = #{info,jdbcType=LONGVARCHAR},"+
            "      </if>"+
            "      <if test=\"info != null\" >"+
            "        buinesimg = #{buinesimg,jdbcType=VARCHAR},"+
            "      </if>"+
            "    </set>"+
            "    where id = #{id,jdbcType=INTEGER}"+
            "</script>")
    int updateByPrimaryKeySelective(Business record);
}