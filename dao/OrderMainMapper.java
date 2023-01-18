package com.yunniu.lease.dao;

import com.yunniu.lease.model.OrderMain;

import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface OrderMainMapper {

        /**
         * 删除Order Main
         */
        @Delete("<script>" +
                "DELETE FROM order_main WHERE id=#{id,jdbcType=INTEGER}" +
                "</script>")
        public Integer deleteOrderMain(Integer id);

        /**
         * 增加Order Main
         */
        @Insert("<script>"+
                "INSERT INTO order_main(id, createTime, state, countMoney)"+
                "VALUES(#{id},#{createTime},#{state},#{countMoney})"+
                "</script>")
        public Integer addOrderMain(OrderMain orderMain);

        /**单个查询
         */
        @Select("<script>"+
                "SELECT * FROM order_main WHERE id = #{id}"+
                "</script>")
        public OrderMain getOrderMainID(Integer id);

        /**
         * 全表查询
         */
        @Select("<script>"+
                "SELECT * FROM order_main"+
                "</script>")
        public List<OrderMain> getOrderMain();

        /**
         * 连表查询
         */
        @Select("<script>" +
                " SELECT * FROM order_sub \n" +
                " LEFT JOIN order_main\n" +
                " ON order_sub.order_main_id = order_main.id\n" +
                " LEFT JOIN product\n" +
                " ON product.id = order_sub.productid\n" +
                " LEFT JOIN user_admin\n" +
                " ON user_admin.id = order_main.userid"+
                "</script>")
        public List<OrderMain> findPageList();

        /**
         * 修改Order Main
         */
        @Update("<script>"+
                "UPDATE order_main SET `createTime`=#{createTime}, state=#{state}, countMoney=#{countMoney} "+
                "WHERE id = #{id}"+
                "</script>")
        public Integer updateOrderMain(OrderMain orderMain);


}