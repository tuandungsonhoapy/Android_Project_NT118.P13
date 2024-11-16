package com.example.androidproject.features.category.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CategoryRepositoryImpl implements CategoryRepository{
    private final FirebaseFirestore db;

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

        db.collection("categories").document(category.prefixCategoryID(quantity)).set(categoryData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Thêm category thành công: " + category.getCategoryName()))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi thêm category: ", e));
    }

    @Override
    public CompletableFuture<Either<Failure, List<CategoryModel>>> getCategoryRepository(String page, String limit) {
        CompletableFuture<Either<Failure, List<CategoryModel>>> future = new CompletableFuture<>();
        List<CategoryModel> categoryList = new ArrayList<>();
        db.collection("categories").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                        CategoryModel category = queryDocumentSnapshots.getDocuments().get(i).toObject(CategoryModel.class);
                        categoryList.add(category);
                    }
                    future.complete(Either.right(categoryList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        Log.d("Firestore", "Lấy category thành công");
        return future;
    }

    @Override
    public void updateCategoryRepository(CategoryModel category) {
        Map<String, Object> categoryData = new HashMap<>();

        categoryData.put("name", category.getCategoryName());
        categoryData.put("imageUrl", category.getImageUrl());
        categoryData.put("description", category.getDescription());
        categoryData.put("updatedAt", category.getUpdatedAt());

        db.collection("categories").document(category.getId()).update(categoryData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Cập nhật category thành công: " + category.getCategoryName()))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi cập nhật category: ", e));
    }

    @Override
    public void deleteCategoryRepository(String id) {
        db.collection("categories").document(id).delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Xóa category thành công"))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi xóa category: ", e));
    }

    @Override
    public void updateCategoryProductCount(String categoryId, int index) {
        Map<String, Object> categoryData = new HashMap<>();
        categoryData.put("productCount", FieldValue.increment(index));

        db.collection("categories").document(categoryId).update(categoryData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Cập nhật số lượng sản phẩm của category thành công"))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi cập nhật số lượng sản phẩm của category: ", e));
    }
}
