package com.example.androidproject.features.auth.usecase;

import android.content.Context;

import com.example.androidproject.core.credential.UserPreferences;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.auth.data.repository.UserRepositoryimpl;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

public class UserUseCase {
    private final UserRepositoryimpl userRepository;

    public UserUseCase(Context ctx) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userRepository = new UserRepositoryimpl(db, ctx, new UserPreferences(ctx));
    }

    public CompletableFuture<Either<Failure, String>> addVoucher(String voucherId) {
        return userRepository.addVoucherRepository(voucherId)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, UserEntity>> getAllUserVouchers() {
        return userRepository.getAllUserVouchers()
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> deleteUserVoucher(String voucherId) {
        return userRepository.deleteUserVoucher(voucherId)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }
}
