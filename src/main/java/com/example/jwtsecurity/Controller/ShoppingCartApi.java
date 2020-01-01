package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.ProductMapper;
import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Model.Item;
import com.example.jwtsecurity.Model.ShoppingCart;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Repository.ShoppingCartRepository;
import com.example.jwtsecurity.Views.ProductToItemView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shopping-cart")
@CrossOrigin
public class ShoppingCartApi {
    private ShoppingCartRepository cartRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;

    public ShoppingCartApi(ShoppingCartRepository cartRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.cartRepository = cartRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart>  getCart(@PathVariable Long id) {
       var cart  =  this.cartRepository.findById(id);
       return cart.isPresent() ? ResponseEntity.ok(cart.get()) : ResponseEntity.notFound().build();

    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCart>> all() {
        return ResponseEntity.ok(this.cartRepository.findAll());
    }

    @GetMapping()
    public ResponseEntity<ShoppingCart >createCartNoId(){
        var cartToCreate = new ShoppingCart();
        cartToCreate.setCreatedOn();
        this.cartRepository.save(cartToCreate);
        return ResponseEntity.ok(cartToCreate);
    }

    @PostMapping()
    public ResponseEntity<ShoppingCart> createCart(@RequestBody ProductToItemView product) {
        Category category = this.categoryRepository.findByName(product.getCategory().getName());
        var itemToSave = new Item();
        itemToSave.setTitle(product.getTitle());
        itemToSave.setPrice(product.getPrice());
        itemToSave.setCategory(category);
        itemToSave.setImageUrl(product.getImageUrl());
        itemToSave.addQuantity();

        var cartToCreate = new ShoppingCart();
        cartToCreate.setCreatedOn();
        cartToCreate.addItem(itemToSave);

        this.cartRepository.save(cartToCreate);
        return ResponseEntity.ok(cartToCreate);

    }

    @PostMapping("/{id}")
    public ResponseEntity<ShoppingCart> addtoCart(@PathVariable Long id, @RequestBody ProductToItemView product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Optional<ShoppingCart> cart = this.cartRepository.findById(id);

        cart.ifPresent(c -> {
            c.getItems().stream()
                    .filter(p -> product.getTitle().equals(p.getTitle()))
                    .findFirst().ifPresentOrElse(Item::addQuantity,
             () -> {
                var productToAdd = this.productMapper.convertToItemEntityYaPili(product);
                c.addItem(productToAdd);
            });
            this.cartRepository.save(c);

        });



        return cart.isPresent() ? ResponseEntity.ok(cart.get()) : this.createCart(product);


    }

    @PostMapping("/reduce/{id}")
    public ResponseEntity<ShoppingCart > reduceQuantity(@PathVariable Long id, @RequestBody ProductToItemView product, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Optional<ShoppingCart> cartOptional = this.cartRepository.findById(id);
        cartOptional.ifPresent(cart -> {

            cart.getItems().stream()
                    .filter(p -> product.getTitle().equals(p.getTitle()))
                    .findFirst().ifPresent(p -> {
                        var amount = p.getQuantity();
                        if (amount > 1) {
                            p.reduceQuantity();
                        } else {
                            cart.removeItem(p);
                        }
                    });

            this.cartRepository.save(cart);

        });

        return ResponseEntity.of(cartOptional);


    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        var cart = this.cartRepository.findById(id);
        cart.ifPresent(c -> this.cartRepository.deleteById(id));

    }

    @PatchMapping ("/clear/{id}")
    public ResponseEntity<?> clearItems(@PathVariable Long id){
        var cart = this.cartRepository.findById(id);

        cart.ifPresent(c -> {
            c.clearItems();
            this.cartRepository.save(c);
        });

        return ResponseEntity.of(cart);

    }
}
