package com.ecommerce.springboot_e_commerce_project.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private String categoryDescription;
}
