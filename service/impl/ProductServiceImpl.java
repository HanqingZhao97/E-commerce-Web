package com.yunniu.lease.service.impl;
import com.yunniu.lease.dao.ProductMapper;
import com.yunniu.lease.model.Product;
import com.yunniu.lease.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hanqing Zhao
 * @date 2022/09/05
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    //Resource 帮忙 new 一个 object
    private ProductMapper productMapper;

    @Override
    public List<Product> getProductService() {
        List<Product> products = productMapper.getProduct();
        return products;
    }

    @Override
    public Integer addProductService(Product product) {
        //1 means success, 0 means fail
        Integer info = productMapper.addProduct(product);
        return info;
    }

    @Override
    public Integer deleteProductService(Integer id) {
        Integer info = productMapper.deleteProduct(id);
        return info;
    }

    @Override
    public Integer updateProductService(Product product) {
        Integer info = productMapper.updateProduct(product);
        return info;
    }

    @Override
    public Product getProductID(Integer id) {
        Product product = productMapper.getProductID(id);
        return product;
    }
}
