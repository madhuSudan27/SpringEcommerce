package com.ecom.ecom.service;

import com.ecom.ecom.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    public void createCategory(Category category);
}
