package com.example.androidproject.features.product.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.presentation.ProductAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                .orderBy("id", Query.Direction.DESCENDING) // Sắp xếp theo id giảm dần
                .limit(limitInt);

        if (!trang_dau && lastDocument != null) {
            query = query.startAfter(lastDocument);
        }

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
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

    @Override
    public CompletableFuture<Either<Failure, List<ProductModelFB>>> getProductPage(int page, int limit) {
        CompletableFuture<Either<Failure, List<ProductModelFB>>> future = new CompletableFuture<>();
        List<ProductModelFB> productList = new ArrayList<>();

        int pageInt = page;
        int limitInt = limit;

        // Đặt lại lastDocument nếu là trang đầu tiên
        if (pageInt == 1) {
            lastDocument = null;
        }

        Query query = db.collection("products")
                .orderBy("id", Query.Direction.DESCENDING) // Sắp xếp theo id giảm dần
                .limit(limitInt);

        // Áp dụng startAfter nếu không phải trang đầu tiên
        if (lastDocument != null) {
            query = query.startAfter(lastDocument);
        }

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        future.complete(Either.right(Collections.emptyList()));
                    } else {
                        // Cập nhật lastDocument cho trang tiếp theo
                        lastDocument = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size() - 1);

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ProductModelFB product = document.toObject(ProductModelFB.class);
                            productList.add(product);
                        }

                        future.complete(Either.right(productList));
                    }
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<ProductModelFB>>> getOutOfStockProducts() {
        CompletableFuture<Either<Failure, List<ProductModelFB>>> future = new CompletableFuture<>();
        List<ProductModelFB> productList = new ArrayList<>();

        db.collection("products")
                .whereEqualTo("stockQuantity", 0)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        ProductModelFB product = document.toObject(ProductModelFB.class);
                        productList.add(product);
                    }
                    future.complete(Either.right(productList));
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

    @Override
    public CompletableFuture<Either<Failure, List<ProductModelFB>>> getProductsAndMapBrands() {
        CompletableFuture<Either<Failure, List<ProductModelFB>>> future = new CompletableFuture<>();
        Set<String> brandIds = new HashSet<>();
        List<ProductModelFB> productList = new ArrayList<>();

        db.collection("products")
                .orderBy("stockQuantity", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(productSnapshots -> {
                    for (QueryDocumentSnapshot document : productSnapshots) {
                        ProductModelFB product = document.toObject(ProductModelFB.class);
                        productList.add(product);
                        if (product.getBrandId() != null) {
                            brandIds.add(product.getBrandId());
                        }
                    }

                    db.collection("brands")
                            .whereIn(FieldPath.documentId(), new ArrayList<>(brandIds))
                            .get()
                            .addOnSuccessListener(brandSnapshots -> {
                                Map<String, BrandModel> brandMap = new HashMap<>();
                                for (QueryDocumentSnapshot brandDoc : brandSnapshots) {
                                    BrandModel brand = brandDoc.toObject(BrandModel.class);
                                    brandMap.put(brandDoc.getId(), brand);
                                }

                                for (ProductModelFB product : productList) {
                                    product.setBrand(brandMap.get(product.getBrandId()));
                                }
                                future.complete(Either.right(productList));
                            });
                });
        return future;
    }



    @Override
    public CompletableFuture<Either<Failure, String>> updateProductQuantity(List<ProductsOnCart> productsOnCarts) {
        CompletableFuture<Either<Failure, String>> resultFuture = CompletableFuture.completedFuture(Either.right("Success"));
        for(ProductsOnCart productsOnCart: productsOnCarts) {
            if (productsOnCart.getProductOptions() == null) {
                resultFuture = resultFuture.thenCompose(r -> {
                    if (r.isRight()) {
                        return updateStockQuantityWithoutOption(productsOnCart.getQuantity(), productsOnCart.getProductId());
                    } else {
                        return CompletableFuture.completedFuture(Either.left(r.getLeft()));
                    }
                });
            } else {
                resultFuture = resultFuture.thenCompose(previousResult -> {
                    if (previousResult.isLeft()) {
                        return CompletableFuture.completedFuture(previousResult);
                    }
                    return updateStockQuantityWithOption(productsOnCart.getQuantity(), productsOnCart.getProductId(), productsOnCart.getProductOptions());
                });;
            }
        }

        return resultFuture.thenCompose(r -> {
            if (r.isLeft()) {
                return CompletableFuture.completedFuture(r);
            }

            List<CompletableFuture<Void>> updateStockFutures = productsOnCarts.stream()
                    .map(productsOnCart -> updateStockQuantityProductHasOption(productsOnCart.getProductId()))
                    .collect(Collectors.toList());

            return CompletableFuture.allOf(updateStockFutures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> Either.right("Success"));
        });
    }

    private CompletableFuture<Either<Failure, String>> updateStockQuantityWithoutOption(int quantity, String productId) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        getDetailProductById(productId)
                .thenAccept(r -> {
                    if(r.isRight()) {
                        ProductModelFB product = r.getRight();
                        int newQuantity = product.getStockQuantity() - quantity;
                        if(newQuantity < 0) {
                            future.complete(Either.left(new Failure("Số lượng không đủ")));
                            return;
                        }

                        db.collection("products")
                                .document(productId)
                                .update("stockQuantity", newQuantity)
                                .addOnSuccessListener(aVoid -> {
                                    future.complete(Either.right("Success"));
                                })
                                .addOnFailureListener(e -> {
                                    future.complete(Either.left(new Failure(e.getMessage())));
                                });
                    } else {
                        future.complete(Either.left(r.getLeft()));
                    }
                });
        return future;
    }

    private CompletableFuture<Either<Failure,String>> updateStockQuantityWithOption(int optionQuantity, String productId, ProductOption option) {
        return getDetailProductById(productId)
                .thenCompose(r -> {
                   if(r.isRight()) {
                          ProductModelFB product = r.getRight();
                          int index = findOptionIndex(product.getOptions(), option);
                          if(index == -1) {
                              return CompletableFuture.completedFuture(Either.left(new Failure("Không tìm thấy option")));
                          }

                          ProductOption productOption = product.getOptions().get(index);
                          int newOpionQuantity = productOption.getQuantity() - optionQuantity;
                          if(newOpionQuantity < 0) {
                              return CompletableFuture.completedFuture(Either.left(new Failure("Số lượng không đủ")));
                          }
                          productOption.setQuantity(newOpionQuantity);
                          product.getOptions().set(index, productOption);

                          CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
                          db.collection("products")
                                 .document(productId)
                                 .update(
                                         "options", product.getOptions()
                                 ).addOnSuccessListener(aVoid -> {
                                      future.complete(Either.right("Success"));
                                 }).addOnFailureListener(e -> {
                                      future.complete(Either.left(new Failure(e.getMessage())));
                                 });
                            return future;
                     } else {
                       return CompletableFuture.completedFuture(Either.left(r.getLeft()));
                   }
                });
    }

    private CompletableFuture<Void> updateStockQuantityProductHasOption(String productId) {
        return getDetailProductById(productId)
                .thenCompose(r -> {
                    if(r.isRight()) {
                        ProductModelFB product = r.getRight();
                        if(product.getOptions() != null) {
                            int newQuantity = product.getOptions().stream()
                                    .mapToInt(ProductOption::getQuantity)
                                    .sum();
                            CompletableFuture<Void> future = new CompletableFuture<>();
                            db.collection("products")
                                    .document(productId)
                                    .update("stockQuantity", newQuantity)
                                    .addOnSuccessListener(aVoid -> {
                                        future.complete(null);
                                    })
                                    .addOnFailureListener(e -> {
                                        future.completeExceptionally(e);
                                    });
                            return future;
                        }
                    }
                    return CompletableFuture.completedFuture(null);
                });
    }

    private int findOptionIndex(List<ProductOption> options, ProductOption option) {
        for(int i = 0; i < options.size(); i++) {
            ProductOption productOption = options.get(i);
            if(productOption.getChip().equals(option.getChip())
                    && productOption.getRam().equals(option.getRam())
                    && productOption.getRom().equals(option.getRom())) {
                return i;
            }
        }
        return -1;
    }
}
