package com.example.androidproject.features.auth.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.auth.data.entity.UserEntity;
import com.example.androidproject.features.voucher.data.model.VoucherModel;

import java.util.concurrent.CompletableFuture;

public interface UserRepository {
    CompletableFuture<Either<Failure, String>> addVoucherRepository(String voucherId);

    CompletableFuture<Either<Failure, UserEntity>> getAllUserVouchers();

    CompletableFuture<Either<Failure, String>> deleteUserVoucher(String voucherId);

    CompletableFuture<Either<Failure, Double>> updateTotalSpent(double spent);

    CompletableFuture<Either<Failure, String>> updateUserTier();
}
