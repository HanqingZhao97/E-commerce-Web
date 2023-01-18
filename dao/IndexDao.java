package com.yunniu.lease.dao;

import com.yunniu.lease.model.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface IndexDao {


//    @Select(value = {"<script>",
//            "select *",
//            "from dict",
//            "where find_in_set(id,#{id})",
//            "</script>"})
//    List<Dictionary> getDictById(String id);

//    @Select(value = {"<script>",
//            "select *",
//            "from `help`",
//            "order by help_index",
//            "</script>"})
//    List<Help> getHelpList();
//
//    @Select(value = {"<script>",
//            "select * from banner where goods_class_id=#{goodsClassId} order by banner_index",
//            "</script>"})
//    List<Banner> getClassBanner(int goodsClassId);
//
//    @Select(value = {"<script>",
//            "select * from goods_class order by goods_class_order asc",
//            "</script>"})
//    List<GoodsClass> getIndexClass();

    @Select(value = {"<script>",
            "select val from setting where id=#{id}",
            "</script>"})
    Integer getSettingById(int id);

    @Update(value = {"<script>",
            "update setting set val = ${val} where id = ${id}",
            "</script>"})
    Integer updateDistributor(String val, String id);

//    @Select(value = {"<script>",
//            "select * from goods where goods_name like concat('%',#{goodsName},'%') and abolish_state=0 order by sales_volume desc",
//            "</script>"})
//    List<Goods> searchGoods(String goodsName);

}
