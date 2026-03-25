package com.ecommerce.springboot_e_commerce_project.mapper;


import com.ecommerce.springboot_e_commerce_project.dto.CategoryDTO;
import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto){
        Category category = new Category();

        category.setCategoryId(dto.getCategoryId());
        category.setCategoryName(dto.getCategoryName());
        category.setCategoryDescription(dto.getCategoryDescription());

        return category;
    }

    public CategoryDTO toDTO(Category category){
        CategoryDTO dto = new CategoryDTO();

        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryDescription(category.getCategoryDescription());

        return dto;
    }
}
