package com.example.jwtsecurity.Mappers;

import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Views.CategoryView;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryView convertToCategoryView(Category entity) {
        var viewModel = new CategoryView();
        viewModel.setId(entity.getId());
        viewModel.setName(entity.getName());

        return viewModel;
    }

    public Category convertToCategoryEntity(CategoryView viewModel){
        var entity = new Category(viewModel.getName());
        return entity;
    }
}
