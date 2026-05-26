package com.mateuslopes92.ecom_proj.controller;

import com.mateuslopes92.ecom_proj.model.Product;
import com.mateuslopes92.ecom_proj.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @RequestMapping("/")
    public String greet(){
        return "Hello";
    }

    @RequestMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
