package com.yunniu.lease.service;

import com.yunniu.lease.model.Product;

import java.util.List;

public interface ProductService {
    //1.查询的service
    public List<Product> getProductService();

    //2.插入的service
    public Integer addProductService(Product product);

    //3.删除的service
    public Integer deleteProductService(Integer id);

    //4.修改的service
    public Integer updateProductService(Product product);

    //5.根据当前ID查询对应的数据
    public Product getProductID(Integer id);

}
