package com.example.androidproject.features.cart.data.model;

import com.example.androidproject.features.cart.data.entity.CartEntity;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;

import java.util.List;
import java.util.stream.Collectors;

public class CartModel extends CartEntity {
    public CartModel() {
    }

    public CartModel(String id, List<ProductsOnCart> products, double total, String userId) {
        super(id, products, total, userId);
    }

    public String getId() {
        return super.getId();
    }

    public void setId(String id) {
        super.setId(id);
    }

    public List<ProductsOnCart> getProducts() {
        return super.getProducts();
    }

    public void setProducts(List<ProductsOnCart> products) {
        super.setProducts(products);
    }

    public double getTotal() {
        return super.getTotal();
    }

    public void setTotal(double total) {
        super.setTotal(total);
    }

    public String getUserId() {
        return super.getUserId();
    }

    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    public double calculateTotal() {
        double total = 0;
        for (ProductsOnCart product : super.getProducts()) {
            total += product.getProductPrice() * product.getQuantity();
        }
        return total;
    }

    public String prefixCartId(long quantity) {
        return "cart" + String.format("%05d", quantity);
    }

    public List<CartEntity> toCartEntityList(List<CartModel> cartModels) {
        return cartModels.stream()
                .map(item -> {
                    CartEntity cartEntity = new CartEntity();
                    cartEntity.setId(item.getId());
                    cartEntity.setProducts(item.getProducts());
                    cartEntity.setTotal(item.getTotal());
                    cartEntity.setUserId(item.getUserId());
                    return cartEntity;
                }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "id='" + super.getId() + '\'' +
                ", products=" + super.getProducts() +
                ", total=" + super.getTotal() +
                ", userId='" + super.getUserId() + '\'' +
                '}';
    }
}
