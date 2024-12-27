package com.example.androidproject.features.checkout.data.entity;

import com.example.androidproject.features.address.data.entity.AddressEntity;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.google.firebase.Timestamp;

import java.util.List;

public class CheckoutEntity {
    private String id;
    private String userId;
    private String addressId;
    private String note;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<ProductsOnCart> products;
    private String fullAddress;
    private double totalPrice;

    public CheckoutEntity() {
    }

    public CheckoutEntity(String userId, String addressId,List<ProductsOnCart> products, String fullAddress, double totalPrice) {
        this.userId = userId;
        this.addressId = addressId;
        this.note = null;
        this.status = "PENDING";
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
        this.products = products;
        this.fullAddress = fullAddress;
        this.totalPrice = totalPrice;
    }

    public CheckoutEntity(String userId, String addressId, String note,List<ProductsOnCart> products, String fullAddress, double totalPrice) {
        this.userId = userId;
        this.addressId = addressId;
        this.note = note;
        this.status = "PENDING";
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
        this.products = products;
        this.fullAddress = fullAddress;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public List<ProductsOnCart> getProducts() {
        return products;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setProducts(List<ProductsOnCart> products) {
        this.products = products;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
