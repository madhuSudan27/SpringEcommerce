package com.ecom.ecom.service;

import com.ecom.ecom.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements  CategoryService{

    private final List<Category> categoryList;

    CategoryServiceImpl() {
        categoryList =  new ArrayList<>();
    }

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(categoryList);
    }

    @Override
    public void createCategory(Category category) {
        categoryList.add(category);
    }
}
