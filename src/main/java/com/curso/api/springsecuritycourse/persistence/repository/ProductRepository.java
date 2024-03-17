package com.curso.api.springsecuritycourse.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.curso.api.springsecuritycourse.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
