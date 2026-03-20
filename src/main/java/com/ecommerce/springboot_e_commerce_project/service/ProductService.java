package com.ecommerce.springboot_e_commerce_project.service;

import com.ecommerce.springboot_e_commerce_project.dto.ProductDTO;
import com.ecommerce.springboot_e_commerce_project.mapper.ProductMapper;
import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.model.Product;
import com.ecommerce.springboot_e_commerce_project.repo.CategoryRepository;
import com.ecommerce.springboot_e_commerce_project.repo.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> getProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product : products){
            productDTOS.add(productMapper.toDTO(product));
        }

        return productDTOS;

    }

    public ResponseEntity<ProductDTO> getProductById(int id) {
        Product product = productRepo.findById(id).orElse(new Product(-1));
//        System.out.println("In Service " +product.getProductId());
        if(product.getProductId() == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(productMapper.toDTO(product),HttpStatus.OK);
    }

    public ResponseEntity<ProductDTO> updateProduct(int id, ProductDTO product) {
        Product existingProduct = productRepo.findById(id).orElse(new Product(-1));
        Category category = categoryRepo.getById(product.getCategoryId());
        if(existingProduct.getProductId() == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setProductImageUrl(product.getProductImageUrl());
        existingProduct.setProductStockQuantity(product.getProductStockQuantity());
        existingProduct.setCategory(category);

        productRepo.save(existingProduct);
        return new ResponseEntity<>(productMapper.toDTO(existingProduct),HttpStatus.OK);
    }

    public Boolean deleteProduct(int id) {
        Product p = productRepo.findById(id).orElse(new Product(-1));

        if(p.getProductId() != -1){
            productRepo.delete(p);
            return true;
        }
        return false;
    }

    public ResponseEntity<ProductDTO> addProductWithCategory(@Valid ProductDTO productDTO, int categoryId) {
        productDTO.setCategoryId((categoryId));
        Product product = productMapper.toEntity(productDTO);

        Category category = categoryRepo.findById(categoryId).orElse(new Category(-1));
//        System.out.println(category.getCategoryId());

        if(category.getCategoryId() == -1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setCategory(category);
        Product savedProduct = productRepo.save(product);
        return new ResponseEntity<>(productMapper.toDTO(savedProduct),HttpStatus.CREATED);
    }

    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if(category.getCategoryId() == -1){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Product> products = productRepo.findAllByCategory(category);
        List<ProductDTO> productDTOS = new ArrayList<>();

        for(Product product: products){
            productDTOS.add(productMapper.toDTO(product));
        }

        return new ResponseEntity<>(productDTOS,HttpStatus.OK);
    }

    public ResponseEntity<Page<ProductDTO>> getProductsByPage(Pageable pageable) {
        Page<Product> productPage = productRepo.findAll(pageable);

        Page<ProductDTO> dtoPage = productPage.map(product -> productMapper.toDTO(product));

        return new ResponseEntity<>(dtoPage,HttpStatus.OK);
    }
}
