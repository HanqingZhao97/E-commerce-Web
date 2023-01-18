package com.yunniu.lease.dao;


import com.yunniu.lease.model.Banner;
import com.yunniu.lease.model.Dictionary;
import com.yunniu.lease.model.GoodsClass;
import com.yunniu.lease.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsClassDao {


    @Select(value = {"<script>",
            "SELECT g.*,g1.goods_class_name as goodsClassPname FROM goods_class g",
            "left JOIN goods_class g1",
            "on g.goods_class_pid=g1.goods_class_id",
            "<where>",
            "<when test='goodsClassName !=null and goodsClassName!=\"\"'>and g.goods_class_name like concat('%',#{goodsClassName},'%')</when>",
            "<when test='goodsClassId !=null and goodsClassId!=\"\"'>and g.goods_class_pid=#{goodsClassId}</when>",
            "and g.goods_class_pid is not null ",
            "</where>",
            "ORDER BY g.goods_class_order ",
            "limit ${startCount},${limit}",
            "</script>"})
    List<GoodsClass> getGoodsClassList(Map<String, Object> params);

    @Select(value = {"<script>",
            "SELECT count(1)  FROM goods_class g",
            "left JOIN goods_class g1",
            "on g.goods_class_pid=g1.goods_class_id",
            "<where>",
            "<when test='goodsClassName !=null and goodsClassName!=\"\"'>and g.goods_class_name like concat('%',#{goodsClassName},'%')</when>",
            "<when test='goodsClassId !=null and goodsClassId!=\"\"'>and g.goods_class_pid=#{goodsClassId}</when>",
            "and g.goods_class_pid is not null ",
            "</where>",
            "</script>"})
    Integer getGoodsClassListCount(Map<String, Object> params);


    @Select(value = {"<script>",
            "SELECT *  FROM goods_class where goods_class_pid = '1' ",
            "</script>"})
    List<GoodsClass> getPgoodsClassList();


    @Select(value = {"<script>",
            "SELECT *  FROM goods_class where goods_class_id = #{id}",
            "</script>"})
    GoodsClass getGoodsClassById(String id);


    @Select(value = {"<script>",
            "SELECT *  FROM goods_class where goods_class_pid = #{pid}",
            "</script>"})
    List<GoodsClass> getPgoodsClassByPidList(String pid);

    @Update(value = {"<script>",
            "update goods_class set modified_time=now()",
            "<when test='goodsClassPid !=null and goodsClassPid !=\"\"'>, goods_class_pid=#{goodsClassPid}</when>",
            "<when test='goodsClassName !=null and goodsClassName !=\"\"'>, goods_class_name=#{goodsClassName}</when>",
            "<when test='goodsClassOrder !=null and goodsClassOrder !=\"\"'>, goods_class_order=#{goodsClassOrder}</when>",
            "<when test='goodsClassImg !=null and goodsClassImg !=\"\"'>, goods_class_img=#{goodsClassImg}</when>",
            "where goods_class_id=#{goodsClassId}",
            "</script>"})
    Integer updateGoodsClass(GoodsClass goodsClass);


    @Insert(value = {"<script>",
            "insert goods_class",
            "(goods_class_pid,goods_class_name,goods_class_order,goods_class_img,create_time)",
            "VALUE",
            "(#{goodsClassPid},#{goodsClassName},#{goodsClassOrder},#{goodsClassImg},NOW())",
            "</script>"})
    Integer addGoodsClass(GoodsClass goodsClass);

    @Delete(value = {"<script>",
            "delete from goods_class where FIND_IN_SET(goods_class_id,#{ids})",
            "</script>"})
    Integer delGoodsClass(String ids);


    @Select(value = {"<script>",
            "SELECT * FROM banner where banner_id = 1",
            "</script>"})
    Banner getBanner();


    @Update(value = {"<script>",
            "update banner set modified_time=now()",
            "<when test='bannerUrls !=null and bannerUrls !=\"\"'>, banner_urls=#{bannerUrls}</when>",
            "where banner_id=1",
            "</script>"})
    Integer updateBanner(Banner banner);

    @Select(value = {"<script>",
            " SELECT * FROM dictionary  where FIND_IN_SET (type,#{type})",
            "</script>"})
    List<Dictionary> getDictionary(String type);


    @Select(value = {"<script>",
            " SELECT * FROM dictionary  where id = #{id}",
            "</script>"})
    Dictionary getDictionaryById(String id);


}
