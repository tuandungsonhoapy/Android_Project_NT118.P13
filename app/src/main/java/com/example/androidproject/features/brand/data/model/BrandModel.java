package com.example.androidproject.features.brand.data.model;

public class BrandModel {
    private int id;
    private String name;
    private int imageResource;
    private int quantity;

    public BrandModel(int id, String name, int imageResource, int quantity) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
