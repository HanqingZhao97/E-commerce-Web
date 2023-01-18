package com.yunniu.lease.dao;

import com.yunniu.lease.model.Order;
import com.yunniu.lease.model.OrderDetail;
import com.yunniu.lease.model.OrderDetailGoods;
import com.yunniu.lease.model.RefundDeposit;
import org.apache.ibatis.annotations.*;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {


    @Insert(value = {"<script>",
            "insert into `order`",
            "(order_id,user_id,lease_day,total_amount,goods_num,receiver_name,receiver_phone,province,city,county,receiver_address,order_remarks,delivery_id,delivery_name,map_coordinate_id,is_lease,create_time)",
            "values",
            "(#{orderId},#{userId},#{leaseDay},#{totalAmount},#{goodsNum},#{receiverName},#{receiverPhone},#{province},#{city},#{county},#{receiverAddress},#{orderRemarks},#{deliveryId},#{deliveryName},#{mapCoordinateId},#{isLease},now())",
            "</script>"})
    Integer addOrder(Order order);

    @Insert(value = {"<script>",
            "insert into order_detail",
            "(order_detail_id,order_id,goods_id,goods_name,goods_img,goods_price,goods_spec_id,goods_spec_name,delivery_id,delivery_name,goods_num,lease_day,lease_day_price)",
            "values",
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">",
            "(UUID_SHORT(),#{item.orderId},#{item.goodsId},#{item.goodsName},#{item.goodsImg},#{item.goodsPrice},#{item.goodsSpecId},#{item.goodsSpecName},#{item.deliveryId},#{item.deliveryName},#{item.goodsNum},#{item.leaseDay},#{item.leaseDayPrice})",
            "</foreach>",
            "</script>"})
    int addOrderDetail(List<OrderDetail> list);


    @Select(value = {"<script>",
            "SELECT o.order_id,o.lease_day,o.lease_start_date,o.lease_end_date,o.pay_amount,od.goods_price,od.goods_name,od.goods_num,od.lease_day_price FROM `order` o",
            "LEFT JOIN order_detail od ON od.order_id = o.order_id",
            "where o.order_id = #{orderId} LIMIT 1",
            "</script>"})
    RefundDeposit getRefundInfo(String orderId);


    @Select(value = {"<script>",
            "select * from `order` where order_id=#{orderId}",
            "</script>"})
    Order getOrderById(String orderId);


    @Select(value = {"<script>",
            "select count(1)",
            "from `order` o",
//            "left join user u on o.user_id=u.user_id",
            "where o.order_state=#{orderState}",
            "<when test='orderId !=null and orderId !=\"\"'>and o.order_id like concat('%',#{orderId},'%')</when>",
            "<when test='printState !=null and printState !=\"\"'>and o.print_state=#{printState}</when>",
//            "<when test='nickName !=null and nickName !=\"\"'>and u.nick_name like concat('%',#{nickName},'%')</when>",
            "<when test='receiverName !=null and receiverName !=\"\"'>and o.receiver_name like concat('%',#{receiverName},'%')</when>",
            "<when test='receiverPhone !=null and receiverPhone !=\"\"'>and o.receiver_phone like concat('%',#{receiverPhone},'%')</when>",
            "</script>"})
    int getOrderListCount(Map<String, Object> map);

    @Select(value = {"<script>",
            "select * ",
            "from `order` o",
//            "left join user u on o.user_id=u.user_id",
            "where  o.abolish_state=0",
            "<when test='orderId !=null and orderId !=\"\"'>and o.order_id like concat('%',#{orderId},'%')</when>",
//            "<when test='nickName !=null and nickName !=\"\"'>and u.nick_name like concat('%',#{nickName},'%')</when>",
            "<when test='receiverName !=null and receiverName !=\"\"'>and o.receiver_name like concat('%',#{receiverName},'%')</when>",
            "<when test='receiverPhone !=null and receiverPhone !=\"\"'>and o.receiver_phone like concat('%',#{receiverPhone},'%')</when>",
            "<when test='userId !=null and userId !=\"\"'>and o.user_id =#{userId}</when>",
            "<when test='orderState !=null and orderState !=\"\"'>and o.order_state =#{orderState}</when>",
            "<when test='orderState ==1'>ORDER BY  o.remind desc,o.create_time</when>",
            "limit ${startCount},${limit}",
            "</script>"})
    List<Order> getOrderList(Map<String, Object> map);


    @Select(value = {"<script>",
            "select od.*,gs.goods_spec_name,g.goods_name,g.goods_price,g.rebate",
            "from order_detail od",
            "left join goods_spec gs on od.goods_spec_id=gs.goods_spec_id",
            "left join goods g on od.goods_id=g.goods_id",
            "where find_in_set(od.order_id,#{orderId}) order by od.order_id,od.goods_id",
            "</script>"})
    List<OrderDetailGoods> getOrderDetailGoodsListByOrderIds(String ids);


    @Select(value = {"<script>",
            "select od.*,o.tracking_num,o.deliver_time,case when od.goods_spec_id='' or od.goods_spec_id is null then g.goods_index_img else gs.goods_spec_img end as goodsImg  ,g.goods_name,g.goods_price,gs.goods_spec_img,gs.goods_spec_name,g.goods_index_img",
            "from order_detail od",
            "LEFT JOIN goods_spec gs on od.goods_spec_id=gs.goods_spec_id",
            "LEFT JOIN goods g on od.goods_id=g.goods_id",
            "LEFT JOIN `order` o on o.order_id=od.order_id",
            "where FIND_IN_SET(od.order_id,#{ids})",
            "</script>"})
    List<OrderDetailGoods> getOrderDetailListByIds(String ids);


//    @Select(value = {"<script>",
//            "select o.*",
//            "from `order` o",
//            "where o.user_id=#{userId} and o.abolish_state=0",
//            "<when test='orderState !=null and orderState!=\"\"'>and o.order_state=#{orderState}</when>",
//            "order by o.create_time desc limit ${startCount},${limit}",
//            "</script>"})
//    List<Order> getOrderList(Map<String, Object> map);

    @Update(value = {"<script>",
            "update `order` set out_trade_no=#{outTradeNo} ",
//            "<when test ='orderType != null and orderType != \"\"'> order_type = #{orderType}</when>",
            "where order_id=#{orderId}",
            "</script>"})
    int updateOutTradeNo(Order order);


    @Update(value = {"<script>",
            "update `order` set remind=1,modified_time=now() where order_id=#{orderId}",
            "</script>"})
    Integer remind(String orderId);


//    @Select(value = {"<script>",
//            "select * from `order` where out_trade_no=#{outTradeNo} limit 1",
//            "</script>"})
//    Order getOrderByOutTradeNo(String OutTradeNo);

    @Update(value = {"<script>",
            "update `order` set order_state=1,pay_amount=#{payAmount},transaction_id=#{transactionId},pay_time=now() where order_id=#{orderId} and order_state=0",
            "</script>"})
    int orderPaySuccess(Order order);

    @Update(value = {"<script>",
            "update `order` set order_state=5,finish_time=now() where order_id=#{orderId} and order_state=0",
            "</script>"})
    int cancelOrder0(String orderId);

    @Update(value = {"<script>",
            "update `order` set refund_amount=pay_amount, order_state=5,finish_time=now() where order_id=#{orderId} and order_state=1",
            "</script>"})
    int cancelOrder1(String orderId);

    @Update(value = {"<script>",
            "update goods_spec gs",
            "left join order_detail od on gs.goods_spec_id=od.goods_spec_id",
            "set gs.goods_spec_stock=gs.goods_spec_stock+od.goods_num",
            "where od.order_id=#{orderId}",
            "</script>"})
    int backGoodsSpec(String orderId);


    @Update(value = {"<script>",
            "update goods gs",
            "left join order_detail od on gs.goods_id=od.goods_id",
            "set gs.goods_stock=gs.goods_stock+od.goods_num",
            "where od.order_id=#{orderId}",
            "</script>"})
    int backGoodsStock(String orderId);

    @Select(value = {"<script>",
            "SELECT * from order_detail where FIND_IN_SET(order_id,#{orderId})",
            "</script>"})
    List<OrderDetail> getOrderDetailList(String orderId);


    @Delete(value = {"<script>",
            "delete from `order_detail` where find_in_set(order_id,#{id})",
            "</script>"})
    int delOrderDetail(String id);

    @Delete(value = {"<script>",
            "delete from `order` where find_in_set(order_id,#{id})",
            "</script>"})
    int delOrder(String id);


    @Update(value = {"<script>",
            "update `order` set order_state=2,tracking_num=#{trackingNum},deliver_time=now() where find_in_set(order_id,#{orderId}) and order_state=1",
            "</script>"})
    Integer tracking(String orderId, String trackingNum);

    //收货
    @Update(value = {"<script>",
            "update `order` set finish_time=now(),order_state=#{orderState}",
            "<when test ='leaseStartDate != null and leaseStartDate != \"\"'> ,lease_start_date = #{leaseStartDate}</when>",
            "<when test ='leaseEndDate != null and leaseEndDate != \"\"'> ,lease_end_date = #{leaseEndDate}</when>",
            " where find_in_set(order_id,#{orderId}) and order_state=2",
            "</script>"})
    Integer receiving(Order order);


    @Select(value = {"<script>",
            "select * from `order` where out_trade_no=#{outTradeNo} limit 1",
            "</script>"})
    Order getOrderByOutTradeNo(String OutTradeNo);


//    @Select(value = {"<script>",
//            "select od.order_detail_id,od.goods_num,g.rebate_a,g.rebate_b",
//            "from order_detail od",
//            "left join goods g on od.goods_id=g.goods_id",
//            "where od.order_id=#{orderId}",
//            "</script>"})
//    List<OrderDetailGoodsRebate> getOrderDetailGoodsRebateByOrderId(String orderId);
//
//
//    @Update(value = {"<script>",
//            "update `order` set read_state=1",
//            "where user_id=#{userId} and read_state=0 and order_state=#{orderState} ",
//            "</script>"})
//    int setOrderRead(Map<String, Object> map1);
//
//    @Select(value = {"<script>",
//            "select * from order_detail od",
//            "where od.order_detail_id=#{orderDetailId}",
//            "</script>"})
//    OrderDetail getOrderDetailById(String orderDetailId);
//
//    @Update(value = {"<script>",
//            "update `order` set order_state=#{orderState}",
//            "where order_id=#{orderId} ",
//            "</script>"})
//    int updateOrderState(Order order);
//
//    @Update(value = {"<script>",
//            "update `order` set order_state=3,finish_time=now()",
//            "where order_id=#{orderId} and order_state=2",
//            "</script>"})
//    int finishOrder(String orderId);
//
//    @Insert(value = {"<script>",
//            "insert into user_rebate",
//            "(user_rebate_id,user_id,order_id,rebate_user_id,rebate_num,rebate_type,create_time)",
//            "values",
//            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">",
//            "(#{item.userRebateId},#{item.userId},#{item.orderId},#{item.rebateUserId},#{item.rebateNum},#{item.rebateType},now())",
//            "  </foreach>",
//            "</script>"})
//    int addUserRebate(List<UserRebate> list);
//
//    @Update(value = {"<script>",
//            "update `user` u",
//            "join (select sum(rebate_num) rebate_num,user_id  from user_rebate where user_id in (select user_id from user_rebate where order_id=#{orderId})  GROUP BY user_id) A",
//            "on u.user_id=A.user_id",
//            "set u.sum_num=A.rebate_num",
//            "</script>"})
//    int updateUserRebateInfoByOrderId(String orderId);

    @Select(value = {"<script>",
            "select od.order_detail_id",
            "from order_detail od",
            "where od.order_id=#{orderId}",
            "</script>"})
    List<Map<String, Object>> getOrderDetailCommentState(String orderId);

    //    @Update(value = {"<script>",
//            "update `order` set order_state=5,finish_time=now()  where order_state=0 and NOW()>(create_time + interval 2 hour)",
//            "</script>"})
//    int cancelOrder();
//
//    @Select(value = {"<script>",
//            "select order_id,user_id,order_state",
//            "from `order`",
//            "where order_state=2 and NOW()>(deliver_time + interval 336 hour)",
//            "</script>"})
//    List<Order> getOrderList2();
//
    @Select(value = {"<script>",
            "select count(1)",
            "from `order` o",
            "where o.user_id=#{userId} and o.order_state in (1,2,3,4)",
            "</script>"})
    int getOrderPayCount(String userId);
}
