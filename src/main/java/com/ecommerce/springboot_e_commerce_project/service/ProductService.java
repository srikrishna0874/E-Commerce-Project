package com.ecommerce.springboot_e_commerce_project.service;

import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.model.Product;
import com.ecommerce.springboot_e_commerce_project.repo.CategoryRepository;
import com.ecommerce.springboot_e_commerce_project.repo.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        return productRepo.findById(id).orElse(new Product(-1));
    }

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public Product updateProduct(int id, Product product) {
        Product existingProduct = productRepo.findById(id).orElse(new Product(-1));

        if(existingProduct.getProductId() == -1) return existingProduct;

        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setProductImageUrl(product.getProductImageUrl());
        existingProduct.setProductStockQuantity(product.getProductStockQuantity());

        productRepo.save(existingProduct);
        return existingProduct;
    }

    public Boolean deleteProduct(int id) {
        Product p = productRepo.findById(id).orElse(new Product(-1));

        if(p.getProductId() != -1){
            productRepo.delete(p);
            return true;
        }

        return false;

    }

    public ResponseEntity<Product> addProductWithCategory(@Valid Product product, int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if(category.getCategoryId() == -1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setCategory(category);
        return new ResponseEntity<>(productRepo.save(product),HttpStatus.CREATED);
    }

    public ResponseEntity<List<Product>> getAllProductsByCategory(int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if(category.getCategoryId() == -1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productRepo.findAllByCategory(category),HttpStatus.OK);
    }
}
