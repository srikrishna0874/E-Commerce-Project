package com.ecommerce.springboot_e_commerce_project.controller;

import com.ecommerce.springboot_e_commerce_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String getProducts(){
        productService.getProducts();
    }
}
