package com.curso.api.springsecuritycourse.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.curso.api.springsecuritycourse.dto.SaveCategory;
import com.curso.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.curso.api.springsecuritycourse.persistence.entity.Category;
import com.curso.api.springsecuritycourse.persistence.repository.CategoryRepository;
import com.curso.api.springsecuritycourse.service.CategoryService;

import jakarta.validation.Valid;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable); 
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(@Valid SaveCategory saveCategory) {
        Category category = new Category();
        category.setName(saveCategory.getName());
        category.setStatus(Category.CategoryStatus.ENABLED);

        return categoryRepository.save(category);    
    }

    @Override
    public Category updateOneById(Long categoryId, @Valid SaveCategory saveCategory) {

        Category categoryFromDB = categoryRepository.findById(categoryId)
                        .orElseThrow( () -> new ObjectNotFoundException("Category not found with id "+categoryId));

        categoryFromDB.setName(saveCategory.getName());
        return categoryRepository.save(categoryFromDB); 

    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
        .orElseThrow( () -> new ObjectNotFoundException("Category not found with id "+categoryId));

        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);
        return categoryRepository.save(categoryFromDB); 
    }

}
