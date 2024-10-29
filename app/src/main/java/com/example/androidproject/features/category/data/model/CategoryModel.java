package com.example.androidproject.features.category.data.model;

import com.google.firebase.Timestamp;

public class CategoryModel {
    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private com.google.firebase.Timestamp createdAt;
    private com.google.firebase.Timestamp updatedAt;

    public CategoryModel(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
    }

    public String getCategoryName() {
        return name;
    }

    public String getCategoryImage() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String prefixCategoryID(long quantity) {
        return "category" + String.format("%05d", quantity);
    }
}
