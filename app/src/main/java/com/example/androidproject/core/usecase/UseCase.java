package com.example.androidproject.core.usecase;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;

import java.util.concurrent.CompletableFuture;

public interface UseCase<Type, Params> {
    public abstract CompletableFuture<Either<Failure, Type>> invoke(Params params);
}
