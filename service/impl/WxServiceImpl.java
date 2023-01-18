package com.yunniu.lease.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.dao.EquipmentUserMapper;
import com.yunniu.lease.dao.IndexDao;
import com.yunniu.lease.dao.OrderDao;
import com.yunniu.lease.dao.UserDao;
import com.yunniu.lease.model.*;
import com.yunniu.lease.redis.RedisUtil;
import com.yunniu.lease.service.WxService;
import com.yunniu.lease.util.HttpClientUtil;
import com.yunniu.lease.util.UUIDUtils;
import com.yunniu.lease.wx.WxConfigUtil;
import com.yunniu.lease.wx.WxUtil;
import com.yunniu.lease.wx.XMLUtil;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class WxServiceImpl implements WxService {

    @Resource
    private UserDao userDao;

    @Resource
    private OrderDao orderDao;

    @Resource
    private IndexDao indexDao;

    @Resource
    EquipmentUserMapper equipmentUserMapper;


    @Override
    public Result login(String code) {
        System.out.println("code================================" + code);
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", WxConfigUtil.APPID);
        map.put("secret", WxConfigUtil.App_Secret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String res1 = HttpClientUtil.doGet(WxConfigUtil.jscodepath, map);
        System.out.println("res1====" + res1);
        JSONObject jsStr = JSONObject.parseObject(res1);
        String openid = (String) jsStr.get("openid");
        System.out.println("openid====" + openid);
        System.out.println("jsStr====" + jsStr);
        String unionId = (String) jsStr.get("unionid");
        //1.根据oppenid查询 
        EquipmentUser equipmentUser=equipmentUserMapper.selectByPrimaryopenid(openid);
        String token = UUIDUtils.getUUID();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("token", token);
        if (equipmentUser == null){
            EquipmentUser  user=new EquipmentUser();
            user.setOpenid(openid);
            Integer info=equipmentUserMapper.insertSelective(user);
            if (info > 0) {
                map1.put("user", user);
            } else {
                throw new RuntimeException();
            }
        } else {

            map1.put("user", equipmentUser);
            return new Result(map1);
        }
        return new Result(map1);
    }

    @Override
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        // 解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());
        for (Object keyValue : m.keySet()) {
            System.out.println(keyValue + "=" + m.get(keyValue));
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
            System.out.println("支付成功!");
            // 这里是支付成功
            // ////////执行自己的业务逻辑////////////////
            String mch_id = (String) packageParams.get("mch_id"); // 商户号
            String openid = (String) packageParams.get("openid"); // 用户标识
            String outTradeNo = (String) packageParams.get("out_trade_no"); // 商户订单号
            String total_fee = (String) packageParams.get("total_fee"); //
            double payAmount = Double.valueOf(total_fee) / 100;
            String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号

            if (WxConfigUtil.MCH_ID.equals(mch_id)) {
                Order order = orderDao.getOrderByOutTradeNo(outTradeNo);
                System.out.println("支付成功!支付订单编号====" + outTradeNo + "   orderState==" + order.getOrderState());
                if (order.getOrderState() != 0) {
                    return;
                }
                String shareUserId = order.getShareUserId();
                if (shareUserId != null && !shareUserId.equals("0")) {
                    distribution(order);
                }

                // 更新订单
                //更新订单状态和信息
                order.setPayAmount(payAmount);
                order.setTransactionId(transaction_id);
                orderDao.orderPaySuccess(order);

            }
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        // ------------------------------
        // 处理业务完毕
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }


    @Override
    public Result wxPay(String userId, String orderId, HttpServletRequest request) throws Exception {
        Order order = orderDao.getOrderById(orderId);
        if (order == null || !order.getUserId().equals(userId) || order.getOrderState() != 0) {
            return new Result(102);
        }
        String share = request.getParameter("share");
        if (share != null && !share.equals("")) {
            System.out.println("通过分享二维码进入的用户参数" + share);
            String[] strArr = share.split("&");
            if (strArr.length == 2) {
                long millisecond = System.currentTimeMillis() - Long.valueOf(strArr[0]);
                Long settingTime = Long.valueOf(indexDao.getSettingById(3) * 1000);
                if (settingTime > millisecond) {
                    order.setShareUserId(strArr[0]);
                } else {
                    System.out.println("分享二维码超时");
                }
            }
        }

        order.setOutTradeNo(UUIDUtils.getUUID());
        User user = userDao.getUserById(userId);
        double tradeMoney = order.getTotalAmount();
        DecimalFormat decimalFormat = new DecimalFormat("###################");
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", WxConfigUtil.APPID); // 应用APPID
        parameters.put("mch_id", WxConfigUtil.MCH_ID); // 微信支付分配的商户号
        parameters.put("nonce_str", WxUtil.generateNonceStr()); // 随机字符串，不长于32位
        parameters.put("sign_type", WxConfigUtil.MD5); // 签名类型
        parameters.put("body", "康钰居医疗"); // 商品描述
        parameters.put("out_trade_no", order.getOutTradeNo()); // 支付订单编号
        parameters.put("fee_type", "CNY"); // 默认人民币：CNY
        parameters.put("total_fee", decimalFormat.format(tradeMoney * 100)); // 订单总金额
        parameters.put("spbill_create_ip", request.getRemoteAddr()); //
//        parameters.put("profit_sharing", "Y");
        // 用户端实际ip
        parameters.put("notify_url", WxConfigUtil.notify_url); // 接收微信支付异步通知回调地址
        parameters.put("trade_type", "JSAPI"); // 支付类型
        parameters.put("openid", user.getOpenId()); // 用户openId
        // 设置签名
        String sign = WxUtil.createSignMD5(parameters);
        System.out.println("sign=====" + sign);
        parameters.put("sign", sign);
        // 封装请求参数结束
        String requestXML = WxUtil.getRequestXml(parameters);
        System.out.println(requestXML);
        // 调用统一下单接口
        String result = WxUtil.httpsRequest(WxConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        System.out.println(result);
        SortedMap<Object, Object> parameterMap2 = new TreeMap<Object, Object>();
        try {
            Map<String, String> map = XMLUtil.doXMLParse(result);
            parameterMap2.put("appId", WxConfigUtil.APPID);
            // parameterMap2.put("partnerid", WxConfigUtil.MCH_ID);
            // parameterMap2.put("prepayid", map.get("prepay_id"));
            parameterMap2.put("package", "prepay_id=" + map.get("prepay_id"));
            parameterMap2.put("signType", WxConfigUtil.MD5);
            parameterMap2.put("nonceStr", WxUtil.generateNonceStr());
            // 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
            parameterMap2.put("timeStamp", Integer.parseInt(Long.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
            String sign2 = WxUtil.createSignMD5(parameterMap2);
            parameterMap2.put("paySign", sign2);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderDao.updateOutTradeNo(order);
        return new Result(parameterMap2);
    }


    //分享商分润
    private Boolean distribution(Order order) {
        List<OrderDetailGoods> orderDetailGoodsListByOrderIds = orderDao.getOrderDetailGoodsListByOrderIds(order.getOrderId());
        Double rebate = 0.0;
        for (OrderDetailGoods orderDetail : orderDetailGoodsListByOrderIds) {
            rebate += orderDetail.getRebate();
        }
        String userId = order.getShareUserId();
        User user = userDao.getUserById(userId);
        User u = new User();
        u.setSumNum(user.getSumNum() + rebate);
        u.setUserId(userId);
        UserRebate userRebate = new UserRebate();
        userRebate.setUserId(order.getShareUserId());//获得返利用户ID
        userRebate.setRebateUserId(order.getUserId());//订单用户ID
        userRebate.setRebateNum(rebate);
        userRebate.setDeposits(user.getSumNum() + rebate);
        userRebate.setOrderId(order.getOrderId());
        userRebate.setRebateType("1");
        Integer res = userDao.addUserRebate(userRebate);
        Integer res1 = userDao.updateUserSumNum(u);
        if (res >= 1 && res1 >= 1) {
            return true;
        }
        return false;
    }


}
