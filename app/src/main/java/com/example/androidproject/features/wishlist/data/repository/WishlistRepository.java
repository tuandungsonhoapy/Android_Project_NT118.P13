package com.example.androidproject.features.wishlist.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WishlistRepository {
    private final FirebaseFirestore firestore;

    public WishlistRepository() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<Either<Failure, List<String>>> getWishlistIds() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return getWishlistIds(uid);
    }

    public CompletableFuture<Either<Failure, List<String>>> getWishlistIds(String userId) {
        CompletableFuture<Either<Failure, List<String>>> future = new CompletableFuture<>();

        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> wishlistIds = (List<String>) documentSnapshot.get("wishlist");
                        if (wishlistIds != null) {
                            future.complete(Either.right(wishlistIds));
                        } else {
                            future.complete(Either.right(new ArrayList<>()));
                        }
                    } else {
                        future.complete(Either.right(new ArrayList<>()));
                    }
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure(e.getMessage())));
                });

        return future;
    }

    public CompletableFuture<Either<Failure, List<ProductEntity>>> getProductsByIds(List<String> productIds) {
        CompletableFuture<Either<Failure, List<ProductEntity>>> future = new CompletableFuture<>();

        firestore.collection("products")
                .whereIn(FieldPath.documentId(), productIds)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ProductEntity> products = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        ProductEntity product = document.toObject(ProductEntity.class);
                        if (product != null) {
                            products.add(product);
                        }
                    }
                    future.complete(Either.right(products));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));

        return future;
    }

    public CompletableFuture<Either<Failure, Boolean>> updateWishlist(List<String> productIds) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return updateWishlist(uid, productIds);
    }

    public CompletableFuture<Either<Failure, Boolean>> updateWishlist(String userId, List<String> productIds) {
        CompletableFuture<Either<Failure, Boolean>> future = new CompletableFuture<>();

        firestore.collection("users")
                .document(userId)
                .update("wishlist", productIds)
                .addOnSuccessListener(aVoid -> {
                    future.complete(Either.right(true));
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure(e.getMessage())));
                });

        return future;
    }
}
