package com.example.androidproject.features.address.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AddressRepositoryImpl implements AddressRepository {
    private final FirebaseFirestore db;
    public AddressRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }
    private String userId;

    @Override
    public CompletableFuture<Either<Failure, String>> addAddressRepository(AddressModel address, long quantity) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        Map<String, Object> addressData = new HashMap<>();

        addressData.put("id", address.prefixAddressID(quantity));
        addressData.put("street", address.getStreet());
        addressData.put("province", address.getProvince());
        addressData.put("district", address.getDistrict());
        addressData.put("ward", address.getWard());
        addressData.put("userId", address.getUserId());
        addressData.put("provinceId", address.getProvinceId());
        addressData.put("districtId", address.getDistrictId());
        addressData.put("wardId", address.getWardId());
        addressData.put("createdAt", address.getCreatedAt());
        addressData.put("updatedAt", address.getUpdatedAt());
        addressData.put("isDefault", address.getIsDefault());

        db.collection("addresses").document(address.prefixAddressID(quantity)).set(addressData);
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure,String>> updateAddressRepository(AddressModel address) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        Map<String, Object> addressData = new HashMap<>();

        addressData.put("street", address.getStreet());
        addressData.put("province", address.getProvince());
        addressData.put("district", address.getDistrict());
        addressData.put("ward", address.getWard());
        addressData.put("provinceId", address.getProvinceId());
        addressData.put("districtId", address.getDistrictId());
        addressData.put("wardId", address.getWardId());
        addressData.put("updatedAt", Timestamp.now());

        db.collection("addresses").document(address.getId()).update(addressData);
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> deleteAddressRepository(String id) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("addresses").document(id).delete()
                .addOnSuccessListener(unused -> future.complete(Either.right("Success")))
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> updateAddressDefault(String id) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        WriteBatch batch = db.batch();
        DocumentReference addressRef = db.collection("addresses").document(id);
        batch.update(addressRef, "isDefault", true);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db.collection("addresses")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        if (!document.getId().equals(id)) {
                            DocumentReference addressRef1 = db.collection("addresses").document(document.getId());
                            batch.update(addressRef1, "isDefault", false);
                        }
                    }
                    batch.commit();
                    future.complete(Either.right("Success"));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<AddressModel>>> getAddressRepository() {
        CompletableFuture<Either<Failure, List<AddressModel>>> future = new CompletableFuture<>();
        List<AddressModel> addressList = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db.collection("addresses")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        AddressModel address = document.toObject(AddressModel.class);
                        addressList.add(address);
                    }
                    future.complete(Either.right(addressList));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));

        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, AddressModel>> getAddressById(String id) {
        CompletableFuture<Either<Failure, AddressModel>> future = new CompletableFuture<>();
        db.collection("addresses").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddressModel address = documentSnapshot.toObject(AddressModel.class);
                    future.complete(Either.right(address));
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, AddressModel>> getDefaultAddress() {
        CompletableFuture<Either<Failure, AddressModel>> future = new CompletableFuture<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        db.collection("addresses")
                .whereEqualTo("userId", userId)
                .whereEqualTo("isDefault", true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        AddressModel address = document.toObject(AddressModel.class);
                        future.complete(Either.right(address));
                    }
                })
                .addOnFailureListener(e -> future.complete(Either.left(new Failure(e.getMessage()))));
        return future;
    }

    // update users collection address
    public CompletableFuture<Either<Failure, String>> updateUserAddress(String addressId, String fullAddress) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        Map<String, Object> userUpdateData = new HashMap<>();
        userUpdateData.put("addressId", addressId);
        userUpdateData.put("fullAddress", fullAddress);

        db.collection("users").document(userId)
                .update(userUpdateData)
                .addOnSuccessListener(aVoid -> {
                    future.complete(Either.right("Address updated successfully"));
                })
                .addOnFailureListener(e -> {
                    future.complete(Either.left(new Failure(e.getMessage())));
                });

        return future;
    }
}
