package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.ProductMapper;
import com.example.jwtsecurity.Model.Product;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Repository.ProductRepository;
import com.example.jwtsecurity.Views.ProductView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductApi {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper mapper;

    public ProductApi(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }



    @GetMapping("/all")
    public List<Product> all (){
        var products = this.productRepository.findAllByOrderByTitle();

        return products;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byId/{id}")
    public ProductView byId(@PathVariable Long id){
        var product = this.productRepository.findById(id).orElse(null);
        if(product == null){
            throw new EntityNotFoundException();
        }
        var productView = this.mapper.convertToProductView(product);

        return productView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product save(@RequestBody ProductView productView, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        var productEntity = this.mapper.convertToEntity(productView);
        this.productRepository.save(productEntity);
        return productEntity;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Product update(@RequestBody ProductView productView, @PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        var productEntity = this.mapper.convertUpdatedEntity(id,productView);
        this.productRepository.save(productEntity);
        return productEntity;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.productRepository.deleteById(id);
    }
}
