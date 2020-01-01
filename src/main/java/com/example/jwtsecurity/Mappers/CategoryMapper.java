package com.example.jwtsecurity.Mappers;

import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Views.CategoryView;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category convertToCategoryEntity(CategoryView viewModel){
        return new Category(viewModel.getName());

    }
}
