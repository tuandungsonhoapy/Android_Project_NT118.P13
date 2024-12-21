package com.example.androidproject.features.product.data.entity;

import com.google.firebase.Timestamp;

import java.util.List;

public class ProductEntity {
    private String id;
    private String name;
    private List<String> images;
    private double price;
    private int stockQuantity;
    private String brandId;
    private String categoryId;
    private boolean hidden;
    private String description;
    private double rating;
    ProductOptions availableOptions;
    ProductOptions variants;
    List<ProductOption> options;
    private com.google.firebase.Timestamp createdAt;
    private com.google.firebase.Timestamp updatedAt;

    public ProductEntity() {
    }

    public ProductEntity(String name, List<String> images, double price, int stockQuantity, String brandId, String categoryId, ProductOptions availableOptions, ProductOptions variants, String description) {
        this.name = name;
        this.images = images;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.hidden = false;
        this.availableOptions = availableOptions;
        this.variants = variants;
        this.description = description;
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
        this.rating = 0;
    }

    public ProductEntity(String name, List<String> images, double price, int stockQuantity, String brandId, String categoryId, List<ProductOption> options, String description) {
        this.name = name;
        this.images = images;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.hidden = false;
        this.options = options;
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
        this.rating = 0;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public ProductOptions getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(ProductOptions availableOptions) {
        this.availableOptions = availableOptions;
    }

    public ProductOptions getVariants() {
        return variants;
    }

    public void setVariants(ProductOptions variants) {
        this.variants = variants;
    }

    public String getName() {
        return name;
    }

    public List<String> getImages() {
        return images;
    }

    public double getPrice() {
        return price;
    }

    public String getBrandId() {
        return brandId;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addImageUrl(String imageUrl) {
        this.images.add(imageUrl);
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ProductOption> getOptions() {
        return options;
    }

    public void setOptions(List<ProductOption> options) {
        this.options = options;
    }
}
