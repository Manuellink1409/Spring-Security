package com.curso.api.springsecuritycourse.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.api.springsecuritycourse.dto.SaveCategory;
import com.curso.api.springsecuritycourse.persistence.entity.Category;

import jakarta.validation.Valid;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(@Valid SaveCategory saveCategory);

    Category updateOneById(Long categoryId, @Valid SaveCategory saveCategory);

    Category disableOneById(Long categoryId);

}
