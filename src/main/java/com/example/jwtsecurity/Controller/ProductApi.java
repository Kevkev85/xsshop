package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.ProductMapper;
import com.example.jwtsecurity.Model.Product;
import com.example.jwtsecurity.Repository.ProductRepository;
import com.example.jwtsecurity.Views.ProductView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductApi {
    private ProductRepository productRepository;
    private ProductMapper mapper;

    public ProductApi(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Product>> all (){
        List<Product> products = this.productRepository.findAllByOrderByTitle();

        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byId/{id}")
    public ResponseEntity<ProductView> byId(@PathVariable Long id){
        Product product = this.productRepository.findById(id).orElseThrow();

        ProductView productView = this.mapper.convertToProductView(product);

        return ResponseEntity.ok(productView);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product save(@RequestBody ProductView productView, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Product productEntity = this.mapper.convertToEntity(productView);
        this.productRepository.save(productEntity);
        return productEntity;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody ProductView productView, @PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Product productEntity = this.mapper.convertUpdatedEntity(id,productView);
        this.productRepository.save(productEntity);
        return ResponseEntity.ok(productEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.productRepository.deleteById(id);
    }
}
