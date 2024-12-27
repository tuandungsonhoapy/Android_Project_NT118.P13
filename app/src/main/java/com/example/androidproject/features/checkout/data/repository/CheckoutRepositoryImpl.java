package com.example.androidproject.features.checkout.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class CheckoutRepositoryImpl implements CheckoutRepository {
    private final FirebaseFirestore db;
    public CheckoutRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> addCheckoutRepository(CheckoutModel checkoutModel, long quantity) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        HashMap<String, Object> checkout = new HashMap<>();

        checkout.put("id", checkoutModel.prefixCheckoutId(quantity));
        checkout.put("userId", checkoutModel.getUserId());
        checkout.put("addressId", checkoutModel.getAddressId());
        checkout.put("note", checkoutModel.getNote());
        checkout.put("status", checkoutModel.getStatus());
        checkout.put("createdAt", checkoutModel.getCreatedAt());
        checkout.put("updatedAt", checkoutModel.getUpdatedAt());
        checkout.put("products", checkoutModel.getProducts());
        checkout.put("fullAddress", checkoutModel.getFullAddress());
        checkout.put("totalPrice", checkoutModel.getTotalPrice());

        db.collection("checkouts").document(checkoutModel.prefixCheckoutId(quantity)).set(checkout);
        future.complete(Either.right("Success"));
        return future;
    }

}
