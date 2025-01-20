package com.example.androidproject.features.checkout.data.model;

import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.checkout.data.entity.CheckoutEntity;
import com.google.firebase.Timestamp;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutModel extends CheckoutEntity {

    public CheckoutModel() {
    }

    public CheckoutModel(String userId, String addressId, List<ProductsOnCart> products, String fullAddress, double totalPrice, String voucherId, double oldTotalPrice) {
        super(userId, addressId, products, fullAddress, totalPrice, voucherId, oldTotalPrice);
    }

    public CheckoutModel(String userId, String addressId, String note, List<ProductsOnCart> products, String fullAddress, double totalPrice, String voucherId, double oldTotalPrice) {
        super(userId, addressId, note, products, fullAddress, totalPrice, voucherId, oldTotalPrice);
    }

    public String getId() {
        return super.getId();
    }

    public String getUserId() {
        return super.getUserId();
    }

    public String getAddressId() {
        return super.getAddressId();
    }

    public String getNote() {
        return super.getNote();
    }

    public String getStatus() {
        return super.getStatus();
    }

    public String getFullAddress() {
        return super.getFullAddress();
    }

    public double getTotalPrice() {
        return super.getTotalPrice();
    }

    public List<ProductsOnCart> getProducts() {
        return super.getProducts();
    }

    public void setId(String id) {
        super.setId(id);
    }

    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    public void setAddressId(String addressId) {
        super.setAddressId(addressId);
    }

    public void setNote(String note) {
        super.setNote(note);
    }

    public void setStatus(String status) {
        super.setStatus(status);
    }

    public void setFullAddress(String fullAddress) {
        super.setFullAddress(fullAddress);
    }

    public void setTotalPrice(double totalPrice) {
        super.setTotalPrice(totalPrice);
    }

    @Override
    public UserEntity getUser() {
        return super.getUser();
    }

    @Override
    public void setUser(UserEntity user) {
        super.setUser(user);
    }

    @Override
    public Timestamp getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public Timestamp getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    public String getVoucherId() {
        return super.getVoucherId();
    }

    @Override
    public double getOldTotalPrice() {
        return super.getOldTotalPrice();
    }

    @Override
    public void setCreatedAt(Timestamp createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    public void setUpdatedAt(Timestamp updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

    @Override
    public void setVoucherId(String voucherId) {
        super.setVoucherId(voucherId);
    }

    @Override
    public void setOldTotalPrice(double oldTotalPrice) {
        super.setOldTotalPrice(oldTotalPrice);
    }

    public void setProducts(List<ProductsOnCart> products) {
        super.setProducts(products);
    }

    public String prefixCheckoutId(long quantity) {
        return "checkout" + String.format("%05d", quantity);
    }

    public List<CheckoutEntity> toCheckoutEntityList(List<CheckoutModel> checkoutModels) {
        return checkoutModels.stream()
                .map(item -> {
                    CheckoutEntity checkoutEntity = new CheckoutEntity();
                    checkoutEntity.setId(item.getId());
                    checkoutEntity.setUserId(item.getUserId());
                    checkoutEntity.setAddressId(item.getAddressId());
                    checkoutEntity.setNote(item.getNote());
                    checkoutEntity.setStatus(item.getStatus());
                    checkoutEntity.setProducts(item.getProducts());
                    checkoutEntity.setFullAddress(item.getFullAddress());
                    checkoutEntity.setTotalPrice(item.getTotalPrice());
                    checkoutEntity.setVoucherId(item.getVoucherId());
                    checkoutEntity.setOldTotalPrice(item.getOldTotalPrice());
                    return checkoutEntity;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CheckoutModel{" +
                "id='" + super.getId() + '\'' +
                ", userId='" + super.getUserId() + '\'' +
                ", addressId='" + super.getAddressId() + '\'' +
                ", note='" + super.getNote() + '\'' +
                ", status='" + super.getStatus() + '\'' +
                ", products=" + super.getProducts() +
                ", fullAddress='" + super.getFullAddress() + '\'' +
                ", totalPrice=" + super.getTotalPrice() +
                ", voucherId='" + super.getVoucherId() + '\'' +
                ", oldTotalPrice=" + super.getOldTotalPrice() +
                '}';
    }
}
