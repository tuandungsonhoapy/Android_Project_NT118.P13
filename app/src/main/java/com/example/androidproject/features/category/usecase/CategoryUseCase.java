package com.example.androidproject.features.category.usecase;

import android.util.Log;

import com.example.androidproject.R;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.data.repository.CategoryRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryUseCase {
    private CategoryRepository categoryRepository = new CategoryRepository();

    public List<BrandModel> getBrandListByCategory(String category) {
        List<BrandModel> brands = new ArrayList<>();
        if (category.equals("Laptop")) {
            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
        } else if (category.equals("Phone")) {
            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
        } else if (category.equals("Controller")) {
            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
        }
        return brands;
    }

    public List<CategoryModel> getCategoryList() {
        List<CategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryModel("Laptop", "sdfgs", "Laptop description"));
        categoryList.add(new CategoryModel("Phone", "fsdf", "Phone description"));
        categoryList.add(new CategoryModel("Controller", "sdfsdf", "Controller description"));
        return categoryList;
    }

    public void addCategory(CategoryModel category, long quantity) {
        CategoryModel categoryModel = new CategoryModel(category.getCategoryName(), category.getCategoryImage(), category.getDescription());
        categoryRepository.addCategoryRepository(categoryModel, quantity);
    }

    public List<CategoryModel> getCategory(String page, String limit) {
        List<CategoryModel> category = categoryRepository.getCategoryRepository(page, limit);
        return category;
    }
}
