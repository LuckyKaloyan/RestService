package com.service;


import com.model.Product;

import com.web.dto.NewProductRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private List<Product> products;

    public ProductService() {
        this.products = new ArrayList<>();
    }

    public List<Product> getAll() {
       return products;
    }

    public Product createNewProduct(NewProductRequest newProductRequest) {
   Product product =     Product.builder()
                .id(UUID.randomUUID())
                .name(newProductRequest.getName())
                .quantity(newProductRequest.getQuantity())
                .build();
        products.add(product);
        return product;
    }
}
