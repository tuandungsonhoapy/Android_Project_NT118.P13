package com.example.androidproject.features.brand.data.entity;

import com.google.firebase.Timestamp;

public class BrandEntity {
    private String id;
    private String name;
    private String imageUrl;
    private String description;
    private Boolean hidden;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public BrandEntity() {
    }

    public BrandEntity(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.hidden = false;
        this.createdAt = Timestamp.now();
        this.updatedAt = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
