package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.ProductMapper;
import com.example.jwtsecurity.Model.Category;
import com.example.jwtsecurity.Model.Item;
import com.example.jwtsecurity.Model.ShoppingCart;
import com.example.jwtsecurity.Repository.CategoryRepository;
import com.example.jwtsecurity.Repository.ItemRepository;
import com.example.jwtsecurity.Repository.ShoppingCartRepository;
import com.example.jwtsecurity.Views.ProductToItemView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shopping-cart")
@CrossOrigin
public class ShoppingCartApi {
    private ShoppingCartRepository cartRepository;
    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;
    private ProductMapper productMapper;

    public ShoppingCartApi(ShoppingCartRepository cartRepository, CategoryRepository categoryRepository, ItemRepository itemRepository, ProductMapper productMapper) {
        this.cartRepository = cartRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ShoppingCart  getCart(@PathVariable Long id) {
       var cart  =  this.cartRepository.findById(id).orElse(null);
       if (cart == null) {
           throw new EntityNotFoundException();
       }

       return cart;

    }

    @GetMapping("/all")
    public List<ShoppingCart> all() {
        return this.cartRepository.findAll();
    }

    @PostMapping()
    public ShoppingCart createCart(@RequestBody ProductToItemView product) {
        Category category = this.categoryRepository.findByName(product.getCategory().getName());
        var itemToSave = new Item();
        //itemToSave.setId(product.getId());
        itemToSave.setTitle(product.getTitle());
        itemToSave.setPrice(product.getPrice());
        itemToSave.setCategory(category);
        itemToSave.setImageUrl(product.getImageUrl());
        itemToSave.addQuantity();

        var cartToCreate = new ShoppingCart();
        cartToCreate.setCreatedOn();
        cartToCreate.addItem(itemToSave);

        this.cartRepository.save(cartToCreate);
        return cartToCreate;

    }

    @PostMapping("/{id}")
    public ShoppingCart addtoCart(@PathVariable Long id, @RequestBody ProductToItemView product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        var cart = this.cartRepository.findById(id).orElse(null);
        if (cart != null) {

            var listOfItems = cart.getItems();
            Optional<Item> alreadyInList = listOfItems.stream()
                    .filter(p -> product.getTitle().equals(p.getTitle()))
                    .findAny();
            if (alreadyInList.isPresent()) {
                alreadyInList.get().addQuantity();
            } else {
                var productToAdd = this.productMapper.convertToItemEntityYaPili(product);
                cart.addItem(productToAdd);
            }
            this.cartRepository.save(cart);
            return cart;


        } else {
            return this.createCart(product);
        }

    }

    @PostMapping("/reduce/{id}")
    public ShoppingCart reduceQuantity(@PathVariable Long id, @RequestBody ProductToItemView product, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        var cart = this.cartRepository.findById(id).orElse(null);
        var listOfitems= cart.getItems();

        var productToReduce = listOfitems.stream()
                .filter(p -> product.getTitle().equals(p.getTitle()))
                .findAny().get();
       var amount = productToReduce.getQuantity();
       if (amount > 1) {
           productToReduce.reduceQuantity();
       } else {
           cart.removeItem(productToReduce);
       }
       this.cartRepository.save(cart);
       return  cart;

    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        this.cartRepository.deleteById(id);
    }

    @PatchMapping ("/clear/{id}")
    public ResponseEntity<?> clearItems(@PathVariable Long id){
        var cart = this.cartRepository.findById(id).orElse(null);

        if (cart != null ){
            cart.clearItems();
            this.cartRepository.save(cart);
            return ResponseEntity.ok().body(cart);
        } else {
            return ResponseEntity.notFound().build();
        }


    }
}
