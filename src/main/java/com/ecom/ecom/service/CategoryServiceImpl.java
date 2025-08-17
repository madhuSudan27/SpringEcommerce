package com.ecom.ecom.service;

import com.ecom.ecom.model.Category;
import com.ecom.ecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements  CategoryService{


//    private final List<Category> categoryList;
//    private  Long nextId = 1L;


     private final CategoryRepository categoryRepository;

     CategoryServiceImpl(CategoryRepository categoryRepository) {
         this.categoryRepository = categoryRepository;
     }

//    CategoryServiceImpl() {
//        categoryList =  new ArrayList<>();
//    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        // get the list of categories from the database>
        Category category =categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));


        // delete the category from the list
        categoryRepository.delete(category);

        return "Category deleted successfully with Id: " + category.getCategoryId();
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        category.setCategoryId(categoryId);

        existingCategory = categoryRepository.save(category);

        return existingCategory;
    }
}
