package com.ecommerce.springboot_e_commerce_project.service;

import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    public Category addCategory(Category category) {
        return categoryRepo.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    public Category getCategoryById(int categoryId) {
        return categoryRepo.findById(categoryId).orElse(new Category(-1));
    }

    public Category updateCategory(int categoryId, Category category) {
        Category existingCategory = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if(existingCategory.getCategoryId() != -1) {
            existingCategory.setCategoryDescription(category.getCategoryDescription());
            existingCategory.setCategoryName(category.getCategoryName());
            return categoryRepo.save(existingCategory);
        }
        return existingCategory;

    }

    public Boolean deleteCategory(int categoryId) {
        Category existingCategory = categoryRepo.findById(categoryId).orElse(new Category(-1));

        if(existingCategory.getCategoryId() != -1) {
            categoryRepo.delete(existingCategory);
            return true;
        }
        return false;
    }
}
