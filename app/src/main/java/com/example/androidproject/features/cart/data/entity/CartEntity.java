package com.example.androidproject.features.cart.data.entity;

import java.util.List;

public class CartEntity {
    private String id;
    private List<ProductsOnCart> products;
    private double total;
    private String userId;

    public CartEntity() {
    }

    public CartEntity(String id, List<ProductsOnCart> products, double total, String userId) {
        this.id = id;
        this.products = products;
        this.total = total;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductsOnCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsOnCart> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
