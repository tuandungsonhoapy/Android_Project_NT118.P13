package com.example.androidproject.features.address.usecase;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.data.repository.AddressRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AddressUsecase {
    private AddressRepositoryImpl addressRepository = new AddressRepositoryImpl(FirebaseFirestore.getInstance());

    public CompletableFuture<Either<Failure, List<AddressModel>>> getAddresses(){
        return addressRepository.getAddressRepository()
                .thenApply(r -> {
                   if (r.isRight()) {
                          return Either.right(r.getRight());
                     } else {
                          return Either.left(r.getLeft());
                   }
                });
    }

    public CompletableFuture<Either<Failure, String>> addAddress(AddressModel address, long quantity) {
        return addressRepository.addAddressRepository(address, quantity).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public void editAddress(String id, AddressModel addressModel) {
        // Edit address in database
    }

    public CompletableFuture<Either<Failure, String>> deleteAddress(String id) {
        return addressRepository.deleteAddressRepository(id).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }
}
