package com.example.jwtsecurity.Controller;

import com.example.jwtsecurity.Mappers.ProductMapper;
import com.example.jwtsecurity.Model.Order;
import com.example.jwtsecurity.Repository.OrderRepository;
import com.example.jwtsecurity.Views.OrderView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderApi {
    private OrderRepository orderRepository;

   private ProductMapper mapper;

    public OrderApi(OrderRepository orderRepository, ProductMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping()
    public Order createOrder(@RequestBody OrderView view, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

            var orderedItemList = view.getOrderItems().stream()
                    .map(c -> this.mapper.convertToOrderItemEntity(c))
                    .collect(Collectors.toList());


            var orderToCreate = new Order(view.getName(), view.getAddressLine1(),
                    view.getAddressLine2(), view.getCity(), view.getTotalPrice());
            orderedItemList.forEach(e -> orderToCreate.addOrderItem(e));
            orderToCreate.setDatePlaced();

            this.orderRepository.save(orderToCreate);

            return orderToCreate;

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        var order = this.orderRepository.findById(id).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Order> allOrders(){
        return this.orderRepository.findAll();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("byName/{name}")
    public List<Order> ordersByName(@PathVariable String name){
        return this.orderRepository.findByName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        this.orderRepository.deleteById(id);
    }
}
