package com.yunniu.lease.dao;

import com.yunniu.lease.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {


    @Select(value = {"<script>",
            "select * from user ",
            "where user_role = ${userRole} ",
            "<when test='nickName !=null and nickName !=\"\"'>and nick_name like concat('%',#{nickName},'%')</when>",
            "limit ${startCount},${limit}",
            "</script>"})
    List<User> getUserList(Map<String, Object> map);


    @Select(value = {"<script>",
            "select u.*",
            "from user u",
            "where u.user_id=#{userId}",
            "</script>"})
    User getUserById(String userId);


    @Insert(value = {"<script>",
            "insert into user",
            "(open_id,user_role,union_id,create_time)",
            "values",
            "(#{openId},#{userRole},#{unionId},now())",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int addUser(User user);


    @Select(value = {"<script>",
            "select *",
            "from `user`",
            "where open_id=#{openid} limit 1",
            "</script>"})
    User getUserByOpenId(String openid);


    @Update(value = {"<script>",
            "update `user` set user_role=#{userRole} where user_id=#{userId} and  abolish_state=0",
            "</script>"})
    Integer updateUserRole(User user);


    @Update(value = {"<script>",
            "update `user` set sum_num=#{sumNum} where user_id=#{userId} and  abolish_state=0",
            "</script>"})
    Integer updateUserSumNum(User user);

    @Insert(value = {"<script>",
            "insert into user_rebate",
            "(user_rebate_id,user_id,order_id,rebate_user_id,rebate_num,rebate_type,deposits,create_time)",
            "values",
            "(#{userRebateId},#{userId},#{orderId},#{rebateUserId},#{rebateNum},#{rebateType},#{deposits},now())",
            "</script>"})
    Integer addUserRebate(UserRebate userRebate);

    @Select(value = {"<script>",
            "select * from user_rebate ",
            " where  user_id=#{userId}",
            "<when test='rebateType !=null and rebateType !=\"\"'> and  rebate_type=#{rebateType}</when>",
            "</script>"})
    List<UserRebate> getUserRebateList(UserRebate userRebate);


    @Select(value = {"<script>",
            "SELECT ask.*,u.nick_name,u.head_link from user_ask_log ask ",
            "LEFT JOIN `user` u on u.user_id = ask.user_id",
            "<where>",
            "<when test='userAskType !=null and userAskType !=\"\"'>and ask.user_ask_type=#{userAskType}</when>",
            "<when test='nickName !=null and nickName !=\"\"'>and u.nick_name like concat('%',#{nickName},'%')</when>",
            "</where>",
            "</script>"})
    List<UserAskLog> getUserAskLogList(Map<String, Object> map);


    @Select(value = {"<script>",
            "SELECT COUNT(0)  from user_ask_log ask",
            "LEFT JOIN `user` u on u.user_id = ask.user_id",
            "<where>",
            "<when test='userAskType !=null and userAskType !=\"\"'>and ask.user_ask_type=#{userAskType}</when>",
            "<when test='nickName !=null and nickName !=\"\"'>and u.nick_name like concat('%',#{nickName},'%')</when>",
            "</where>",
            "order by ask.user_ask_type,ask.create_time",
            "</script>"})
    Integer getUserAskLogCount(Map<String, Object> map);


    @Select(value = {"<script>",
            "SELECT * from user_ask_log ",
            "where user_ask_log_id=#{userAskLogId}",
            "</script>"})
    UserAskLog getUserAskLog(String userAskLogId);


    @Update(value = {"<script>",
            "update user_ask_log set user_ask_type=#{userAskType},user_deposits=#{userDeposits}",
            "where user_ask_log_id=#{userAskLogId}",
            "</script>"})
    Integer updateUserAskLogType(UserAskLog userAskLog);


    @Insert(value = {"<script>",
            "insert into user_ask_log",
            "(user_id,user_deposits,user_ask_deposits,create_time)",
            "values",
            "(#{userId},#{userDeposits},#{userAskDeposits},now())",
            "</script>"})
    Integer addUserAskLog(UserAskLog userAskLog);


    @Select(value = {"<script>",
            "select * from user_address where user_id=#{userId}",
            "</script>"})
    List<UserAddress> getUserAddress(Integer userId);


    @Update(value = {"<script>",
            "update user_address set address_type=0",
            "where user_id=#{userId}",
            "</script>"})
    int updateUserAddressTo0(int userId);

    @Insert(value = {"<script>",
            "insert into user_address",
            "(user_id,receiver_name,receiver_phone,province,city,county,receiver_address,city_id,address_type,create_time)",
            "values",
            "(#{userId},#{receiverName},#{receiverPhone},#{province},#{city},#{county},#{receiverAddress},#{cityId},#{addressType},now())",
            "</script>"})
    int addUserAddress(UserAddress userAddress);

    @Update(value = {"<script>",
            "update user_address set modified_time=now()",
            "<when test='receiverName !=null and receiverName !=\"\"'>, receiver_name=#{receiverName}</when>",
            "<when test='receiverPhone !=null and receiverPhone !=\"\"'>, receiver_phone=#{receiverPhone}</when>",
            "<when test='province !=null and province !=\"\"'>, province=#{province}</when>",
            "<when test='city !=null and city !=\"\"'>, city=#{city}</when>",
            "<when test='county !=null and county !=\"\"'>, county=#{county}</when>",
            "<when test='receiverAddress !=null and receiverAddress !=\"\"'>, receiver_address=#{receiverAddress}</when>",
            "<when test='cityId !=null and cityId !=\"\"'>, city_id=#{cityId}</when>",
            "<when test='addressType !=null and addressType !=\"\"'>, address_type=#{addressType}</when>",
            "where user_address_id=#{userAddressId}",
            "</script>"})
    int editUserAddress(UserAddress userAddress);

    @Select(value = {"<script>",
            "select * from user_cart where user_id=#{userId} and goods_id=#{goodsId}  limit 1",
            "</script>"})
    UserCart getUserCartByCart(UserCart userCart);

    //
    @Insert(value = {"<script>",
            "insert into user_cart",
            "(user_cart_id,user_id,lease_day,goods_id,goods_num,goods_spec_id,delivery_id,create_time,modified_time)",
            "values",
            "(UUID_SHORT(),#{userId},#{leaseDay},#{goodsId},#{goodsNum},#{goodsSpecId},#{deliveryId},now(),now())",
            "</script>"})
    int addCartGoods(UserCart userCart);

    @Update(value = {"<script>",
            "update user_cart set modified_time=now()",
            "<when test='userCartType !=null '>,user_cart_type=#{userCartType}</when>",
            "<when test='goodsNum !=null '>,goods_num=#{goodsNum}</when>",
            "where user_cart_id=#{userCartId} and user_id=#{userId}",
            "</script>"})
    int editCartGoods(UserCart oldUserCart);


    @Update(value = {"<script>",
            "update user_cart set modified_time=now(),user_cart_type=#{userCartType}",
            "where user_id=#{userId}",
            "<when test='userCartId !=null and userCartId !=\"\"'> and user_cart_id=#{userCartId}</when>",
            "</script>"})
    int updateCartGoodsType(UserCart userCart);

    @Delete(value = {"<script>",
            "delete from user_cart where find_in_set(user_cart_id,#{userCartId})",
            "</script>"})
    int delCartGoods(String userCartId);


    @Select(value = {"<script>",
            "select * ",
            "from user_cart ",
            "where user_id=#{userId} ",
            "<when test='userCartType !=null and userCartType !=\"\"'> and user_cart_type=#{userCartType}</when>",
            "Order by create_time desc",
            "</script>"})
    List<UserCart> getCartGoods(Integer userId, String userCartType);

    @Delete(value = {"<script>",
            "delete from user_address where find_in_set(user_address_id,#{userAddressId}) and user_id=#{userId}",
            "</script>"})
    int delUserAddress(UserAddress userAddress);


    @Select(value = {"<script>",
            "SELECT * FROM user_address where user_address_id = #{userAddressId}",
            "</script>"})
    UserAddress getUserAddressById(String userAddressId);


    @Select(value = {"<script>",
            "select * from user_cart where find_in_set(user_cart_id,#{userCartId})",
            "</script>"})
    List<OrderDetail> getUserCartGoodsDetail(String userCartId);


}
