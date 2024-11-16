package com.example.androidproject.features.category.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CategoryRepository {
    void addCategoryRepository(CategoryModel category, long quantity);

    CompletableFuture<Either<Failure, List<CategoryModel>>> getCategoryRepository(String page, String limit);

    void updateCategoryRepository(CategoryModel category);

    void deleteCategoryRepository(String id);

    void updateCategoryProductCount(String categoryId, int index);

    CompletableFuture<Either<Failure, CategoryModel>> getCategoryByID(String id);
}
