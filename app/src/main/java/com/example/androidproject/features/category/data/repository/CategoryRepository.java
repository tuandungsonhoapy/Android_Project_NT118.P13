package com.example.androidproject.features.category.data.repository;

import android.util.Log;

import com.example.androidproject.features.category.data.model.CategoryModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepository {
    public void addCategoryRepository(CategoryModel category, long quantity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> categoryData = new HashMap<>();

        categoryData.put("id", category.prefixCategoryID(quantity));
        categoryData.put("name", category.getCategoryName());
        categoryData.put("imageUrl", category.getCategoryImage());
        categoryData.put("description", category.getDescription());
        categoryData.put("createdAt", category.getCreatedAt());
        categoryData.put("updatedAt", category.getUpdatedAt());

        db.collection("categories").document(category.prefixCategoryID(quantity)).set(categoryData)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Thêm category thành công: " + category.getCategoryName()))
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi thêm category: ", e));
    }

    public List<CategoryModel> getCategoryRepository(String page, String limit) {
        List<CategoryModel> categoryList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                        CategoryModel category = queryDocumentSnapshots.getDocuments().get(i).toObject(CategoryModel.class);
                        categoryList.add(category);
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Lỗi khi lấy category: ", e));
        Log.d("Firestore", "Lấy category thành công");
        return categoryList;
    }
}
