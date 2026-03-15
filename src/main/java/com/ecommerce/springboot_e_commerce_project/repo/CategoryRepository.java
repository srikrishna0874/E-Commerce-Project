package com.ecommerce.springboot_e_commerce_project.repo;

import com.ecommerce.springboot_e_commerce_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
