package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.GoodsDao;
import com.yunniu.lease.dao.IndexDao;
import com.yunniu.lease.dao.UserDao;
import com.yunniu.lease.model.*;
import com.yunniu.lease.service.OrderService;
import com.yunniu.lease.service.UserService;
import com.yunniu.lease.util.ImageUtil;
import com.yunniu.lease.wx.WxUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private IndexDao indexDao;
    @Resource
    OrderService orderService;
    @Resource
    GoodsDao goodsDao;


    @Override
    public TableResult getUserList(HttpServletRequest request) {
        Map<String, Object> map = Pages.getParams(request);
//        int count = goodsDao.getGoodsListCount(map);
        List<User> list = userDao.getUserList(map);
        return new TableResult(3, list);
    }

    @Override
    public Result updateUserRole(User user) {
        Integer res = userDao.updateUserRole(user);
        if (res >= 1) {
            return new Result();
        }
        return new Result(103);
    }

    //获取经销商  2分润 和 3有效期
    @Override
    public Map<String, Integer> getDistributorInfo() {
        Map<String, Integer> map = new HashMap<>();
        map.put("bonus", indexDao.getSettingById(2));
        map.put("minute", indexDao.getSettingById(3));
        return map;
    }

    //更新经销商设置  2分润 和 3有效期
    @Override
    public Result updateDistributor(String minute) {
        Integer res = indexDao.updateDistributor(minute, "3");
        if (res >= 1) {
            return new Result();
        }
        return new Result(103);
    }


    @Override
    public Result getQRCode(Integer userId) {
        //分享码生成
        String shareCodeStr = WxUtil.downloadMiniCode(userId + "&" + System.currentTimeMillis());
        String QRCodeBase64 = ImageUtil.TransformPhotoToBase64Data(shareCodeStr);
        return new Result(QRCodeBase64);
    }


    @Override
    public Result getUserAddress(Integer userId) {
        return new Result(userDao.getUserAddress(userId));
    }

    @Override
    public Result addUserAddress(UserAddress userAddress) {
        if (userAddress.getAddressType() == 1) {
            userDao.updateUserAddressTo0(userAddress.getUserId());
        }
//        String cityId = userDao.getProvinceByName(userAddress.getProvince());
//        if (cityId == null || cityId.isEmpty()) {
//            return new Result(102);
//        }
//        userAddress.setCityId(cityId);
        int res = userDao.addUserAddress(userAddress);
        if (res > 0) {
            return new Result();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Result editUserAddress(UserAddress userAddress) {
        if (userAddress.getAddressType() == 1) {
            userDao.updateUserAddressTo0(userAddress.getUserId());
        }
//        String cityId = userDao.getProvinceByName(userAddress.getProvince());
//        if (cityId == null || cityId.isEmpty()) {
//            return new Result(102);
//        }
//        userAddress.setCityId(cityId);
        int res = userDao.editUserAddress(userAddress);
        if (res > 0) {
            return new Result();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Result delUserAddress(UserAddress userAddress) {
        int res = userDao.delUserAddress(userAddress);
        if (res > 0) {
            return new Result();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Result addCartGoods(UserCart userCart) {
        if (userCart.getGoodsNum() < 1) {
            return new Result(104, "加入失败,商品数量不能少于1!");
        }
        UserCart oldUserCart = userDao.getUserCartByCart(userCart);

        Integer minDay = indexDao.getSettingById(4);
        if (null == userCart.getLeaseDay() || userCart.getLeaseDay() < minDay) {
            Goods goods = goodsDao.getGoodsById(userCart.getGoodsId());
            if (goods.getIsLease().equals("1")) {
                return new Result(102, "租赁天数不合规");
            }
        }

        int res = 0;
        if (oldUserCart == null) {
            res = userDao.addCartGoods(userCart);
        } else {
            oldUserCart.setGoodsNum(oldUserCart.getGoodsNum() + userCart.getGoodsNum());
            res = userDao.editCartGoods(oldUserCart);
        }
        if (res > 0) {
            return new Result();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Result updateCartGoodsType(UserCart userCart) {
        Goods goods = null;
        Map<String, Double> map = new HashMap();
        if (userCart.getUserCartType() == 1 && null != userCart.getUserCartId() && !userCart.getUserCartId().equals("")) {
            List<OrderDetail> userCartGoodsList = userDao.getUserCartGoodsDetail(userCart.getUserCartId());
            OrderDetail orderDetail = userCartGoodsList.get(0);
            goods = orderService.getGoodsPrice(orderDetail.getGoodsId(), orderDetail.getGoodsSpecId(), orderDetail.getGoodsNum(), orderDetail.getLeaseDay());
            map.put("goodsPrice", goods.getGoodsPrice());
        } else {
            map.put("goodsPrice", 0.0);
        }
        Integer res = userDao.updateCartGoodsType(userCart);
        if (res > 0) {
            return new Result(map);
        }
        return new Result().error();
    }

    @Override
    public Result editCartGoods(UserCart userCart) {
        if (userCart.getGoodsNum() != null && userCart.getGoodsNum() < 1) {
            return new Result(104, "操作失败,商品数量不能少于1!");
        }
        int res = userDao.editCartGoods(userCart);
        if (res > 0) {
            return new Result();
        } else {
            return new Result(102);
        }
    }

    @Override
    public Result delCartGoods(UserCart userCart) {
        int res = userDao.delCartGoods(userCart.getUserCartId());
        if (res > 0) {
            return new Result();
        } else {
            return new Result(102);
        }
    }

    @Override
    public Result getCartGoods(Integer userId, String userCartType) {
        List<UserCart> cartGoods = userDao.getCartGoods(userId, userCartType);
        for (int i = 0; i < cartGoods.size(); i++) {
            UserCart userCart = cartGoods.get(i);
//            Goods goods1 = orderService.getGoodsPrice(userCart.getGoodsId(), userCart.getGoodsSpecId(), userCart.getGoodsNum(), userCart.getLeaseDay());
            if (userCart.getGoodsSpecId() != null) {
                GoodsSpec goodsSpec = goodsDao.getGoodsSpecById(userCart.getGoodsSpecId(), userCart.getGoodsId());
                if (null != goodsSpec) {
                    userCart.setGoodsSpecName(goodsSpec.getGoodsSpecName());
                    userCart.setGoodsSpecPrice(goodsSpec.getGoodsPrice());
                }
            }
            Goods goods = goodsDao.getGoodsById(userCart.getGoodsId());
            userCart.setIsLease(goods.getIsLease());
            userCart.setGoodsPrice(goods.getGoodsPrice());
            userCart.setGoodsImg(goods.getGoodsIndexImg());
            userCart.setGoodsName(goods.getGoodsName());
            cartGoods.set(i, userCart);
        }
        return new Result(cartGoods);
    }


}
