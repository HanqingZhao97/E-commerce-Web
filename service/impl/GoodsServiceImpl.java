package com.yunniu.lease.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yunniu.lease.dao.GoodsClassDao;
import com.yunniu.lease.dao.GoodsDao;
import com.yunniu.lease.model.*;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.GoodsService;
import com.yunniu.lease.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsDao goodsDao;
    @Resource
    private GoodsClassDao goodsClassDao;

    @Override
    public TableResult getGoodsList(HttpServletRequest request) {
        Map<String, Object> map = Pages.getParams(request);
        int count = goodsDao.getGoodsListCount(map);
        List<Goods> list = goodsDao.getGoodsList(map);
        return new TableResult(count, list);
    }

    @Override
    public TableResult getAppGoodsList(HttpServletRequest request) {
        Map<String, Object> map = Pages.getParams(request);
        map.put("abolishState", "1");
        int count = goodsDao.getGoodsListCount(map);
        List<Goods> list = goodsDao.getGoodsList(map);
        return new TableResult(count, list);
    }

    @Override
    public Goods getGoodsById(String id) {
        return goodsDao.getGoodsById(id);
    }


    @Override
    public Result getGoodsInfo(String id) {
        Goods goods = goodsDao.getGoodsById(id);
        if (null == goods) {
            return new Result(102, "没有查到该商品");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("goodsId", goods.getGoodsId());
        jsonObject.put("goodsImgs", goods.getGoodsImgs());
        jsonObject.put("goodsName", goods.getGoodsName());
        jsonObject.put("goodsPrice", goods.getGoodsPrice());
//        jsonObject.put("goodsStock", goods.getGoodsStock());
        jsonObject.put("goodsDetail", goods.getGoodsDetail());
        jsonObject.put("goodsDes", goods.getGoodsDes());
        jsonObject.put("deposit", goods.getDeposit());
        List<Dictionary> list = goodsClassDao.getDictionary("service,delivery");//查询 商家服务和配送方式
        List<GoodsSpec> goodsSpec = goodsDao.getGoodsSpecByGoodsId(id);//查询商品规格
        List<Dictionary> service = new ArrayList<>();
        List<Dictionary> delivery = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Dictionary dictionary = list.get(i);
            if (dictionary.getType().equals("service")) {
                service.add(dictionary);
            } else if (dictionary.getType().equals("delivery")) {
                delivery.add(dictionary);
            }
        }
        jsonObject.put("goodsSpec", goodsSpec);
        jsonObject.put("delivery", delivery);
        jsonObject.put("service", service);
        return new Result(100, "成功", jsonObject);
    }


    @Override
    public Result addGoods(Goods goods) {
        String oldGoodsId = goodsDao.getGoodsIdByName(goods.getGoodsName());
        if (oldGoodsId != null) {
            return new Result().error("商品名称已存在!");
        }
        String[] imgArr = goods.getGoodsImgArr();
        String pics = "";
        if (imgArr.length > 0) {
            for (int i = 0; i < imgArr.length; i++) {
                if (imgArr[i].startsWith("data:image")) {
                    String suffix = imgArr[i].substring(imgArr[i].indexOf("/") + 1, imgArr[i].indexOf(";"));
                    String realpath = FileUtil.BasePath + "/goods/" + UUIDUtils.getUUID() + "." + suffix;
                    if (ImageUtil.GenerateImage(imgArr[i + 1], realpath)) {
                        String url = COSUtil.uploadFileByPath(realpath);
                        if ("".equals(pics)) {
                            pics = url;
                        } else {
                            pics = pics + "," + url;
                        }
                    }
                }
            }
        }
        if ("".equals(pics)) {
            return new Result().error("图片不能为空!");
        }
        goods.setGoodsImgs(pics);
        if (pics.indexOf(",") > -1) {
            goods.setGoodsIndexImg(pics.substring(0, pics.indexOf(",")));
        } else {
            goods.setGoodsIndexImg(pics);
        }

        int res = goodsDao.addGoods(goods);
        if (res > 0) {
            return new Result("新增商品成功");
        }
        return new Result().error();
    }

    @Override
    public Result updateGoods(Goods goods) {
        if (goods.getGoodsId() == null || goods.getGoodsId().equals("")) {
            return addGoods(goods);
        }
        String oldGoodsId = goodsDao.getGoodsIdByName(goods.getGoodsName());
        if (oldGoodsId != null && !oldGoodsId.equals(goods.getGoodsId())) {
            return new Result().error("商品名称已存在!");
        }
        Goods oldGoods = goodsDao.getGoodsById(goods.getGoodsId());
        String[] picArr = goods.getGoodsImgArr();
        String pics = "";
        if (picArr.length > 0) {
            for (int i = 0; i < picArr.length; i++) {
                if (picArr[i].startsWith("data:image")) {
                    String suffix = picArr[i].substring(picArr[i].indexOf("/") + 1, picArr[i].indexOf(";"));
                    String realpath = FileUtil.BasePath + "/goods/" + UUIDUtils.getUUID() + "." + suffix;
                    if (ImageUtil.GenerateImage(picArr[i + 1], realpath)) {
                        String url = COSUtil.uploadFileByPath(realpath);
                        FileUtil.deleteFilesByRealPath(realpath);
                        if ("".equals(pics)) {
                            pics = url;
                        } else {
                            pics = pics + "," + url;
                        }
                    } else {
                        return new Result().error("图片存储出错!");
                    }
                } else if (picArr[i].startsWith("http")) {
                    picArr[i] = picArr[i].replace("amp;", "");
                    if ("".equals(pics)) {
                        pics = picArr[i];
                    } else {
                        pics = pics + "," + picArr[i];
                    }
                }
            }
        }
        goods.setGoodsImgs(pics);
        if (pics.indexOf(",") > -1) {
            goods.setGoodsIndexImg(pics.substring(0, pics.indexOf(",")));
        } else {
            goods.setGoodsIndexImg(pics);
        }
        int res = goodsDao.updateGoods(goods);
        if (res > 0) {
            //删除oss图片
            String[] oldArr = oldGoods.getGoodsImgs().split(",");
            String[] newArr = goods.getGoodsImgs().split(",");
            for (int i = 0; i < oldArr.length; i++) {
                boolean b = true;
                String imgName = COSUtil.getName(oldArr[i]);
                for (int j = 0; j < newArr.length; j++) {
                    if (newArr[j].indexOf(imgName) > -1) {
                        b = false;
                    }
                }
                if (b) {
                    System.out.println("删除图片" + oldArr[i]);
                    COSUtil.delete(imgName);
                }
            }
            return new Result("更新商品成功");
        }
        return new Result().error();
    }

    @Override
    public Result changeAbolishState(String goodsId, String abolishState) {
        Integer res = goodsDao.changeAbolishState(goodsId, abolishState);
        if (res > 0) {
            if (abolishState.equals("0")) {
                return new Result(100, "商品已下架");
            } else if (abolishState.equals("1")) {
                return new Result(100, "商品已上架");
            }
        }
        return new Result(103);
    }

    @Override
    public Result changeDeleteState(String goodsId) {
        Integer res = goodsDao.changeDeleteState(goodsId);
        if (res > 0) {
            return new Result(100, "商品已删除");
        }
        return new Result(103);
    }


    public static void main(String[] args) {
        String[] ss = {"11", "22", "33"};
        System.out.println(ss[3]);
    }


    @Override
    public Result updateGoodsSpec(GoodsSpecs goodsSpecs) {
        String goodsId = goodsSpecs.getGoodsId();
        String[] goodsSpecIdArr = goodsSpecs.getGoodsSpecId();
        Integer del = goodsDao.deleteGoodsSpecByGoodsId(goodsId, StringUtils.join(goodsSpecIdArr, ","));
        String[] goodsSpecName = goodsSpecs.getGoodsSpecName();
        MultipartFile[] goodsSpecImg = goodsSpecs.getGoodsSpecImg();
        String msg = "";
        for (int i = 0; i < goodsSpecName.length; i++) {
            GoodsSpec goodsSpec = new GoodsSpec();
            goodsSpec.setGoodsId(goodsId);
//            goodsSpec.setGoodsSpecStock(goodsSpecs.getGoodsSpecStock()[i]);
            goodsSpec.setGoodsSpecName(goodsSpecName[i]);
            goodsSpec.setGoodsPrice(goodsSpecs.getGoodsPrice()[i]);
            if (null != goodsSpecs.getDeposit() && goodsSpecs.getDeposit().length >= i) {
                goodsSpec.setDeposit(goodsSpecs.getDeposit()[i]);
            }
            MultipartFile file = goodsSpecImg[i];
            if (file != null && !file.isEmpty()) {
                String realpath = FileUtil.saveFile(file, "house");
                String url = COSUtil.uploadFileByPath(realpath);
                if (url != null && !"".equals(url)) {
                    goodsSpec.setGoodsSpecImg(url);
                }
            }
            if (i <= goodsSpecIdArr.length - 1 && goodsSpecIdArr[i] != null) {
                String img = goodsSpec.getGoodsSpecImg();
                goodsSpec.setGoodsSpecId(goodsSpecIdArr[i]);
                if (img != null) {
                    GoodsSpec oldGoodsSpec = goodsDao.getGoodsSpecById(goodsSpecIdArr[i], goodsId);
                    String fileName = COSUtil.getName(oldGoodsSpec.getGoodsSpecImg());
                    COSUtil.delete(fileName);
                }
                Integer res = goodsDao.updateGoodsSpec(goodsSpec);
                if (res >= 1) {
                    msg = "更新成功";
                }

            } else {
                Result result = addGoodsSpec(goodsSpec);
                if (result.getCode() == 102) {
                    return result;
                } else {
                    msg = "新增成功";
                }
            }
        }
        return new Result(100, msg);
    }


    public Result addGoodsSpec(GoodsSpec goodsSpec) {
        GoodsSpec goodsSpec1 = goodsDao.getGoodsSpecByName(goodsSpec);
        if (goodsSpec1 != null) {
            return new Result(102, "规格" + goodsSpec.getGoodsSpecName() + "已存在!");
        }
        int res = goodsDao.addGoodsSpec(goodsSpec);
        if (res > 0) {
            return new Result(100, "添加成功");
        }
        return new Result(102, "规格:" + goodsSpec.getGoodsSpecName() + "添加失败");
    }

    @Override
    public List<GoodsSpec> getGoodsSpecByGoodsId(String goodsId) {
        List<GoodsSpec> goodsSpecByGoodsId = goodsDao.getGoodsSpecByGoodsId(goodsId);
        if (goodsSpecByGoodsId.size() == 0) {
            goodsSpecByGoodsId.add(new GoodsSpec());
        }
        return goodsSpecByGoodsId;
    }
}
