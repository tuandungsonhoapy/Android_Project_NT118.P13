package com.example.androidproject.features.brand.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.brand.data.model.BrandModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandRepository {
    CompletableFuture<Either<Failure, List<BrandModel>>> getBrandRepository(String page, String limit, String search);

    void addBrandRepository(BrandModel brandModel, long quantity);

    void updateBrandRepository(BrandModel brandModel);

    void deleteBrandRepository(String brandId);

    CompletableFuture<Either<Failure, BrandModel>> getBrandById(String brandId);

    void updateBrandHidden(String brandId, boolean hidden);
}
