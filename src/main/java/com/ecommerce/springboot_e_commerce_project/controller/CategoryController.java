package com.ecommerce.springboot_e_commerce_project.controller;

import com.ecommerce.springboot_e_commerce_project.dto.CategoryDTO;
import com.ecommerce.springboot_e_commerce_project.model.Category;
import com.ecommerce.springboot_e_commerce_project.service.CategoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            System.out.println("Inserting new category");
            return new ResponseEntity<>(categoryService.addCategory(categoryDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") int categoryId) {
        try {
            CategoryDTO category = categoryService.getCategoryById(categoryId);
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
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") int categoryId) {
        try {
            CategoryDTO updatedCategoryDto = categoryService.updateCategory(categoryId, categoryDTO);
            if (updatedCategoryDto.getCategoryId() == -1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
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
