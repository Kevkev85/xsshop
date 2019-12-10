package com.example.jwtsecurity.Repository;

import com.example.jwtsecurity.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByName();

    Category findByName(String s);


    void deleteAll();
}
