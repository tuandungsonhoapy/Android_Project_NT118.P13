package com.example.androidproject.features.product.usecase;

import android.util.Log;

import com.example.androidproject.R;
import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.category.data.entity.CategoryEntity;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.product.data.entity.ProductEntity;
import com.example.androidproject.features.product.data.model.ProductModel;
import com.example.androidproject.features.product.data.model.ProductModelFB;
import com.example.androidproject.features.product.data.repository.ProductRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProductUseCase {
    private ProductRepository productRepository = new ProductRepository(FirebaseFirestore.getInstance());

    public List<ProductModel> getProductsList() {
        List<ProductModel> productList = new ArrayList<>();
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""), true));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo","",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel("Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""),false));
        productList.add(new ProductModel("Legion 5 2021", R.drawable.image_product, 1299, 12, new BrandModel( "Lenovo", "",""),false));

        return productList;

    }

    public List<ProductEntity> getProductEntityList(){
        List<ProductEntity> productList = new ArrayList<>();
        productList.add(new ProductEntity("Legion 5", new ArrayList<>(), 1299, 12, "1", "1", null, null));
        productList.add(new ProductEntity("Legion 5", new ArrayList<>(), 1299, 12, "1", "1", null, null));
        productList.add(new ProductEntity("Legion 5", new ArrayList<>(), 1299, 12, "1", "1", null, null));
        productList.add(new ProductEntity("Legion 5", new ArrayList<>(), 1299, 12, "1", "1", null, null));
        productList.add(new ProductEntity("Legion 5", new ArrayList<>(), 1299, 12, "1", "1", null, null));

        return productList;
    }

    public void addProduct(ProductModelFB product, long quantity) {
        productRepository.addProductRepository(product, quantity);
    }

    public CompletableFuture<Either<Failure,List<ProductEntity>>> getProducts(String page, String limit, String search) {
        return productRepository.getProductRepository(page, limit, search).thenApply(r -> {
            if (r.isRight()) {
                List<ProductModelFB> productModels = r.getRight();
                Log.d("ProductUseCase", "getProducts: " + productModels.size());
                List<ProductEntity> productEntities = new ProductModelFB().toProductEntityList(productModels);
                return Either.right(productEntities);
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public CompletableFuture<Either<Failure, List<ProductModelFB>>> getAllProducts(String categoryId, String brandId, String search, String page, String limit) {
        return productRepository.getAllProducts(categoryId, brandId, search, page, limit)
                .thenApply(r -> {
                    if (r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }
}
