package com.yunniu.lease.service;

import com.yunniu.lease.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface GoodsService {

    TableResult getGoodsList(HttpServletRequest request);

    TableResult getAppGoodsList(HttpServletRequest request);

    Goods getGoodsById(String id);

    Result getGoodsInfo(String id);

    Result addGoods(Goods goods);

    Result updateGoods(Goods goods);

    Result changeAbolishState(String goodsId, String abolishState);

    Result changeDeleteState(String goodsId);

    Result updateGoodsSpec(GoodsSpecs goodsSpecs);

    List<GoodsSpec> getGoodsSpecByGoodsId(String goodsId);


}
