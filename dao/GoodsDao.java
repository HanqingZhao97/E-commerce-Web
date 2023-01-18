package com.yunniu.lease.dao;


import com.yunniu.lease.model.Goods;
import com.yunniu.lease.model.GoodsSpec;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsDao {

    @Select(value = {"<script>",
            "select count(1)",
            "from goods g",
            "<where>",
            "<when test='goodsName !=null and goodsName !=\"\"'> and g.goods_name like concat('%',#{goodsName},'%')</when>",
            "<when test='isLease !=null and isLease !=\"\"'> and g.is_lease = #{isLease}</when>",
            "<when test='goodsClassPid !=null and goodsClassPid !=\"\"'> and g.goods_class_pid = #{goodsClassPid}</when>",
            "<when test='abolishState !=null and abolishState !=\"\"'> and g.abolish_state = #{abolishState}</when>",
            "and g.is_delete !=1 ",
            "</where>",
            "</script>"})
    int getGoodsListCount(Map<String, Object> map);

    @Select(value = {"<script>",
            "select g.goods_id,g.is_lease,g.goods_index_img,g.goods_name,g.goods_price,g.create_time,g.goods_price,g.goods_stock,g.abolish_state,g.sales_volume,c.goods_class_name",
            "from goods g LEFT JOIN goods_class c on g.goods_class_pid = c.goods_class_id ",
            "<where>",
            "<when test='goodsName !=null and goodsName !=\"\"'> and g.goods_name like concat('%',#{goodsName},'%')</when>",
            "<when test='isLease !=null and isLease !=\"\"'> and g.is_lease = #{isLease}</when>",
            "<when test='goodsClassPid !=null and goodsClassPid !=\"\"'> and g.goods_class_pid = #{goodsClassPid}</when>",
            "<when test='abolishState !=null and abolishState !=\"\"'> and g.abolish_state = #{abolishState}</when>",
            "<when test='recommend !=null and recommend !=\"\"'> and g.recommend = #{recommend}</when>",
            "and g.is_delete !=1 ",
            "</where>",
            "ORDER BY ",
            "<when test='recommend ==1 '> g.recommend_order,</when>",
            "g.abolish_state desc,g.sales_volume desc,g.create_time desc",
            "limit ${startCount},${limit}",
            "</script>"})
    List<Goods> getGoodsList(Map<String, Object> map);


    @Insert(value = {"<script>",
            "insert into goods (",
            "<when test='goodsClassId !=null and goodsClassId !=\"\"'>goods_class_id,</when>",
            "<when test='goodsClassPid !=null and goodsClassPid !=\"\"'>goods_class_pid,</when>",
            "<when test='goodsIndexImg !=null and goodsIndexImg !=\"\"'>goods_index_img,</when>",
            "<when test='goodsImgs !=null and goodsImgs !=\"\"'>goods_imgs,</when>",
            "<when test='goodsName !=null and goodsName !=\"\"'>goods_name,</when>",
            "<when test='goodsPrice !=null and goodsPrice !=\"\"'>goods_price,</when>",
            "<when test='goodsDetail !=null and goodsDetail !=\"\"'>goods_detail,</when>",
            "<when test='goodsStock !=null and goodsStock !=\"\"'>goods_stock,</when>",
            "<when test='goodsDes !=null and goodsDes !=\"\"'>goods_des,</when>",
            "<when test='deliveryId !=null and deliveryId !=\"\"'>delivery_id,</when>",
            "<when test='isLease !=null and isLease !=\"\"'>is_lease,</when>",
            "<when test='serviceId !=null and serviceId !=\"\"'>service_id,</when>",
            "<when test='rebate !=null and rebate !=\"\"'>rebate,</when>",
            "<when test='deposit !=null and deposit !=\"\"'>deposit,</when>",
            "<when test='recommend !=null and recommend !=\"\"'>recommend,</when>",
            "<when test='recommendOrder !=null and recommendOrder !=\"\"'>recommend_order,</when>",
            "create_time)",
            "values(",
            "<when test='goodsClassId !=null and goodsClassId !=\"\"'>#{goodsClassId},</when>",
            "<when test='goodsClassPid !=null and goodsClassPid !=\"\"'>#{goodsClassPid},</when>",
            "<when test='goodsIndexImg !=null and goodsIndexImg !=\"\"'>#{goodsIndexImg},</when>",
            "<when test='goodsImgs !=null and goodsImgs !=\"\"'>#{goodsImgs},</when>",
            "<when test='goodsName !=null and goodsName !=\"\"'>#{goodsName},</when>",
            "<when test='goodsPrice !=null and goodsPrice !=\"\"'>#{goodsPrice},</when>",
            "<when test='goodsDetail !=null and goodsDetail !=\"\"'>#{goodsDetail},</when>",
            "<when test='goodsStock !=null and goodsStock !=\"\"'>#{goodsStock},</when>",
            "<when test='goodsDes !=null and goodsDes !=\"\"'>#{goodsDes},</when>",
            "<when test='deliveryId !=null and deliveryId !=\"\"'>#{deliveryId},</when>",
            "<when test='isLease !=null and isLease !=\"\"'>#{isLease},</when>",
            "<when test='serviceId !=null and serviceId !=\"\"'>#{serviceId},</when>",
            "<when test='rebate !=null and rebate !=\"\"'>#{rebate},</when>",
            "<when test='deposit !=null and deposit !=\"\"'>#{deposit},</when>",
            "<when test='recommend !=null and recommend !=\"\"'>#{recommend},</when>",
            "<when test='recommendOrder !=null and recommendOrder !=\"\"'>#{recommendOrder},</when>",
            "NOW())",
            "</script>"})
    Integer addGoods(Goods goods);


    @Update(value = {"<script>",
            "update goods set modified_time=now()",
            "<when test='goodsClassId !=null '>,goods_class_id=#{goodsClassId}</when>",
            "<when test='goodsClassPid !=null '>,goods_class_pid=#{goodsClassPid}</when>",
            "<when test='goodsIndexImg !=null and goodsIndexImg !=\"\"'>,goods_index_img=#{goodsIndexImg}</when>",
            "<when test='goodsImgs !=null and goodsImgs !=\"\"'>,goods_imgs=#{goodsImgs}</when>",
            "<when test='goodsName !=null and goodsName !=\"\"'>,goods_name=#{goodsName}</when>",
            "<when test='goodsPrice !=null and goodsPrice !=\"\"'>,goods_price=#{goodsPrice}</when>",
            "<when test='goodsDetail !=null and goodsDetail !=\"\"'>,goods_detail=#{goodsDetail}</when>",
            "<when test='goodsStock !=null and goodsStock !=\"\"'>,goods_stock=#{goodsStock}</when>",
            "<when test='goodsDes !=null and goodsDes !=\"\"'>,goods_des=#{goodsDes}</when>",
            "<when test='deliveryId !=null and deliveryId !=\"\"'>,delivery_id=#{deliveryId}</when>",
            "<when test='isLease !=null and isLease !=\"\"'>,is_lease=#{isLease}</when>",
            "<when test='serviceId !=null and serviceId !=\"\"'>,service_id=#{serviceId}</when>",
            "<when test='rebate !=null and rebate !=\"\"'>,rebate=#{rebate}</when>",
            "<when test='deposit !=null and deposit !=\"\"'>,deposit=#{deposit}</when>",
            "<when test='recommend !=null and recommend !=\"\"'>,recommend=#{recommend}</when>",
            "<when test='recommendOrder !=null and recommendOrder !=\"\"'>,recommend_order=#{recommendOrder}</when>",
            "where goods_id=#{goodsId}",
            "</script>"})
    Integer updateGoods(Goods goods);


    @Select(value = {"<script>",
            "select * from goods where goods_id=#{goodsId}",
            "</script>"})
    Goods getGoodsById(String goodsId);


    @Select(value = {"<script>",
            "select goods_id from goods where goods_name=#{goodsName} limit 1",
            "</script>"})
    String getGoodsIdByName(String goodsName);

    //商品在售状态更新
    @Update(value = {"<script>",
            "update goods set abolish_state=#{abolishState}",
            "where goods_id=#{goodsId}",
            "</script>"})
    Integer changeAbolishState(String goodsId, String abolishState);


    //商品删除状态更新
    @Update(value = {"<script>",
            "update goods set is_delete=1",
            "where  FIND_IN_SET(goods_id,#{goodsId})",
            "</script>"})
    Integer changeDeleteState(String goodsId);


    @Insert(value = {"<script>",
            "insert into goods_spec (goods_id,",
            "<when test='goodsSpecName !=null and goodsSpecName !=\"\"'>goods_spec_name,</when>",
            "<when test='goodsSpecImg !=null and goodsSpecImg !=\"\"'>goods_spec_img,</when>",
            "<when test='goodsSpecStock !=null and goodsSpecStock !=\"\"'>goods_spec_stock,</when>",
            "<when test='goodsPrice !=null and goodsPrice !=\"\"'>goods_price,</when>",
            "<when test='deposit !=null and deposit !=\"\"'>deposit,</when>",
            "create_time)",
            "values(",
            "#{goodsId},",
            "<when test='goodsSpecName !=null and goodsSpecName !=\"\"'>#{goodsSpecName},</when>",
            "<when test='goodsSpecImg !=null and goodsSpecImg !=\"\"'>#{goodsSpecImg},</when>",
            "<when test='goodsSpecStock !=null and goodsSpecStock !=\"\"'>#{goodsSpecStock},</when>",
            "<when test='goodsPrice !=null and goodsPrice !=\"\"'>#{goodsPrice},</when>",
            "<when test='deposit !=null and deposit !=\"\"'>#{deposit},</when>",
            "NOW())",
            "</script>"})
    Integer addGoodsSpec(GoodsSpec goodsSpec);


    @Select(value = {"<script>",
            "select * from goods_spec where goods_spec_id=#{goodsSpecId} and goods_id = #{goodsId}",
            "</script>"})
    GoodsSpec getGoodsSpecById(String goodsSpecId, String goodsId);


    @Select(value = {"<script>",
            "select *",
            "from goods_spec",
            "where goods_id=#{goodsId} and goods_spec_name=#{goodsSpecName} limit 1",
            "</script>"})
    GoodsSpec getGoodsSpecByName(GoodsSpec goodsSpec);


    @Select(value = {"<script>",
            "select *",
            "from goods_spec",
            "where goods_id=#{goodsId}",
            "</script>"})
    List<GoodsSpec> getGoodsSpecByGoodsId(String goodsId);


    @Delete(value = {"<script>",
            "delete",
            "from goods_spec",
            "where  FIND_IN_SET(goods_id,#{goodsId}) and !FIND_IN_SET(goods_spec_id,#{goodsSpecId})",
            "</script>"})
    Integer deleteGoodsSpecByGoodsId(String goodsId, String goodsSpecId);


    @Update(value = {"<script>",
            "update goods_spec set modified_time=now()",
            "<when test='goodsSpecName !=null and goodsSpecName !=\"\"'>,goods_spec_name=#{goodsSpecName}</when>",
            "<when test='goodsSpecImg !=null and goodsSpecImg !=\"\"'>,goods_spec_img=#{goodsSpecImg}</when>",
            "<when test='goodsPrice !=null and goodsPrice !=\"\"'>,goods_price=#{goodsPrice}</when>",
            "<when test='goodsSpecStock !=null and goodsSpecStock !=\"\"'>,goods_spec_stock=#{goodsSpecStock}</when>",
            "<when test='deposit !=null and deposit !=\"\"'>,deposit=#{deposit}</when>",
            "where goods_spec_id=#{goodsSpecId}",
            "</script>"})
    Integer updateGoodsSpec(GoodsSpec goodsSpec);


    @Update(value = {"<script>",
            "update goods_spec gs",
            "left join order_detail od on gs.goods_spec_id=od.goods_spec_id",
            "set gs.goods_spec_stock=gs.goods_spec_stock-od.goods_num",
            "where od.order_id=#{orderId}",
            "</script>"})
    int reduceGoodsSpecStock(String orderId);

    @Update(value = {"<script>",
            "UPDATE goods SET goods_stock = (select SUM(goods_spec_stock) from goods_spec where goods_id =  #{goodsId}) WHERE goods_id = #{goodsId}",
            "</script>"})
    Integer updateGoodsStockBygoodsId(String goodsId);


    @Update(value = {"<script>",
            "  UPDATE goods_spec  SET goods_spec_stock = goods_spec_stock-#{number}  where goods_spec_id=#{goodsSpecId}",
            "</script>"})
    Integer updateGoodsStockByGoodsSpecId(String goodsSpecId, Integer number);


}
