package com.example.androidproject.features.brand.data.model;
import android.util.Log;

import com.example.androidproject.features.brand.data.entity.BrandEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BrandModel extends BrandEntity {
    public BrandModel() {
    }

    public BrandModel(String name, String imageUrl, String description) {
        super(name, imageUrl, description);
    }

    public String getId() {
        return super.getId();
    }

    public String getName() {
        return super.getName();
    }

    public String getImageUrl() {
        return super.getImageUrl();
    }

    public String getDescription() {
        return super.getDescription();
    }


    public void setId(String id) {
        super.setId(id);
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setImageUrl(String imageUrl) {
        super.setImage(imageUrl);
    }

    public void setDescription(String description) {
        super.setDescription(description);
    }

    public List<BrandEntity> toBrandEntityList(List<BrandModel> items) {
        return items.stream()
                .map(item -> {
                    BrandEntity brandEntity = new BrandEntity();
                    brandEntity.setId(item.getId());
                    brandEntity.setName(item.getName());
                    brandEntity.setImage(item.getImageUrl());
                    brandEntity.setDescription(item.getDescription());
                    return brandEntity;
                })
                .collect(Collectors.toList());
    }

    public String prefixBrandID(long quantity) {
        return "brand" + String.format("%05d", quantity);
    }

    @Override
    public String toString() {
        return getName();
    }
}
