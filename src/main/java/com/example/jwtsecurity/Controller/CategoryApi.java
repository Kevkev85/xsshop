package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.CategoryMapper;
import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Views.CategoryView;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Category>> all(){
        return ResponseEntity.ok(this.categoryRepository.findAllByOrderByName());

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/mpya")
    public ResponseEntity<Category> save(@RequestBody CategoryView categoryView, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Category categoryEntity = this.mapper.convertToCategoryEntity(categoryView);
        this.categoryRepository.save(categoryEntity);

        return ResponseEntity.ok(categoryEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name){
        Category category = this.categoryRepository.findByName(name);
        this.categoryRepository.delete(category);
    }
}
