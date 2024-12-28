package com.example.androidproject.features.product.data.repository;

import android.util.Log;
import android.widget.Toast;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ProductRepository implements IProductRepository{
    private final FirebaseFirestore db;
    private DocumentSnapshot lastDocument = null;

    public ProductRepository(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public void addProductRepository(ProductModelFB product, long quantity) {
        Map<String, Object> productData = new HashMap<>();

        productData.put("id", product.prefixProductID(quantity));
        productData.put("name", product.getName());
        productData.put("images", (product.getImages() != null) ? product.getImages() : new ArrayList<>());
        productData.put("price", product.getPrice());
        productData.put("stockQuantity", 0);
        productData.put("brandId", product.getBrandId());
        productData.put("categoryId", product.getCategoryId());
        productData.put("rating", product.getRating());
        productData.put("hidden", false);
        productData.put("description", product.getDescription());
        productData.put("options", new ArrayList<>());
        productData.put("createdAt", product.getCreatedAt());
        productData.put("updatedAt", product.getUpdatedAt());

        db.collection("products").document(product.prefixProductID(quantity)).set(productData);
    }

    @Override
    public CompletableFuture<Either<Failure, List<ProductModelFB>>> getProductRepository(String page, String limit, String search) {
        CompletableFuture<Either<Failure, List<ProductModelFB>>> future = new CompletableFuture<>();
        List<ProductModelFB> productList = new ArrayList<>();

        int pageInt = Integer.parseInt(page);
        int limitInt = Integer.parseInt(limit);
        boolean trang_dau = pageInt == 1;

        Query query = db.collection("products")
                .limit(limitInt);

        if (!trang_dau && lastDocument != null) {
            query = query.startAfter(lastDocument);
        }

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d("FIREBASE_DEBUG", "Query succeeded, documents count: " + queryDocumentSnapshots.size());
                    if(queryDocumentSnapshots.isEmpty()) {
                        future.complete(Either.right(Collections.emptyList()));
                    } else {
                        lastDocument = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size() - 1);

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ProductModelFB product = document.toObject(ProductModelFB.class);
                            Log.d("FIREBASE_DEBUG_REPO", "Product ID: " + product.getId());
                            productList.add(product);
                        }

                        List<ProductModelFB> filterProductList = productList;

                        if (search != null && !search.isEmpty()) {
                            filterProductList = productList.stream()
                                    .filter(product -> product.getName().toLowerCase().contains(search.toLowerCase()))
                                    .collect(Collectors.toList());
                        }

                        future.complete(Either.right(filterProductList));
                    }
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public void updateProductRepository(CategoryModel category) {

    }

    @Override
    public void deleteProductRepository(String id) {

    }

    @Override
    public CompletableFuture<Either<Failure, List<String>>> getBrandListByCategoryFromProduct(String category) {
        CompletableFuture<Either<Failure, List<String>>> future = new CompletableFuture<>();
        List<String> brandIdList = new ArrayList<>();

        db.collection("products")
                .whereEqualTo("categoryId", category)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        ProductModelFB product = document.toObject(ProductModelFB.class);
                        if (!brandIdList.contains(product.getBrandId())) {
                            brandIdList.add(product.getBrandId());
                        }
                    }
                    future.complete(Either.right(brandIdList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, ProductModelFB>> getDetailProductById(String id) {
        CompletableFuture<Either<Failure, ProductModelFB>> future = new CompletableFuture<>();
        db.collection("products")
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    ProductModelFB product = documentSnapshot.toObject(ProductModelFB.class);
                    future.complete(Either.right(product));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<ProductModelFB>>> getAllProducts(
            String categoryId,
            String brandId,
            String search,
            String page,
            String limit
    ) {
        CompletableFuture<Either<Failure, List<ProductModelFB>>> future = new CompletableFuture<>();
        List<ProductModelFB> productList = new ArrayList<>();

        int limitInt = (limit == null || limit.isEmpty()) ? Integer.MAX_VALUE : Integer.parseInt(limit);
        int pageInt = (page == null || page.isEmpty()) ? 1 : Integer.parseInt(page);
        boolean trang_dau = pageInt == 1;

        Query query = db.collection("products")
                .limit(limitInt);

        if (categoryId != null && !categoryId.isEmpty()) {
            query = query.whereEqualTo("categoryId", categoryId);
        }

        if (brandId != null && !brandId.isEmpty()) {
            query = query.whereEqualTo("brandId", brandId);
        }

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
                            ProductModelFB product = document.toObject(ProductModelFB.class);
                            productList.add(product);
                        }

                        List<ProductModelFB> filterProductList = productList;

                        if (search != null && !search.isEmpty()) {
                            filterProductList = productList.stream()
                                    .filter(product -> product.getName().toLowerCase().contains(search.toLowerCase()))
                                    .collect(Collectors.toList());
                        }

                        future.complete(Either.right(filterProductList));
                    }
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }
}
