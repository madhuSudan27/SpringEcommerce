package com.ecom.service;

import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.payload.ProductDTO;
import com.ecom.payload.ProductResponse;
import com.ecom.repositories.CategoryRepository;
import com.ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId){
        // need to get the category from the database

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));




        // if category is found, set the category to the product
        product.setCategory(category);

        double specialPrice = product.getProductPrice() * (1 - product.getDiscount() / 100.0);
        product.setProductSpecialPrice(specialPrice);

        if(product.getProductImage() == null  || product.getProductImage().isEmpty()){
            product.setProductImage("default.jpg");
        }

        // save the product to the database
        Product savedProduct = productRepository.save(product);

        // return the saved product
        ProductDTO productDTO = modelMapper.map(savedProduct, ProductDTO.class);
        return productDTO;

    }


    public ProductResponse getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList(); // <Product, ProductDTO>

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);

        return productResponse;
    }


    public ProductResponse getProductsByCategory(Long categoryId){

        // lets find the category in the database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByProductPriceAsc(category);
        List<ProductDTO> productDTOs = products
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList(); // <Product, ProductDTO>

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);
        return productResponse;


    }

    @Override
    public ProductResponse getProductByKeyword(String keyword) {
        // do a search in the database
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase( '%' +keyword+ '%');

        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList(); // <Product, ProductDTO>

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(productDTOs);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        // find the product in the database
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Productid", productId));

        // update the product
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductImage(product.getProductImage());
        existingProduct.setProductQuantity(product.getProductQuantity());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setDiscount(product.getDiscount());
        double specialPrice = product.getProductPrice() * (1 - product.getDiscount() / 100.0);
        existingProduct.setProductSpecialPrice(specialPrice);

        // save the product to the database
        Product updatedProduct = productRepository.save(existingProduct);

        // return the updated product
        ProductDTO productDTO = modelMapper.map(updatedProduct, ProductDTO.class);
        return productDTO;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        // need to get the product from db;
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId" , productId));

        // if exists then delete from db
        productRepository.deleteById(productId);
        return modelMapper.map(existingProduct, ProductDTO.class);
    }


}
