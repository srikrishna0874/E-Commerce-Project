package com.ecommerce.springboot_e_commerce_project.repo;

import com.ecommerce.springboot_e_commerce_project.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
