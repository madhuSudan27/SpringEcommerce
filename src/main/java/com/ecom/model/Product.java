package com.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    private String productImage;
    private Integer productQuantity;
    private Double productPrice;
    private Double discount;
    private Double productSpecialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
