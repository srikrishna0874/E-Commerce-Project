package com.ecommerce.springboot_e_commerce_project.service;

import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.processing.Generated;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @GeneratedValue
    public List<Product> getProducts() {
        return
    }
}
