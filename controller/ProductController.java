package com.yunniu.lease.controller;

import com.yunniu.lease.model.*;
import com.yunniu.lease.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public Result updateProduct(Product product) {
        Result result = null;
        Integer info = productService.updateProductService(product);
        if(info==1){
            result = new Result(100, "success", info);
        }else{
            result = new Result(200, "fail", info);
        }
        return result;
    }
    //分页查询
    @RequestMapping(value = "/getPageList",method= RequestMethod.POST)
    @ResponseBody
    public TableResult productPageList(Model model, HttpServletRequest request){
        Map<String, Object> params= Pages.getParams(request);
        System.out.println("---------------");
        System.out.println("---------------");

        List<Product> list = productService.getProductService();
        System.out.println("查询出来的数据="+list.toString());

        TableResult tableResult = new TableResult(1, list);

        return tableResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Integer add(Product product){
        Integer info=0;
        System.out.println(product.toString());
        System.out.println(product.toString());
        info=productService.addProductService(product);
        return info;
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public Integer deleteUser(@RequestBody UserAdmin user) {
        Integer info = productService.deleteProductService(user.getId());
        return info;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String productList(
            Model model, HttpServletRequest request){
        return "product/product_list";
    }

    //用户添加路径
    @RequestMapping(value = "/productAddUrl",method = RequestMethod.GET)
    public String productAddUrl(Model model, HttpServletRequest request){

        return "product/product_add";
    }

    //用户修改路径
    @RequestMapping(value = "/UpdateUrl",method = RequestMethod.GET)
    public String productUpdate(Model model, HttpServletRequest request){
        //接受前端发过来的ID，可通过ID找出当前ID对应的数据
        String id = request.getParameter("id");
        System.out.println("12345");
        Product product = productService.getProductID(Integer.parseInt(id));
        //可以通过model将查询到的对象带入到相对应的界面
        model.addAttribute("product",product);
        return "product/product_edit";
    }

}
