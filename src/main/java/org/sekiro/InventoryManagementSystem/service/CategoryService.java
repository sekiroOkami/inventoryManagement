package org.sekiro.InventoryManagementSystem.service;

import org.sekiro.InventoryManagementSystem.dto.CategoryDTO;
import org.sekiro.InventoryManagementSystem.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response updateCategory(Long categoryId, CategoryDTO categoryDTO);
    Response deleteCategory(Long categoryId);
}
