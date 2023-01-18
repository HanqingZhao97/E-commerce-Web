package com.yunniu.lease.service;

import com.yunniu.lease.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    Result getQRCode(Integer userId);

    Result getUserAddress(Integer userId);

    Result addUserAddress(UserAddress userAddress);

    Result editUserAddress(UserAddress userAddress);

    Result delUserAddress(UserAddress userAddress);

    Result addCartGoods(UserCart userCart);

    Result updateCartGoodsType(UserCart userCart);

    Result editCartGoods(UserCart userCart);

    Result delCartGoods(UserCart userCart);

    Result getCartGoods(Integer userId, String userCartType);

    TableResult getUserList(HttpServletRequest request);

    Result updateUserRole(User user);

    Map<String, Integer> getDistributorInfo();

    Result updateDistributor(String minute);


}
