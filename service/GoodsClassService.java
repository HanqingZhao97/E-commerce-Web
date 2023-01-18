package com.yunniu.lease.service;

import com.yunniu.lease.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodsClassService {

    TableResult getGoodsClassListPage(HttpServletRequest request);

    GoodsClass getGoodsClassById(String id);

    List<GoodsClass> getPgoodsClassList();

    List<GoodsClass> getPgoodsClassByPidList(String pid);

    Result updateGoodsClass(GoodsClass goodsClass);

    Result delGoodsClass(String id);

    Result updateBanner(Banner banner);

    Banner getBanner();

    List<Dictionary> getDictionary(String type);

}
