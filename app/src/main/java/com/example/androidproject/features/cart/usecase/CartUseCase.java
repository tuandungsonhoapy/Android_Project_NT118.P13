package com.example.androidproject.features.cart.usecase;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.cart.data.repository.CartRepositoryImpl;
import com.example.androidproject.features.product.data.entity.ProductOption;
import com.example.androidproject.features.product.data.repository.ProductRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;


public class CartUseCase {
    private final CartRepositoryImpl cartRepository;

    public CartUseCase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ProductRepository productRepository = new ProductRepository(db);
        cartRepository = new CartRepositoryImpl(db, productRepository);
    }

    public CompletableFuture<Either<Failure,String>> addProductToCart(
                                                               String productId,
                                                               int productQuantity,
                                                               ProductOption option,
                                                               long quantity) {
        return cartRepository.addProductToCart(productId, productQuantity, option, quantity)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        Log.e("CartUseCase", "addProductToCart: " + r.getLeft().getErrorMessage());
                        return Either.left(r.getLeft());
                    }
                });
    }
}
