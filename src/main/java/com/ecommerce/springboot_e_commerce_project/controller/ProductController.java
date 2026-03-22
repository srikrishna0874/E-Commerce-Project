package com.ecommerce.springboot_e_commerce_project.controller;

import com.ecommerce.springboot_e_commerce_project.dto.ProductDTO;
import com.ecommerce.springboot_e_commerce_project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @GetMapping("")
//    public ResponseEntity<List<ProductDTO>> getProducts(){
//        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id, @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Boolean deletedSuccessfully = productService.deleteProduct(id);

        if (deletedSuccessfully)
            return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.OK);

        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/category/{id}")
    public ResponseEntity<ProductDTO> addProductWithCategory(@PathVariable("id") int categoryId, @RequestBody ProductDTO productDTO) {
        try {
            return productService.addProductWithCategory(productDTO, categoryId);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("category/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable("id") int categoryId) {
        return productService.getAllProductsByCategory(categoryId);
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductDTO>> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        size = Math.min(size, 5);
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productService.getProductsByPage(pageable);
    }
}
