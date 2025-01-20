package com.example.androidproject.features.voucher.usecase;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.voucher.data.model.VoucherModel;
import com.example.androidproject.features.voucher.data.repository.VoucherRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VoucherUseCase {
    private VoucherRepositoryImpl voucherRepository = new VoucherRepositoryImpl(FirebaseFirestore.getInstance());

    public CompletableFuture<Either<Failure, String>> addVoucher(VoucherModel voucherModel, long quantity) {
        return voucherRepository.addVoucherRepository(voucherModel, quantity)
                .thenApply(r -> {
                    if (r.isLeft()) {
                        return Either.left(r.getLeft());
                    } else {
                        return Either.right(r.getRight());
                    }
                });
    }

    public CompletableFuture<Either<Failure, List<VoucherModel>>> getVoucher() {
        return voucherRepository.getVoucherRepository()
                .thenApply(r -> {
                    if (r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, VoucherModel>> getVoucherById(String id) {
        return voucherRepository.getVoucherById(id)
                .thenApply(r -> {
                    if (r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> updateVoucher(VoucherModel voucherModel) {
        return voucherRepository.updateVoucherRepository(voucherModel)
                .thenApply(r -> {
                    if (r.isLeft()) {
                        return Either.left(r.getLeft());
                    } else {
                        return Either.right(r.getRight());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> deleteVoucher(String id) {
        return voucherRepository.deleteVoucherRepository(id)
                .thenApply(r -> {
                    if (r.isLeft()) {
                        return Either.left(r.getLeft());
                    } else {
                        return Either.right(r.getRight());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> updateVoucherHidden(String id, boolean hidden) {
        return voucherRepository.updateVoucherHidden(id, hidden)
                .thenApply(r -> {
                    if (r.isLeft()) {
                        return Either.left(r.getLeft());
                    } else {
                        return Either.right(r.getRight());
                    }
                });
    }

    public CompletableFuture<Either<Failure, List<VoucherModel>>> getAllActiveVouchers(List<String> voucherIds) {
        return voucherRepository.getAllActiveVouchers(voucherIds)
                .thenApply(r -> {
                    if (r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }
}
