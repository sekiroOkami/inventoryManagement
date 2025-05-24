package org.sekiro.InventoryManagementSystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sekiro.InventoryManagementSystem.dto.CategoryDTO;
import org.sekiro.InventoryManagementSystem.dto.Response;
import org.sekiro.InventoryManagementSystem.entities.Category;
import org.sekiro.InventoryManagementSystem.exceptions.NotFoundException;
import org.sekiro.InventoryManagementSystem.repository.CategoryRepository;
import org.sekiro.InventoryManagementSystem.service.CategoryService;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response createCategory(CategoryDTO categoryDTO) {
        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<CategoryDTO> categoryDTOS = modelMapper.map(
                categories,
                new TypeToken<List<Category>>(){}.getType()
        );

        return Response.builder()
                .status(200)
                .message("success")
                .categoryDTOS(categoryDTOS)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException(
                "Category Not Found"));
        CategoryDTO categoryDTO = modelMapper.map(existingCategory, CategoryDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .categoryDTO(categoryDTO)
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException(
                "Category Not Found"));
        if (existingCategory.getName() != null) existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return Response.builder()
                .status(200)
                .message("Category successfully updated.")
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return Response.builder()
                .status(200)
                .message("Category successfully deleted.")
                .build();
    }
}
