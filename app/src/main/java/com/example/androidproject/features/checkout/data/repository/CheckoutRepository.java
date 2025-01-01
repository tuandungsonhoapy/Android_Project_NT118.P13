package com.example.androidproject.features.checkout.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CheckoutRepository {
    CompletableFuture<Either<Failure, String>> addCheckoutRepository(CheckoutModel checkoutModel, long quantity);

    CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutList(String userId);

    CompletableFuture<Either<Failure, List<CheckoutModel>>> getLatestCheckouts(int limit);

    CompletableFuture<Either<Failure, Integer>> getNumberCheckoutToday();

    CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByStatus(String status);

    CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByStatusAndUserId(String status, String userId);

    CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByUserId(String userId);

    CompletableFuture<Either<Failure, List<CheckoutModel>>> getCheckoutListByFilter(String field, String filter, String userId);
}
