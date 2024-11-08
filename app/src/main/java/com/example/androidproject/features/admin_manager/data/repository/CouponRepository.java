package com.example.androidproject.features.admin_manager.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.admin_manager.data.entity.CouponEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CouponRepository {
    CompletableFuture<Either<Failure, List<CouponEntity>>> fetch();
}
