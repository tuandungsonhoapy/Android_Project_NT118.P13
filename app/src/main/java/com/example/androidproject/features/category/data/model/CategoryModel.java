package com.example.androidproject.features.category.data.model;

public class CategoryModel {
    private String id;
    private String name;
    private int image;
    private String description;

    public CategoryModel(String id, String name, int image, String description) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.description = description;
    }

    public String getCategoryName() {
        return name;
    }

    public int getCategoryImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
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
}
