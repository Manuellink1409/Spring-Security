package com.curso.api.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.curso.api.springsecuritycourse.dto.SaveProduct;
import com.curso.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.curso.api.springsecuritycourse.persistence.entity.Category;
import com.curso.api.springsecuritycourse.persistence.entity.Product;
import com.curso.api.springsecuritycourse.persistence.repository.ProductRepository;
import com.curso.api.springsecuritycourse.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {

        return productRepository.findAll(pageable);

    }

    @Override
    public Optional<Product> findOneById(Long productId) {

        return productRepository.findById(productId);

    }

    @Override
    public Product createOne(SaveProduct saveProduct) {

        Product product = new Product();
        product.setName(saveProduct.getName());
        product.setPrice(saveProduct.getPrice());
        product.setStatus(Product.ProductStatus.ENABLED);

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());
        product.setCategory(category);

        return productRepository.save(product);

    }

    @Override
    public Product updateOneById(Long productId, SaveProduct saveProduct) {

        Product productFromDB = productRepository.findById(productId)
                        .orElseThrow( () -> new ObjectNotFoundException("Product not found with id "+productId));

        productFromDB.setName(saveProduct.getName());
        productFromDB.setPrice(saveProduct.getPrice());

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());
        productFromDB.setCategory(category);

        return productRepository.save(productFromDB);

    }

    @Override
    public Product disableOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                        .orElseThrow( () -> new ObjectNotFoundException("Product not found with id "+productId));
        productFromDB.setStatus(Product.ProductStatus.DISABLED);
        
        return productRepository.save(productFromDB);
    }

}
