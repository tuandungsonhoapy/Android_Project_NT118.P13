package com.example.androidproject.features.brand.data.model;

public class BrandModel {
    private int id;
    private String name;
    private int image;

    public BrandModel(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return image;
    }
}
