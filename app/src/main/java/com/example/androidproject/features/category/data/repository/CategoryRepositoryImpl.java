package com.example.androidproject.features.category.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CategoryRepositoryImpl implements CategoryRepository{
    private final FirebaseFirestore db;
    private DocumentSnapshot lastDocument = null;
    public CategoryRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void addCategoryRepository(CategoryModel category, long quantity) {
        Map<String, Object> categoryData = new HashMap<>();

        categoryData.put("id", category.prefixCategoryID(quantity));
        categoryData.put("name", category.getCategoryName());
        categoryData.put("imageUrl", category.getImageUrl());
        categoryData.put("description", category.getDescription());
        categoryData.put("createdAt", category.getCreatedAt());
        categoryData.put("updatedAt", category.getUpdatedAt());
        categoryData.put("productCount", 0);
        categoryData.put("hidden", category.isHidden());

        db.collection("categories").document(category.prefixCategoryID(quantity)).set(categoryData);
    }

    @Override
    public CompletableFuture<Either<Failure, List<CategoryModel>>> getCategoryRepository(String page, String limit, String search) {
        CompletableFuture<Either<Failure, List<CategoryModel>>> future = new CompletableFuture<>();
        List<CategoryModel> categoryList = new ArrayList<>();

        int pageInt = Integer.parseInt(page);
        int limitInt = Integer.parseInt(limit);
        boolean trang_dau = pageInt == 1;

        Query query = db.collection("categories")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(limitInt);

        if (!trang_dau && lastDocument != null) {
            query = query.startAfter(lastDocument);
        }

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()) {
                        future.complete(Either.right(Collections.emptyList()));
                    } else {
                        lastDocument = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size() - 1);

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            CategoryModel category = document.toObject(CategoryModel.class);
                            categoryList.add(category);
                        }

                        List<CategoryModel> filterCategoryList = categoryList;

                        if (search != null && !search.isEmpty()) {
                            filterCategoryList = categoryList.stream()
                                    .filter(category -> category.getCategoryName().toLowerCase().contains(search.toLowerCase()))
                                    .collect(Collectors.toList());
                        }

                        future.complete(Either.right(filterCategoryList));
                    }
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public void updateCategoryRepository(CategoryModel category) {
        Map<String, Object> categoryData = new HashMap<>();

        categoryData.put("name", category.getCategoryName());
        categoryData.put("imageUrl", category.getImageUrl());
        categoryData.put("description", category.getDescription());
        categoryData.put("updatedAt", category.getUpdatedAt());

        db.collection("categories").document(category.getId()).update(categoryData);
    }

    @Override
    public void deleteCategoryRepository(String id) {
        db.collection("categories").document(id).delete();
    }

    @Override
    public void updateCategoryProductCount(String categoryId, int index) {
        Map<String, Object> categoryData = new HashMap<>();
        categoryData.put("productCount", FieldValue.increment(index));

        db.collection("categories").document(categoryId).update(categoryData);
    }

    @Override
    public CompletableFuture<Either<Failure,CategoryModel>> getCategoryByID(String id) {
        CompletableFuture<Either<Failure, CategoryModel>> future = new CompletableFuture<>();
        db.collection("categories").document(id).get()
                .addOnSuccessListener(r -> {
                    CategoryModel category = r.toObject(CategoryModel.class);
                    future.complete(Either.right(category));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public void updateCategoryHidden(String id, boolean hidden) {
        Map<String, Object> categoryData = new HashMap<>();
        categoryData.put("hidden", hidden);

        db.collection("categories").document(id).update(categoryData);
    }

    @Override
    public CompletableFuture<Either<Failure, List<CategoryModel>>> getCategoryListForHomeScreen() {
        CompletableFuture<Either<Failure, List<CategoryModel>>> future = new CompletableFuture<>();
        List<CategoryModel> categoryList = new ArrayList<>();

        db.collection("categories")
                .where(Filter.equalTo("hidden", false))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        CategoryModel category = document.toObject(CategoryModel.class);
                        categoryList.add(category);
                    }
                    future.complete(Either.right(categoryList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, CategoryModel>> getCategoryByName(String name) {
        CompletableFuture<Either<Failure, CategoryModel>> future = new CompletableFuture<>();
        db.collection("categories")
                .where(Filter.equalTo("name", name))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty()) {
                        future.complete(Either.right(null));
                    } else {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        CategoryModel category = document.toObject(CategoryModel.class);
                        future.complete(Either.right(category));
                    }
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<CategoryModel>>> getCategoryListForAllProduct() {
        CompletableFuture<Either<Failure, List<CategoryModel>>> future = new CompletableFuture<>();
        List<CategoryModel> categoryList = new ArrayList<>();

        db.collection("categories")
                .where(Filter.equalTo("hidden", false))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        CategoryModel category = document.toObject(CategoryModel.class);
                        categoryList.add(category);
                    }
                    future.complete(Either.right(categoryList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }
}
