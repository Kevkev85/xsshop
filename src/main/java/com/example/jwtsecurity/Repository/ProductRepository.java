package com.example.jwtsecurity.Repository;

import com.example.jwtsecurity.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByTitle();
    boolean existsById(Long id);

}
