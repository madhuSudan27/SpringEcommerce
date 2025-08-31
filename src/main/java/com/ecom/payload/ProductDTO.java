package com.ecom.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private String productImage;
    private Integer productQuantity;
    private Double productPrice;
    private Double productSpecialPrice;
    private Double discount;

}
