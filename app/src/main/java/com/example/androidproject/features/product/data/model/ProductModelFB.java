package com.example.androidproject.features.product.data.model;

import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.entity.ProductOptions;
import com.google.firebase.Timestamp;

import java.util.List;
import java.util.stream.Collectors;

public class ProductModelFB extends ProductEntity {
    public ProductModelFB() {
    }

    public ProductModelFB(String name, List<String> images, double price, int stockQuantity, String brandId, String categoryId, ProductOptions availableOptions, ProductOptions variants, String description) {
        super(name, images, price, stockQuantity, brandId, categoryId, availableOptions, variants, description);
    }

    public ProductModelFB(String name, List<String> images, double price, int stockQuantity, String brandId, String categoryId, List<ProductOption> options, String description) {
        super(name, images, price, stockQuantity, brandId, categoryId, options, description);
    }

    @Override
    public BrandModel getBrand() {
        return super.getBrand();
    }

    @Override
    public void setBrand(BrandModel brand) {
        super.setBrand(brand);
    }

    @Override
    public double getRating() {
        return super.getRating();
    }

    @Override
    public void setRating(double rating) {
        super.setRating(rating);
    }

    @Override
    public String getCategoryId() {
        return super.getCategoryId();
    }

    @Override
    public void setCategoryId(String categoryId) {
        super.setCategoryId(categoryId);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public List<String> getImages() {
        return super.getImages();
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public boolean isHidden() {
        return super.isHidden();
    }

    @Override
    public String getBrandId() {
        return super.getBrandId();
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
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public int getStockQuantity() {
        return super.getStockQuantity();
    }

    @Override
    public ProductOptions getAvailableOptions() {
        return super.getAvailableOptions();
    }

    @Override
    public void setAvailableOptions(ProductOptions availableOptions) {
        super.setAvailableOptions(availableOptions);
    }

    @Override
    public ProductOptions getVariants() {
        return super.getVariants();
    }

    @Override
    public void setVariants(ProductOptions variants) {
        super.setVariants(variants);
    }

    @Override
    public void setImages(List<String> images) {
        super.setImages(images);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void addImageUrl(String imageUrl) {
        super.addImageUrl(imageUrl);
    }

    @Override
    public void setPrice(double price) {
        super.setPrice(price);
    }

    @Override
    public void setStockQuantity(int stockQuantity) {
        super.setStockQuantity(stockQuantity);
    }

    @Override
    public void setBrandId(String brandId) {
        super.setBrandId(brandId);
    }

    @Override
    public void setHidden(boolean hidden) {
        super.setHidden(hidden);
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
    public List<ProductOption> getOptions() {
        return super.getOptions();
    }

    @Override
    public void setOptions(List<ProductOption> options) {
        super.setOptions(options);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    public List<ProductEntity> toProductEntityList(List<ProductModelFB> items) {
        return items.stream()
                .map(item -> {
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.setId(item.getId());
                    productEntity.setName(item.getName());
                    productEntity.setImages(item.getImages());
                    productEntity.setPrice(item.getPrice());
                    productEntity.setStockQuantity(item.getStockQuantity());
                    productEntity.setBrandId(item.getBrandId());
                    productEntity.setHidden(item.isHidden());
                    productEntity.setCreatedAt(item.getCreatedAt());
                    productEntity.setUpdatedAt(item.getUpdatedAt());
                    productEntity.setOptions(item.getOptions());
                    productEntity.setBrand(item.getBrand());
                    return productEntity;
                })
                .collect(Collectors.toList());
    }

    public static ProductEntity toProductEntity(ProductModelFB item) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(item.getId());
        productEntity.setName(item.getName());
        productEntity.setImages(item.getImages());
        productEntity.setPrice(item.getPrice());
        productEntity.setStockQuantity(item.getStockQuantity());
        productEntity.setBrandId(item.getBrandId());
        productEntity.setHidden(item.isHidden());
        productEntity.setCreatedAt(item.getCreatedAt());
        productEntity.setUpdatedAt(item.getUpdatedAt());
        productEntity.setOptions(item.getOptions());
        productEntity.setBrand(item.getBrand());
        return productEntity;
    }

    public String prefixProductID(long quantity) {
        return "product" + String.format("%05d", quantity);
    }
}
