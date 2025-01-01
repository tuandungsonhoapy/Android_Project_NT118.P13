package com.example.androidproject.features.cart.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.cart.data.model.CartModel;
import com.example.androidproject.features.product.data.entity.ProductOption;

import java.util.concurrent.CompletableFuture;

public interface CartRepository {
    public CompletableFuture<Either<Failure,String>> addProductToCart(String productId, int productQuantity, ProductOption option, long quantity);

    public CompletableFuture<Either<Failure, CartModel>> getCartByUserId(String userId);

    public CompletableFuture<Either<Failure, String>> getCurrentCartItemCount();

    public CompletableFuture<Either<Failure, CartModel>> getCurrentUserCart();

    public CompletableFuture<Either<Failure, String>> updateCartQuantity(String productId, int productQuantity);

    public CompletableFuture<Either<Failure, String>> deleteCart(String userId);

    public CompletableFuture<Either<Failure, String>> removeProductFromCart(String productId, ProductOption option);
}
