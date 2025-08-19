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
    public void createCategory(Category category) {
         Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
         if(savedCategory != null){
             throw new ApiException("Category with name "+category.getCategoryName()+" already exists!!!");
         }
         categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        // get the list of categories from the database>
        Category category =categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));


        // delete the category from the list
        categoryRepository.delete(category);

        return "Category deleted successfully with Id: " + category.getCategoryId();
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", categoryId));

        category.setCategoryId(categoryId);

        existingCategory = categoryRepository.save(category);

        return existingCategory;
    }
}
