package com.example.androidproject.features.checkout.usecase;

import com.example.androidproject.R;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.checkout.data.model.CheckoutModel;
import com.example.androidproject.features.checkout.data.repository.CheckoutRepository;
import com.example.androidproject.features.checkout.data.repository.CheckoutRepositoryImpl;
import com.example.androidproject.features.order.data.ProductDataForOrderModel;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CheckoutUseCase {
    private CheckoutRepository checkoutRepository = new CheckoutRepositoryImpl(FirebaseFirestore.getInstance());

    public CompletableFuture<Either<Failure, String>> addCheckout(CheckoutModel checkoutModel, long quantity) {
        return checkoutRepository.addCheckoutRepository(checkoutModel, quantity)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }
}
