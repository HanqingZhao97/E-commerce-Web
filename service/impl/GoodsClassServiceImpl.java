package com.yunniu.lease.service.impl;

import com.yunniu.lease.dao.GoodsClassDao;
import com.yunniu.lease.model.*;
import com.yunniu.lease.model.TableResult;
import com.yunniu.lease.service.GoodsClassService;
import com.yunniu.lease.util.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class GoodsClassServiceImpl implements GoodsClassService {

    @Resource
    GoodsClassDao goodsClassDao;


    @Override
    public TableResult getGoodsClassListPage(HttpServletRequest request) {
        Map<String, Object> params = Pages.getParams(request);
        Integer count = goodsClassDao.getGoodsClassListCount(params);
        List<GoodsClass> list = goodsClassDao.getGoodsClassList(params);
        TableResult tableResult = new TableResult(count, list);
        return tableResult;
    }

    @Override
    public GoodsClass getGoodsClassById(String id) {
        return goodsClassDao.getGoodsClassById(id);
    }


    @Override
    public List<GoodsClass> getPgoodsClassList() {
        return goodsClassDao.getPgoodsClassList();
    }

    @Override
    public List<GoodsClass> getPgoodsClassByPidList(String pid) {
        return goodsClassDao.getPgoodsClassByPidList(pid);
    }

    @Override
    public Result updateGoodsClass(GoodsClass goodsClass) {
        String id = goodsClass.getGoodsClassId();
        MultipartFile goodsClassImg = goodsClass.getFile();
        if (goodsClassImg != null && !goodsClassImg.isEmpty()) {
            String realpath = FileUtil.saveFile(goodsClassImg, "house");
            String url = COSUtil.uploadFileByPath(realpath);
            if (url != null && !"".equals(url)) {
                goodsClass.setGoodsClassImg(url);
            }
        }
        if (id == null || id.equals("")) {
            Integer res = goodsClassDao.addGoodsClass(goodsClass);
            if (res > 0) {
                return new Result(100, "新增成功");
            }
            return new Result(103);
        } else {
            GoodsClass goodsClass1 = goodsClassDao.getGoodsClassById(goodsClass.getGoodsClassId());
            Integer res = goodsClassDao.updateGoodsClass(goodsClass);
            if (res > 0) {
                if (null != goodsClass1.getGoodsClassImg()) {
                    String imgName = COSUtil.getName(goodsClass1.getGoodsClassImg());
                    OssUtil.delOssImg(goodsClass1.getGoodsClassImg());
                    COSUtil.delete(imgName);
                }
                return new Result(100, "更新成功");
            }
            return new Result(103);
        }
    }

    @Override
    public Result delGoodsClass(String id) {
        Integer res = goodsClassDao.delGoodsClass(id);
        if (res > 0) {
            return new Result(100, "删除成功");
        }
        return new Result(103);
    }


    @Override
    public Result updateBanner(Banner banner) {
        Banner oldBanner = goodsClassDao.getBanner();
        String[] picArr = banner.getImgArr();
        String pics = "";
        if (picArr.length > 0) {
            for (int i = 0; i < picArr.length; i++) {
                if (picArr[i].startsWith("data:image")) {
                    String suffix = picArr[i].substring(picArr[i].indexOf("/") + 1, picArr[i].indexOf(";"));
                    String realpath = FileUtil.BasePath + "/goods/" + UUIDUtils.getUUID() + "." + suffix;
                    if (ImageUtil.GenerateImage(picArr[i + 1], realpath)) {
                        String url = COSUtil.uploadFileByPath(realpath);
//                        FileUtil.deleteFilesByRealPath(realpath);
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
        banner.setBannerUrls(pics);
        int res = goodsClassDao.updateBanner(banner);
        if (res > 0) {
            //删除oss图片
            String[] oldArr = oldBanner.getBannerUrls().split(",");
            String[] newArr = banner.getBannerUrls().split(",");
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
            return new Result("轮播图更新成功");
        }
        return new Result().error();
    }

    @Override
    public Banner getBanner() {
        return goodsClassDao.getBanner();
    }

    @Override
    public List<Dictionary> getDictionary(String type) {
        return goodsClassDao.getDictionary(type);
    }


}
