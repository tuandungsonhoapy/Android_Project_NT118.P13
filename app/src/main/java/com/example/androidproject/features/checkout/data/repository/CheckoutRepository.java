package com.example.androidproject.features.checkout.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;

import java.util.concurrent.CompletableFuture;

public interface CheckoutRepository {
    CompletableFuture<Either<Failure, String>> addCheckoutRepository(CheckoutModel checkoutModel, long quantity);
}
