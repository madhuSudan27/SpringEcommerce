package com.ecom.ecom.service;

import com.ecom.ecom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements  CategoryService{

    private final List<Category> categoryList;
    private  Long nextId = 1L;

    CategoryServiceImpl() {
        categoryList =  new ArrayList<>();
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryList);
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categoryList.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category category = categoryList.stream()
                        .filter(c -> c.getCategoryId().equals(categoryId))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));


        // delete the category from the list
        categoryList.remove(category);

        return "Category deleted successfully with Id: " + category.getCategoryId();
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {

        Category existingCategory = categoryList.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        existingCategory.setCategoryName(category.getCategoryName());

        return existingCategory;
    }
}
