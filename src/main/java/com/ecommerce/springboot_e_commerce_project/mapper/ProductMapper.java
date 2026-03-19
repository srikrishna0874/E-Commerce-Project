package com.ecommerce.springboot_e_commerce_project.mapper;

import com.ecommerce.springboot_e_commerce_project.dto.ProductDTO;
import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.model.Product;
import com.ecommerce.springboot_e_commerce_project.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    CategoryRepository categoryRepo;

    public Product toEntity(ProductDTO dto){
        Product product = new Product();

        product.setProductName(dto.getProductName());
        product.setProductPrice(dto.getProductPrice());
        product.setProductDescription(dto.getProductDescription());
        product.setProductImageUrl(dto.getProductImageUrl());
        product.setProductStockQuantity(dto.getProductStockQuantity());

        Category category = categoryRepo.findById(dto.getCategoryId())
                                        .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        return product;
    }

    public ProductDTO toDTO(Product product){
        ProductDTO dto = new ProductDTO();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductImageUrl(product.getProductImageUrl());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductStockQuantity(product.getProductStockQuantity());

        Category category = product.getCategory();

        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryId(category.getCategoryId());

        return dto;
    }
}
