package com.example.androidproject.features.address.data.repository;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.address.data.model.AddressModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AddressRepository {
    CompletableFuture<Either<Failure, String>> addAddressRepository(AddressModel address, long quantity);

    void updateAddressRepository(AddressModel address);

    CompletableFuture<Either<Failure, String>> deleteAddressRepository(String id);

    void updateAddressDefault(String id, boolean isDefault);

    CompletableFuture<Either<Failure, List<AddressModel>>> getAddressRepository();
}
