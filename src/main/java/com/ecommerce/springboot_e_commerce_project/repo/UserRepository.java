package com.ecommerce.springboot_e_commerce_project.repo;

import com.ecommerce.springboot_e_commerce_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
