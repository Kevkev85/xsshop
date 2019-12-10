package com.example.jwtsecurity.Mappers;

import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Model.Item;
import com.example.jwtsecurity.Model.OrderItem;
import com.example.jwtsecurity.Model.Product;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Repository.ProductRepository;
import com.example.jwtsecurity.Views.ItemView;
import com.example.jwtsecurity.Views.OrderItemView;
import com.example.jwtsecurity.Views.ProductToItemView;
import com.example.jwtsecurity.Views.ProductView;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class ProductMapper {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public ProductMapper(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Product convertToEntity(ProductView view){
        var category = this.categoryRepository.findByName(view.getCategory());

        var entity = new Product(view.getTitle(), view.getPrice(), category, view.getImageUrl());
        return entity;
    }

    public Item convertToProperItemEntity(ItemView view) {
        var category = this.categoryRepository.findByName(view.getCategoryView().getName());
        var entity = new Item(view.getTitle(), view.getPrice(), category, view.getImageUrl());
        return entity;
    }

    public Item convertToItemEntity(ProductView view){
        var category = this.categoryRepository.findByName(view.getCategory());

        var entity = new Item(view.getTitle(), view.getPrice(), category, view.getImageUrl());
        return entity;
    }

    public Item convertToItemEntityYaPili(ProductToItemView view){
        var category = this.categoryRepository.findByName(view.getCategory().getName());

        var entity = new Item(view.getTitle(), view.getPrice(), category, view.getImageUrl());
        entity.setQuantity(1);
        return entity;
    }

    public OrderItem convertToOrderItemEntity(OrderItemView view){
        var entity = new OrderItem(view.getTitle(), view.getPrice(), view.getQuantity());
        return entity;
    }

    public Product convertUpdatedEntity( Long id, ProductView view) {
        Product updatedProduct = this.productRepository.findById(id).orElse(null);
        if(updatedProduct == null) throw new EntityNotFoundException();

        Category category = this.categoryRepository.findByName(view.getCategory());

        updatedProduct.setTitle(view.getTitle());
        updatedProduct.setPrice(view.getPrice());
        updatedProduct.setCategory(category);
        updatedProduct.setImageUrl(view.getImageUrl());

        return updatedProduct;

    }



    public ProductView convertToProductView(Product entity){
        var viewModel = new ProductView();

        viewModel.setId(entity.getId());
        viewModel.setTitle(entity.getTitle());
        viewModel.setPrice(entity.getPrice());
        viewModel.setCategory(entity.getCategory().getName());
        viewModel.setImageUrl(entity.getImageUrl());

        return viewModel;
    }
}
