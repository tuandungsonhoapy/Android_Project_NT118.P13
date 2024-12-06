package com.example.androidproject.features.category.usecase;

import android.util.Log;

import com.example.androidproject.R;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.core.utils.counter.CounterModel;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.data.repository.CategoryRepository;
import com.example.androidproject.features.category.data.repository.CategoryRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CategoryUseCase {
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl(FirebaseFirestore.getInstance());

    public List<BrandModel> getBrandListByCategory(String category) {
//        List<BrandModel> brands = new ArrayList<>();
//        if (category.equals("Laptop")) {
//            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
//            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
//            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
//            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
//        } else if (category.equals("Phone")) {
//            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
//            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
//            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
//            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
//        } else if (category.equals("Controller")) {
//            brands.add(new BrandModel(1, "Acer", R.drawable.image_acer_logo, 50));
//            brands.add(new BrandModel(2, "Asus", R.drawable.image_asus_logo, 60));
//            brands.add(new BrandModel(3, "Dell", R.drawable.image_dell_logo, 55));
//            brands.add(new BrandModel(4, "HP", R.drawable.image_hp_logo, 45));
//        }
//        return brands;
        return null;
    }

    public CompletableFuture<Either<Failure,List<CategoryEntity>>> getCategoryList() {
        return categoryRepository.getCategoryListForHomeScreen().thenApply(r -> {
            if (r.isRight()) {
                List<CategoryModel> categoryModels = r.getRight();
                List<CategoryEntity> categoryEntities = new CategoryModel().toCategoryEntityList(categoryModels);
                return Either.right(categoryEntities);
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public void addCategory(CategoryModel category, long quantity) {
        CategoryModel categoryModel = new CategoryModel(category.getCategoryName(), category.getImageUrl(), category.getDescription());
        categoryRepository.addCategoryRepository(categoryModel, quantity);
    }

    public CompletableFuture<Either<Failure,List<CategoryEntity>>> getCategory(String page, String limit, String search) {
        return categoryRepository.getCategoryRepository(page, limit, search).thenApply(r -> {
           if (r.isRight()) {
                List<CategoryModel> categoryModels = r.getRight();
                List<CategoryEntity> categoryEntities = new CategoryModel().toCategoryEntityList(categoryModels);
                return Either.right(categoryEntities);
           } else {
               return Either.left(r.getLeft());
           }
        });
    }

    public void updateCategoryProductCount(String categoryId, int index) {
        categoryRepository.updateCategoryProductCount(categoryId, index);
    }

    public CompletableFuture<Either<Failure, CategoryModel>> getCategoryByID(String id) {
        return categoryRepository.getCategoryByID(id);
    }

    public void updateCategoryHidden(String id, boolean hidden) {
        categoryRepository.updateCategoryHidden(id, hidden);
    }

    public void updateCategory(CategoryModel category) {
        categoryRepository.updateCategoryRepository(category);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteCategoryRepository(id);
    }
}
