package com.ecommerce.springboot_e_commerce_project.service;

import com.ecommerce.springboot_e_commerce_project.dto.CategoryDTO;
import com.ecommerce.springboot_e_commerce_project.mapper.CategoryMapper;
import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.repo.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryMapper categoryMapper;


    public CategoryDTO addCategory(@Valid CategoryDTO categoryDTO) {
        Category savedCategory = categoryRepo.save(categoryMapper.toEntity(categoryDTO));
        return categoryMapper.toDTO(savedCategory);
    }

    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categories)
            categoryDTOList.add(categoryMapper.toDTO(category));
        return categoryDTOList;
    }

    public CategoryDTO getCategoryById(int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElse(new Category(-1));
        return categoryMapper.toDTO(category);
    }

    public CategoryDTO updateCategory(int categoryId, @Valid CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if (existingCategory.getCategoryId() != -1) {
            existingCategory.setCategoryDescription(categoryDTO.getCategoryDescription());
            existingCategory.setCategoryName(categoryDTO.getCategoryName());

            existingCategory = categoryRepo.save(existingCategory);
        }
        return categoryMapper.toDTO(existingCategory);

    }

    public Boolean deleteCategory(int categoryId) {
        Category existingCategory = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if (existingCategory.getCategoryId() != -1) {
            categoryRepo.delete(existingCategory);
            return true;
        }
        return false;
    }
}
