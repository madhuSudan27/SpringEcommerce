package com.ecom.service;

import com.ecom.model.Product;
import com.ecom.payload.ProductDTO;
import com.ecom.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);
    ProductResponse getAllProducts();
}
