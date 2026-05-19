package com.mateuslopes.SpringWebApp.service;

import com.mateuslopes.SpringWebApp.model.Product;
import com.mateuslopes.SpringWebApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProductId(int prodId) {
        return productRepository.findById(prodId).orElse(new Product());
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(int prodId) {
        productRepository.deleteById(prodId);
    }
}
