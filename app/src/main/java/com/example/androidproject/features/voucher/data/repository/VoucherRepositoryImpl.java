package com.example.androidproject.features.voucher.data.repository;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class VoucherRepositoryImpl implements VoucherRepository {
    private final FirebaseFirestore db;

    public VoucherRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public CompletableFuture<Either<Failure,String>> addVoucherRepository(VoucherModel voucherModel, long quantity) {
        CompletableFuture<Either<Failure,String>> future = new CompletableFuture<>();
        voucherModel.setId(voucherModel.prefixVoucherId(quantity));
        db.collection("vouchers").document(voucherModel.prefixVoucherId(quantity)).set(voucherModel);

        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<VoucherModel>>> getVoucherRepository() {
        CompletableFuture<Either<Failure, List<VoucherModel>>> future = new CompletableFuture<>();
        List<VoucherModel> voucherList = new ArrayList<>();
        db.collection("vouchers")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot document : queryDocumentSnapshots) {
                        VoucherModel voucher = document.toObject(VoucherModel.class);
                        voucherList.add(voucher);
                    }
                    future.complete(Either.right(voucherList));
                });
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure,VoucherModel>> getVoucherById(String id) {
        CompletableFuture<Either<Failure,VoucherModel>> future = new CompletableFuture<>();
        db.collection("vouchers").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    VoucherModel voucher = documentSnapshot.toObject(VoucherModel.class);
                    future.complete(Either.right(voucher));
                });
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> updateVoucherRepository(VoucherModel voucherModel) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("vouchers").document(voucherModel.getId()).set(voucherModel);
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> deleteVoucherRepository(String id) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("vouchers").document(id).delete();
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> updateVoucherHidden(String id, boolean hidden) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("vouchers").document(id).update("hidden", hidden);
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, List<VoucherModel>>> getAllActiveVouchers(List<String> voucherIds) {
        CompletableFuture<Either<Failure, List<VoucherModel>>> future = new CompletableFuture<>();
        List<VoucherModel> voucherList = new ArrayList<>();

        if (voucherIds == null || voucherIds.isEmpty()) {
            future.complete(Either.right(voucherList));
            return future;
        }

        db.collection("vouchers")
                .whereEqualTo("hidden", false)
                .whereIn("id", voucherIds)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        VoucherModel voucher = document.toObject(VoucherModel.class);
                        voucherList.add(voucher);
                    }
                    future.complete(Either.right(voucherList));
                })
                .addOnFailureListener(e -> {
                    Failure failure = new Failure("Failed to fetch vouchers: " + e.getMessage());
                    future.complete(Either.left(failure));
                });

        return future;
    }
}
