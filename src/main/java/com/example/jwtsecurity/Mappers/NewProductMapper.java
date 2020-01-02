package com.example.jwtsecurity.Mappers;

import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Model.Product;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Repository.ProductRepository;
import com.example.jwtsecurity.Views.ProductView;
import org.springframework.stereotype.Component;

@Component
public class NewProductMapper {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public NewProductMapper(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    ToProductEntity mpya = (ProductView view) -> {
        Category category = this.categoryRepository.findByName(view.getCategory());
        return new Product(view.getTitle(), view.getPrice(), category, view.getImageUrl());

    };

}
