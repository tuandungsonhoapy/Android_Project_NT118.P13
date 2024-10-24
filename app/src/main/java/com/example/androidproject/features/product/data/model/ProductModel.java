package com.example.androidproject.features.product.data.model;

import com.example.androidproject.features.brand.data.model.BrandModel;

public class ProductModel {
    private String name;
    private int image;
    private double price;
    private int quantity;
    private BrandModel brand;
    private boolean favorite;

    public ProductModel(String name, int image, double price, int quantity, BrandModel brand, boolean favorite) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BrandModel getBrand() {
        return brand;
    }

    public void setBrand(BrandModel brand) {
        this.brand = brand;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
