package com.ecommerce.springboot_e_commerce_project.controller;

import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            System.out.println("Inserting new category");
            return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            if (category.getCategoryId() == -1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable("id") int categoryId) {
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            if (updatedCategory.getCategoryId() == -1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int categoryId) {
        try {
            Boolean isDeleted = categoryService.deleteCategory(categoryId);
            if (isDeleted)
                return new ResponseEntity<>("Category with id = " + categoryId + " Deleted successfully!", HttpStatus.OK);
            return new ResponseEntity<>("Category with id = " + categoryId +" not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
