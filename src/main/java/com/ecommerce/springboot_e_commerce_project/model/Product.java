package com.ecommerce.springboot_e_commerce_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false)
    @NotBlank
    private String productName;
    private String productDescription;

    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal productPrice;

    private String productImageUrl;
    @PositiveOrZero
    private Integer productStockQuantity;
    //private Double productRating;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;

    public Product(int id){
        this.productId = id;
    }

}
