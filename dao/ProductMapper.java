package com.yunniu.lease.dao;

import com.yunniu.lease.model.Product;
import com.yunniu.lease.model.UserAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    /**
     * 删除product
     */
    @Delete("<script>" +
            "DELETE FROM product WHERE id=#{id,jdbcType=INTEGER}" +
            "</script>")
    public Integer deleteProduct(Integer id);

    /**
     * 增加product
     */
    @Insert(value = {"<script>",
            "INSERT INTO product(productName, price, number)",
            "VALUES(#{productName},#{price},#{number})",
            "</script>"})
    public Integer addProduct(Product product);

    /**单个查询
     */
    @Select(value = {"<script>",
            "SELECT * FROM product WHERE id = #{id}",
            "</script>"})
    public Product getProductID(Integer id);

    /**
     * 全表查询
     * @return
     */
    @Select(value = {"<script>",
            "SELECT * FROM product",
            "</script>"})
    public List<Product> getProduct();

    /**
     * 修改product
     */
    @Update("<script>"+
            "UPDATE product SET `productName`=#{productName},`price`=#{price}, number=#{number} "+
            "WHERE id = #{id}"+
            "</script>")
    public Integer updateProduct(Product product);
}