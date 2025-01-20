package com.example.androidproject.features.cart.usecase;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.cart.data.model.CartModel;
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

    public CompletableFuture<Either<Failure, String>> getCurrentCartItemCount() {
        return cartRepository.getCurrentCartItemCount()
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        Log.e("CartUseCase", "getCurrentCartItemCount: " + r.getLeft().getErrorMessage());
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, CartModel>> getCurrentUserCart() {
        return cartRepository.getCurrentUserCart()
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        Log.e("CartUseCase", "getCurrentUserCart: " + r.getLeft().getErrorMessage());
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> updateCartQuantity(String productId, int productQuantity) {
        return cartRepository.updateCartQuantity(productId, productQuantity)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        Log.e("CartUseCase", "updateCartQuantity: " + r.getLeft().getErrorMessage());
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> deleteCart(String userId) {
        return cartRepository.deleteCart(userId)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        Log.e("CartUseCase", "deleteCart: " + r.getLeft().getErrorMessage());
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> removeProductFromCart(String productId, ProductOption option) {
        return cartRepository.removeProductFromCart(productId, option)
                .thenApply(r -> {
                    if(r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }
}
