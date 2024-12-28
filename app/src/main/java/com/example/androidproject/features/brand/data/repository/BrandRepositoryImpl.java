package com.example.androidproject.features.brand.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BrandRepositoryImpl implements BrandRepository{
    private final FirebaseFirestore db;
    private DocumentSnapshot lastDocument = null;

    public BrandRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    public CompletableFuture<Either<Failure, List<BrandModel>>> getBrandRepository(String page, String limit, String search) {
        CompletableFuture<Either<Failure, List<BrandModel>>> future = new CompletableFuture<>();
        List<BrandModel> brandList = new ArrayList<>();

        int pageInt = Integer.parseInt(page);
        int limitInt = Integer.parseInt(limit);

        boolean firstPage = pageInt == 1;

        Query query = db.collection("brands")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(limitInt);

        if (!firstPage && lastDocument != null) {
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
                            BrandModel brand = document.toObject(BrandModel.class);
                            brandList.add(brand);
                        }

                        List<BrandModel> filterCategoryList = brandList;

                        if (search != null && !search.isEmpty()) {
                            filterCategoryList = brandList.stream()
                                    .filter(category -> category.getName().toLowerCase().contains(search.toLowerCase()))
                                    .collect(Collectors.toList());
                        }

                        future.complete(Either.right(filterCategoryList));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure(e.getMessage())));
                });
        return future;
    }

    public void addBrandRepository(BrandModel brandModel, long quantity) {
        Map<String, Object> brandData = new HashMap<>();

        brandData.put("id", brandModel.prefixBrandID(quantity));
        brandData.put("name", brandModel.getName());
        brandData.put("imageUrl", brandModel.getImageUrl());
        brandData.put("description", brandModel.getDescription());
        brandData.put("createdAt", brandModel.getCreatedAt());
        brandData.put("updatedAt", brandModel.getUpdatedAt());
        brandData.put("hidden", brandModel.getHidden());

        db.collection("brands").document(brandModel.prefixBrandID(quantity)).set(brandData);
    }

    public void updateBrandRepository(BrandModel brandModel) {
        Map<String, Object> brandData = new HashMap<>();

        brandData.put("name", brandModel.getName());
        brandData.put("imageUrl", brandModel.getImageUrl());
        brandData.put("description", brandModel.getDescription());
        brandData.put("updatedAt", brandModel.getUpdatedAt());

        db.collection("brands").document(brandModel.getId()).update(brandData);
    }

    public void deleteBrandRepository(String id) {
        db.collection("brands").document(id).delete();
    }

    public CompletableFuture<Either<Failure, BrandModel>> getBrandById(String id) {
        CompletableFuture<Either<Failure, BrandModel>> future = new CompletableFuture<>();
        db.collection("brands").document(id).get()
                .addOnSuccessListener(r -> {
                    BrandModel brand = r.toObject(BrandModel.class);
                    future.complete(Either.right(brand));
                });
        return future;
    }

    public void updateBrandHidden(String id, boolean hidden) {
        Map<String, Object> brandData = new HashMap<>();
        brandData.put("hidden", hidden);

        db.collection("brands").document(id).update(brandData);
    }

    public CompletableFuture<Either<Failure, List<BrandModel>>> getBrandListForStoreScreen() {
        CompletableFuture<Either<Failure, List<BrandModel>>> future = new CompletableFuture<>();
        List<BrandModel> brandList = new ArrayList<>();

        db.collection("brands")
                .where(Filter.equalTo("hidden", false))
                .get()
                .addOnSuccessListener(q -> {
                    int count = 0;
                    for (DocumentSnapshot document : q) {
                        if (count >= 4) break;
                        BrandModel brand = document.toObject(BrandModel.class);
                        brandList.add(brand);
                        count++;
                    }
                    future.complete(Either.right(brandList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<BrandModel>>> getBrandList() {
        CompletableFuture<Either<Failure, List<BrandModel>>> future = new CompletableFuture<>();
        List<BrandModel> brandList = new ArrayList<>();

        db.collection("brands")
                .get()
                .addOnSuccessListener(q -> {
                    for (DocumentSnapshot document : q) {
                        BrandModel brand = document.toObject(BrandModel.class);
                        brandList.add(brand);
                    }
                    future.complete(Either.right(brandList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }
}
