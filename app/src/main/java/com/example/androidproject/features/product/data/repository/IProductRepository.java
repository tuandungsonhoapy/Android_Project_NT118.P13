package com.example.androidproject.features.product.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.cart.data.entity.ProductsOnCart;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductRepository {
    void addProductRepository(ProductModelFB product, long quantity);

    CompletableFuture<Either<Failure, List<ProductModelFB>>> getProductRepository(String page, String limit, String search);

    void updateProductRepository(CategoryModel category);

    void deleteProductRepository(String id);

    CompletableFuture<Either<Failure, List<String>>> getBrandListByCategoryFromProduct(String category);

    CompletableFuture<Either<Failure, ProductModelFB>> getDetailProductById(String id);

    CompletableFuture<Either<Failure, List<ProductModelFB>>> getAllProducts(String categoryId, String brandId, String search, String page, String limit);

    CompletableFuture<Either<Failure, List<ProductModelFB>>> getProductsAndMapBrands();

    CompletableFuture<Either<Failure, String>> updateProductQuantity(List<ProductsOnCart> productsOnCart);

    CompletableFuture<Either<Failure, List<ProductModelFB>>> getProductPage(int page, int limit);

    CompletableFuture<Either<Failure, List<ProductModelFB>>> getOutOfStockProducts();
}
