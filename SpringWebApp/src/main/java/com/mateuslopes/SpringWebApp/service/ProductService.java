package com.mateuslopes.SpringWebApp.service;

import com.mateuslopes.SpringWebApp.model.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    List<Product> products = Arrays.asList(
            new Product(1, "Iphone", 5000),
            new Product(2, "Camera", 7000),
            new Product(3, "Macbook", 10000)
    );

    public List<Product> getProducts(){
        return products;
    }

}
