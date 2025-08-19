package com.ecom.service;

import com.ecom.exceptions.ApiException;
import com.ecom.exceptions.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.payload.CategoryDTO;
import com.ecom.payload.CategoryResponse;
import com.ecom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements  CategoryService{

     private final CategoryRepository categoryRepository;

     private final ModelMapper modelMapper;

     @Autowired
     CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
         this.categoryRepository = categoryRepository;
         this.modelMapper = modelMapper;
     }



    @Override
    public CategoryResponse getAllCategories() {
         List<Category> categories = categoryRepository.findAll();
         if(categories.isEmpty()){
             throw new ApiException("No categories found!");
         }

         List<CategoryDTO> categoriesDTOS = categories.stream()
                 .map(category -> modelMapper.map(category, CategoryDTO.class))
                 .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoriesDTOS);

         return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

         Category category = modelMapper.map(categoryDTO, Category.class);

         Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
         if(existingCategory != null){
             throw new ApiException("Category with name "+category.getCategoryName()+" already exists!!!");
         }

         // returns saved data from the database
         Category savedCategory = categoryRepository.save(category);
         return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        // get the list of categories from the database>
        Category category =categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));

        // delete the category from the list <- void
        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }
}
