package com.example.androidproject.features.cart.data.entity;

import com.example.androidproject.features.product.data.entity.ProductOption;

public class ProductsOnCart {
    private String ProductId;
    private String ProductName;
    private String ProductImage;
    private double ProductPrice;
    private ProductOption ProductOptions;
    private int quantity;

    public ProductsOnCart() {
    }

    public ProductsOnCart(String ProductId, String ProductName, String ProductImage, double ProductPrice, ProductOption ProductOptions, int quantity) {
        this.ProductId = ProductId;
        this.ProductName = ProductName;
        this.ProductImage = ProductImage;
        this.ProductPrice = ProductPrice;
        this.ProductOptions = ProductOptions;
        this.quantity = quantity;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String ProductId) {
        this.ProductId = ProductId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String ProductImage) {
        this.ProductImage = ProductImage;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double ProductPrice) {
        this.ProductPrice = ProductPrice;
    }

    public ProductOption getProductOptions() {
        return ProductOptions;
    }

    public void setProductOptions(ProductOption ProductOptions) {
        this.ProductOptions = ProductOptions;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return getProductOptions() != null ? getProductName() + " - " + getProductOptions().getChip() : getProductName();
    }
}
