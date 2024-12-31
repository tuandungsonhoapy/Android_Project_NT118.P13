package com.example.androidproject.features.auth.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.voucher.data.model.VoucherModel;

import java.util.concurrent.CompletableFuture;

public interface UserRepository {
    CompletableFuture<Either<Failure, String>> addVoucherRepository(VoucherModel voucherModel);

    CompletableFuture<Either<Failure, String>> getAllUserVouchers(String userId);
}
