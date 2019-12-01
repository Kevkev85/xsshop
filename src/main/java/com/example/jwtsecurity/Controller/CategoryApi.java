package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.CategoryMapper;
import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Views.CategoryView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryApi {
    private CategoryRepository categoryRepository;
    private CategoryMapper mapper;

    public CategoryApi(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    @GetMapping("/all")
    public List<Category>all(){
        // var allCategories = this.categoryRepository.findAll()
        var allCategories = this.categoryRepository.findAllByOrderByName();
        return allCategories;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Category save(@RequestBody CategoryView categoryView, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        var categoryEntity = this.mapper.convertToCategoryEntity(categoryView);
        this.categoryRepository.save(categoryEntity);

        return categoryEntity;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name){
        var category = this.categoryRepository.findByName(name);
        this.categoryRepository.delete(category);
    }
}
