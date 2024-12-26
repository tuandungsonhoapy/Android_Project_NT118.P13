package com.example.androidproject.features.address.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public void updateAddressRepository(AddressModel address) {
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
    }

    @Override
    public CompletableFuture<Either<Failure, String>> deleteAddressRepository(String id) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("addresses").document(id).delete();
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public void updateAddressDefault(String id, boolean isDefault) {
        Map<String, Object> addressData = new HashMap<>();
        addressData.put("isDefault", isDefault);
        db.collection("addresses").document(id).update(addressData);
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
}
