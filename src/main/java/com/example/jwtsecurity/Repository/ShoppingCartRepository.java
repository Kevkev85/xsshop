package com.example.jwtsecurity.Repository;

import com.example.jwtsecurity.Model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository< ShoppingCart, Long> {
}
