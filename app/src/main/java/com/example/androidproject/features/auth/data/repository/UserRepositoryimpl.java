package com.example.androidproject.features.auth.data.repository;

import android.content.Context;

import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

public class UserRepositoryimpl implements UserRepository {
    private final FirebaseFirestore db;
    private final Context ctx;
    public UserRepositoryimpl(FirebaseFirestore db, Context ctx) {
        this.db = db;
        this.ctx = ctx;
    }
    private UserPreferences userPreferences = new UserPreferences(ctx);

    @Override
    public CompletableFuture<Either<Failure, String>> addVoucherRepository(VoucherModel voucherModel) {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        db.collection("users").document(voucherModel.getId()).set(voucherModel);
        future.complete(Either.right("Success"));
        return future;
    }

    @Override
    public CompletableFuture<Either<Failure, String>> getAllUserVouchers() {
        CompletableFuture<Either<Failure, String>> future = new CompletableFuture<>();
        String userId = (String)userPreferences.getUserDataByKey(UserPreferences.KEY_DOC_ID);
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    future.complete(Either.right("Success"));
                });
        return future;
    }
}
