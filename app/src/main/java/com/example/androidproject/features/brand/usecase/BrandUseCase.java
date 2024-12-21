package com.example.androidproject.features.brand.usecase;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.brand.data.repository.BrandRepository;
import com.example.androidproject.features.brand.data.repository.BrandRepositoryImpl;
import com.example.androidproject.features.category.data.model.CategoryModel;
import com.example.androidproject.features.category.data.repository.CategoryRepository;
import com.example.androidproject.features.category.data.repository.CategoryRepositoryImpl;
import com.example.androidproject.features.category.usecase.CategoryUseCase;
import com.example.androidproject.features.product.data.repository.ProductRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BrandUseCase {
    private BrandRepository brandRepository = new BrandRepositoryImpl(FirebaseFirestore.getInstance());
    private CategoryRepository categoryRepository = new CategoryRepositoryImpl(FirebaseFirestore.getInstance());
    private ProductRepository productRepository = new ProductRepository(FirebaseFirestore.getInstance());

    public void addBrand(BrandModel brand, long quantity) {
        BrandModel brandModel = new BrandModel(brand.getName(), brand.getImageUrl(), brand.getDescription());
        brandRepository.addBrandRepository(brandModel, quantity);
    }

    public CompletableFuture<Either<Failure, List<BrandEntity>>> getBrandList(String page, String limit, String search) {
        return brandRepository.getBrandRepository(page, limit, search).thenApply(r -> {
            if (r.isRight()) {
                List<BrandModel> brandModels = r.getRight();
                List<BrandEntity> brandEntities = new BrandModel().toBrandEntityList(brandModels);
                return Either.right(brandEntities);
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public void updateBrand(BrandModel brand) {
        brandRepository.updateBrandRepository(brand);
    }

    public void deleteBrand(String id) {
        brandRepository.deleteBrandRepository(id);
    }

    public CompletableFuture<Either<Failure, BrandModel>> getBrandById(String id) {
        return brandRepository.getBrandById(id).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public void updateBrandHidden(String id, boolean hidden) {
        brandRepository.updateBrandHidden(id, hidden);
    }

    public CompletableFuture<Either<Failure, List<BrandEntity>>> getBrandListForStoreScreen() {
        return brandRepository.getBrandListForStoreScreen().thenApply(r -> {
            if (r.isRight()) {
                List<BrandModel> brandModels = r.getRight();
                List<BrandEntity> brandEntities = new BrandModel().toBrandEntityList(brandModels);
                return Either.right(brandEntities);
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public CompletableFuture<Either<Failure, List<BrandEntity>>> getBrandListByCategory(String category) {
        return categoryRepository.getCategoryByName(category).thenCompose(r -> {
            if(r.isRight()) {
                CategoryModel categoryModel = r.getRight();
                return productRepository.getBrandListByCategoryFromProduct(categoryModel.getId())
                        .thenCompose(r1 -> {
                            if (r1.isRight()) {
                                List<String> brandIds = r1.getRight();
                                List<CompletableFuture<Either<Failure, BrandModel>>> futures = brandIds.stream()
                                        .map(brandId -> brandRepository.getBrandById(brandId))
                                        .collect(Collectors.toList());
                                return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                                        .thenApply(r2 -> {
                                            List<BrandModel> brandModels = new ArrayList<>();
                                            for (CompletableFuture<Either<Failure, BrandModel>> future : futures) {
                                                Either<Failure, BrandModel> result = future.join();
                                                if (result.isRight()) {
                                                    brandModels.add(result.getRight());
                                                }
                                            }
                                            List<BrandEntity> brandEntities = new BrandModel().toBrandEntityList(brandModels);
                                            return Either.right(brandEntities);
                                        });
                            } else {
                                return CompletableFuture.completedFuture(Either.left(r1.getLeft()));
                            }
                        });
            } else {
                return CompletableFuture.completedFuture(Either.left(r.getLeft()));
            }
        });
    }
}
