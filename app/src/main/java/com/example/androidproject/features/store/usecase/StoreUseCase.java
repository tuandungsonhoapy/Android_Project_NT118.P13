package com.example.androidproject.features.store.usecase;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.category.data.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class StoreUseCase {
    public List<CategoryModel> getCategoryList() {
        List<CategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryModel("Laptop", "sdfgs", "Laptop description"));
        categoryList.add(new CategoryModel("Phone", "fsdf", "Phone description"));
        categoryList.add(new CategoryModel("Controller", "sdfsdf", "Controller description"));

        return categoryList;
    }

    public List<BrandModel> getBrandList() {
        List<BrandModel> brandList = new ArrayList<>();
        brandList.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
        brandList.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
        brandList.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
        brandList.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));

        return brandList;
    }

    public int getColumns(int col) {
        return col;
    }
}
