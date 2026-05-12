package com.mateuslopes.SpringWebApp.controller;

import com.mateuslopes.SpringWebApp.model.Product;
import com.mateuslopes.SpringWebApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping("product/{prodId}")
    public Product getProductById(@PathVariable int prodId){
        return service.getProductId(prodId);
    }

    @PostMapping("product")
    public void addProduct(@RequestBody Product product){
        service.addProduct(product);
    }

}
