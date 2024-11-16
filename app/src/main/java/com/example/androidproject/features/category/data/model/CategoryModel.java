package com.example.androidproject.features.category.data.model;


import com.example.androidproject.features.category.data.entity.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryModel extends CategoryEntity {
    public CategoryModel() {
    }

    public CategoryModel(String name, String imageUrl, String description) {
        super(name, imageUrl, description);
    }

    public String getName() {
        return super.getCategoryName();
    }

    public String getImageUrl() {
        return super.getImageUrl();
    }

    public String getDescription() {
        return super.getDescription();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setImageUrl(String imageUrl) {
        super.setImageUrl(imageUrl);
    }

    public void setDescription(String description) {
        super.setDescription(description);
    }

    public List<CategoryEntity> toCategoryEntityList(List<CategoryModel> items) {
        return items.stream()
                .map(item -> new CategoryEntity(item.getCategoryName(), item.getImageUrl(), item.getDescription()))
                .collect(Collectors.toList());
    }

    public String prefixCategoryID(long quantity) {
        return "category" + String.format("%05d", quantity);
    }
}
