package com.example.androidproject.features.cart.data.model;

import com.example.androidproject.features.order.data.ProductDataForOrderModel;

public class CartModel {
    private String id;
    private ProductDataForOrderModel[] products;
    private String userID;

    public String getId() {
        return id;
    }

    public ProductDataForOrderModel[] getProducts() {
        return products;
    }

    public String getUserID(){
        return userID;
    }

    public void setProducts(ProductDataForOrderModel[] products) {
        this.products = products;
    }
}
