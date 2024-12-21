package com.example.androidproject.features.brand.usecase;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.entity.BrandEntity;
import com.example.androidproject.features.brand.data.model.BrandModel;
import com.example.androidproject.features.brand.data.repository.BrandRepository;
import com.example.androidproject.features.brand.data.repository.BrandRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BrandUseCase {
    private BrandRepository brandRepository = new BrandRepositoryImpl(FirebaseFirestore.getInstance());

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
}
