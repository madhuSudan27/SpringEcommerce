package com.ecom.controller;


import com.ecom.config.AppConstants;
import com.ecom.model.Category;
import com.ecom.payload.CategoryDTO;
import com.ecom.payload.CategoryResponse;
import com.ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final  CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }




    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories( @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                              @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                              @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORY_BY, required = false) String sortBy,
                                                              @RequestParam(name = "sortOrder" , defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        if (pageNumber < 0 || pageSize < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CategoryResponse categories = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @PostMapping("/public/categories")
    public  ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {

        CategoryDTO status = categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<>(status, HttpStatus.OK);

    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO category) {

        CategoryDTO updateCategory = categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);

    }
}
