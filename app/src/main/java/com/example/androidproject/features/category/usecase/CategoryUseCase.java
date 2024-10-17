package com.example.androidproject.features.category.usecase;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryUseCase {
    public List<BrandModel> getBrandListByCategory(String category) {
        List<BrandModel> brands = new ArrayList<>();
        if (category.equals("Máy tính")) {
            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
        } else if (category.equals("Điện thoại")) {
            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
        } else if (category.equals("Máy tính bảng")) {
            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
        }
        return brands;
    }
}
