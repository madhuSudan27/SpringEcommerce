package com.ecom.service;

import com.ecom.model.Product;
import com.ecom.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);
}
