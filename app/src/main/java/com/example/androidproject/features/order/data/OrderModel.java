package com.example.androidproject.features.order.data;

import com.example.androidproject.features.product.data.model.ProductModel;

public class OrderModel {
    private String id;
    private String user_id;
    private ProductModel[] products;
    private double total_price;
    private String status;
    private String payment_method;
    private String address_id;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public OrderModel(String id, String user_id, ProductModel[] products, double total_price, String status, String payment_method, String address_id, String created_at) {
        this.id = id;
        this.user_id = user_id;
        this.products = products;
        this.total_price = total_price;
        this.status = status;
        this.payment_method = payment_method;
        this.address_id = address_id;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ProductModel[] getProducts() {
        return products;
    }

    public void setProducts(ProductModel[] products) {
        this.products = products;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getProductsCount() {
        return products.length;
    }
}
