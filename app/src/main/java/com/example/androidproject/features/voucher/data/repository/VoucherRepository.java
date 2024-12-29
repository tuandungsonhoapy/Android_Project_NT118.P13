package com.example.androidproject.features.voucher.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.voucher.data.model.VoucherModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface VoucherRepository {
    CompletableFuture<Either<Failure, String>> addVoucherRepository(VoucherModel voucherModel, long quantity);

    CompletableFuture<Either<Failure, List<VoucherModel>>> getVoucherRepository();

    CompletableFuture<Either<Failure,VoucherModel>> getVoucherById(String id);

    CompletableFuture<Either<Failure, String>> updateVoucherRepository(VoucherModel voucherModel);

    CompletableFuture<Either<Failure, String>> deleteVoucherRepository(String id);

    CompletableFuture<Either<Failure, String>> updateVoucherHidden(String id, boolean hidden);

    CompletableFuture<Either<Failure, List<VoucherModel>>> getAllActiveVouchers();
}
