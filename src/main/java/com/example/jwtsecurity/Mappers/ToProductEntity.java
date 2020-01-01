package com.example.jwtsecurity.Mappers;

import com.example.jwtsecurity.Model.Product;
import com.example.jwtsecurity.Views.ProductView;

public interface ToProductEntity {
    Product convert(ProductView view);
}
