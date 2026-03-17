package com.ecommerce.springboot_e_commerce_project.controller;

import com.ecommerce.springboot_e_commerce_project.model.Product;
import com.ecommerce.springboot_e_commerce_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product p = productService.getProductById(id);
        System.out.println(p.getProductId());
        if(p.getProductId() > 0){
            return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product){
        try{
            return new ResponseEntity<>(productService.addProduct(product),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id,@Valid @RequestBody Product product){
        Product p = productService.updateProduct(id,product);

        if(p.getProductId() == -1)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Boolean deletedSuccessfully = productService.deleteProduct(id);

        if(deletedSuccessfully)
            return new ResponseEntity<>("Product Deleted Successfully",HttpStatus.OK);

        return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
    }

    @PostMapping("/category/{id}")
    public ResponseEntity<Product> addProductWithCategory(@PathVariable("id") int categoryId, @Valid @RequestBody Product product){
        try{
            return productService.addProductWithCategory(product,categoryId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("category/{id}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable("id") int categoryId){
        return productService.getAllProductsByCategory(categoryId);
    }
}
