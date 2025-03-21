package com.onlinegrocery.myapp.utils;

import com.onlinegrocery.myapp.model.Product;

import java.util.List;

public class OrderDTO {

    private int userId;
    private List<Product> products;

    public OrderDTO() {
    }

    public OrderDTO(int userId, List<Product> products) {
        this.userId = userId;
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
